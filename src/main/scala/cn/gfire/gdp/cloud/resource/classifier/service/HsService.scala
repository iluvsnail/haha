package cn.gfire.gdp.cloud.resource.classifier.service

import com.alibaba.fastjson.{JSONArray, JSONObject}
import javax.annotation.PostConstruct
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.mapstruct.Qualifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import redis.clients.jedis.exceptions.JedisConnectionException
import redis.clients.jedis.{Jedis, JedisPool}

import scala.collection.JavaConversions._


@Service
class HsService {

    val HS_TOOL_CURRENT_CAST = "hs:tool:current:cast"
    val HS_TOOL_CLIENT= "hs:tool:client:message"
    val HS_TOOL_CLIENT_FULLSCREEN = "hs:tool:client:fullscreen"
    val HS_TOOL_CLIENT_GIFT = "hs:tool:client:gift"
    val KEY_EXPIRES = 600


    private var pool: JedisPool = null
    @Autowired
    private val redisIP =  ""
    @Autowired
    private val redisPort:Int = 0


    @PostConstruct
    private def init(): Unit = {
        val config = new GenericObjectPoolConfig
        config.setMaxIdle(200)
        config.setMaxTotal(5120)
        config.setMaxWaitMillis(5000)
        config.setTestOnBorrow(true)
        config.setTestOnReturn(true)
        pool = new JedisPool(config, redisIP, redisPort,60000)
    }
    def getCurrentCast():String = {
        val redis = getRedisInstance()
        val currentCast=redis.get(HS_TOOL_CURRENT_CAST)
        redis.close()
        currentCast
    }

    def setCurrentCast(newCast:String):String = {
        val redis = getRedisInstance()
        redis.set(HS_TOOL_CURRENT_CAST,newCast)
        val currentCast=redis.get(HS_TOOL_CURRENT_CAST)
        redis.close()
        currentCast
    }

    def sendMessage(message:String):String = {
        val redis = getRedisInstance()
        listClient().foreach(client=> {
            redis.set(client.toString, message)
            redis.expire(client.toString,KEY_EXPIRES)
        })
        redis.close()
        message
    }

    def sendFullscreenMessage(message:String):String = {
        val redis = getRedisInstance()
        listClient().foreach(client=> {
            redis.set(getFullscreenKey(client.toString), message)
            redis.expire(getFullscreenKey(client.toString),KEY_EXPIRES)
        })
        redis.close()
        message
    }

    def sendGift(message:String):String = {
        val redis = getRedisInstance()
        listClient().foreach(client=> {
            redis.set(getGiftKey(client.toString), message)
            redis.expire(getGiftKey(client.toString),KEY_EXPIRES)
        })
        redis.close()
        message
    }


    def listMessage():JSONObject = {
        val redis = getRedisInstance()
        val jsa = new JSONArray()
        listClient().foreach(client => {
            val message = redis.get(client.toString);
            val fullscreen = redis.get(getFullscreenKey(client.toString))
            val gift = redis.get(getGiftKey(client.toString))
            if(null !=message && !message.isEmpty || null !=fullscreen && !fullscreen.isEmpty || null !=gift && !gift.isEmpty)
                jsa.add(client.toString.substring(client.toString.lastIndexOf(":")+1)+":"+message+":"+fullscreen+":"+gift+":"+redis.ttl(client.toString))
        })
        redis.close()
        val jso = new JSONObject()
        jso.put("count", jsa.size())
        jso.put("messages",jsa)
        jso
    }

    def addClient(clientId:String):String = {
        val redis = getRedisInstance()
        val key = HS_TOOL_CLIENT + ":" + clientId
        if(!redis.exists(key)){
            setExpire(redis, key)
        }
        redis.close()
        clientId
    }

    private def setExpire(redis: Jedis, key: String) = {
        redis.set(key, "")
        redis.set(getFullscreenKey(key),"")
        redis.set(getGiftKey(key),"")
        redis.expire(key, KEY_EXPIRES)
        redis.expire(getFullscreenKey(key), KEY_EXPIRES)
        redis.expire(getGiftKey(key), KEY_EXPIRES)
    }

    def checkHealth(clientId:String)={
        val redis = getRedisInstance()
        val key = HS_TOOL_CLIENT + ":" + clientId
        redis.expire(key, KEY_EXPIRES)
        redis.expire(getFullscreenKey(key), KEY_EXPIRES)
        redis.expire(getGiftKey(key), KEY_EXPIRES)
        redis.close()
        clientId
    }

    def listClient():JSONArray = {
        val redis = getRedisInstance()
        val jsa = new JSONArray()
        redis.keys(HS_TOOL_CLIENT+":*").foreach(rst=>{
            jsa.add(rst)
        })
        redis.close()
        jsa
    }

    def listClients():JSONArray = {
        val redis = getRedisInstance()
        val jsa = new JSONArray()
        redis.keys(HS_TOOL_CLIENT+":*").foreach(rst=>{
            jsa.add(rst.substring(rst.lastIndexOf(":")+1)+":"+redis.ttl(rst))
        })
        redis.close()
        jsa
    }

    def consumeMessage(clientID:String):String = {
        val redis = getRedisInstance()
        var rst = ""
        val key = HS_TOOL_CLIENT+":"+clientID
        if (redis.exists(key)){
            rst = redis.get(key)
            redis.set(key,"")
            redis.expire(key,KEY_EXPIRES)
        }
        redis.close()
        rst
    }

    def consumeFullscreenMessage(clientID:String):String = {
        val redis = getRedisInstance()
        var rst = ""
        val key = HS_TOOL_CLIENT_FULLSCREEN+":"+clientID
        if (redis.exists(key)){
            rst = redis.get(key)
            redis.set(key,"")
            redis.expire(key,KEY_EXPIRES)
        }
        redis.close()
        rst
    }

    def consumeGift(clientID:String):String = {
        val redis = getRedisInstance()
        var rst = ""
        val key = HS_TOOL_CLIENT_GIFT+":"+clientID
        if (redis.exists(key)){
            rst = redis.get(key)
            redis.set(key,"")
            redis.expire(key,KEY_EXPIRES)
        }
        redis.close()
        rst
    }

    private def getFullscreenKey(key:String):String = {
        var rst = ""
        if(key!=null) rst = key.replaceAll("message","fullscreen")
        rst
    }

    private def getGiftKey(key:String):String = {
        var rst = ""
        if(key!=null) rst = key.replaceAll("message","gift")
        rst
    }
  private def getRedisInstance():Jedis = {
        var jedis:Jedis = null
        try jedis = pool.getResource
        catch {
            case e: JedisConnectionException =>{
                e.printStackTrace()
                if (jedis != null) jedis.close()
            }
        }
        jedis
    }
}
