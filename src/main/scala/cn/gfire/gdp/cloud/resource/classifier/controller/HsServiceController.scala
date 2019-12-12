package cn.gfire.gdp.cloud.resource.classifier.controller

import cn.gfire.gdp.cloud.resource.classifier.service.HsService
import io.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation}
import com.alibaba.fastjson.{JSONArray, JSONObject}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.web.bind.annotation._
import org.springframework.web.client.RestTemplate



@Api(value = "Hs tool micro services api",tags=Array("Hs tool services api"))
@RestController
@RequestMapping(Array("/hs-tool/api/v1/"))
class HsServiceController {

  @Autowired
  val restTemplate:RestTemplate=null
  @Autowired
  val hsService:HsService = null

  @ApiImplicitParams(
    Array(
    )
  )
  @ApiOperation(value = "获取当前主播ID",
    notes = "")
  @RequestMapping(value = Array("/current/cast"),method = Array(RequestMethod.GET))
  def currentCast():String ={
    hsService.getCurrentCast()
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "cast", value = "新的主播ID", required = true,dataType = "string",  paramType = "path",defaultValue = "test")
    )
  )
  @ApiOperation(value = "设置新的主播ID",
    notes = "")
  @RequestMapping(value = Array("/current/cast/{cast}"),method = Array(RequestMethod.POST),produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def resetCast(@PathVariable cast:String):String ={
    hsService.setCurrentCast(cast)
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "clientID", value = "帐号（小号）", required = true,dataType = "string",  paramType = "path",defaultValue = "test")
    )
  )
  @ApiOperation(value = "添加新的帐号（小号）",
    notes = "")
  @RequestMapping(value = Array("/client/add/{clientID}"),method = Array(RequestMethod.GET))
  def addClient(@PathVariable clientID:String):String ={
    hsService.addClient(clientID)
  }

  @ApiImplicitParams(
    Array(
    )
  )
  @ApiOperation(value = "获取所有帐号",
    notes = "")
  @RequestMapping(value = Array("/client/all"),method = Array(RequestMethod.POST),produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def listAllClient():ResponseEntity[JSONArray] ={
    val responseEntity = new ResponseEntity[JSONArray](hsService.listClients(),HttpStatus.OK)
    responseEntity
  }

  @ApiImplicitParams(
    Array(
    )
  )
  @ApiOperation(value = "获取所有帐号要发送的消息",
    notes = "")
  @RequestMapping(value = Array("/client/message/all"),method = Array(RequestMethod.POST),produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def listAllMessage():ResponseEntity[JSONObject] ={
    val responseEntity = new ResponseEntity[JSONObject](hsService.listMessage(),HttpStatus.OK)
    responseEntity
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "message", value = "消息", required = true,dataType = "string",  paramType = "path",defaultValue = "test")
    )
  )
  @ApiOperation(value = "发送新消息",
    notes = "")
  @RequestMapping(value = Array("/message/send/{message}"),method = Array(RequestMethod.POST))
  def sendMessage(@PathVariable message:String):String = {
    hsService.sendMessage(message)
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "clientID", value = "帐号（小号）", required = true,dataType = "string",  paramType = "path",defaultValue = "test")
    )
  )
  @ApiOperation(value = "客户端消费消息",
    notes = "")
  @RequestMapping(value = Array("/client/{clientID}/message/consume"),method = Array(RequestMethod.GET))
  def comsumeMessage(@PathVariable clientID:String):String = {
    hsService.consumeMessage(clientID)
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "clientID", value = "帐号（小号）", required = true,dataType = "string",  paramType = "path",defaultValue = "test")
    )
  )
  @ApiOperation(value = "健康检测",
    notes = "")
  @RequestMapping(value = Array("/client/{clientID}/checkhealth"),method = Array(RequestMethod.GET))
  def checkHealth(@PathVariable clientID:String):String = {
    hsService.checkHealth(clientID)
  }
/*  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "serverId", value = "请求服务ID", required = true,dataType = "string",  paramType = "path",defaultValue = "server-2134BCE"),
      new ApiImplicitParam(name = "resourceId", value = "resourceId", required = true,dataType = "string", paramType = "path",defaultValue = "linux_test1")
    )
  )
  @ApiOperation(value = "Classify the resouce",
    notes = "Classify the resouce given server id and resource id")
  @RequestMapping(value = Array("/{serverId}/evaluate/{resourceId}"),method = Array(RequestMethod.POST),produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def evaluateResource(@PathVariable serverId:String,@PathVariable resourceId:String):ResponseEntity[JSONObject] ={
    val responseEntity = new ResponseEntity[JSONObject](classifierService.predictResource(resourceId),HttpStatus.OK)
    responseEntity
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "serverId", value = "请求服务ID", required = true,dataType = "string",  paramType = "path",defaultValue = "server-2134BCE"),
      new ApiImplicitParam(name = "resourceId", value = "resourceId", required = true,dataType = "string", paramType = "path",defaultValue = "game"),
      new ApiImplicitParam(name = "standardTypeId", value = "标准分类编号", required = true,dataType = "string", paramType = "path",defaultValue = "g_123")
    )
  )
  @ApiOperation(value = "Classify the resouce",
    notes = "Classify the resouce given server id 、 resource id and type id")
  @RequestMapping(value = Array("/{serverId}/train/{resourceId}/{standardTypeId}"),method = Array(RequestMethod.POST),produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def trainMLModel(@PathVariable serverId:String,@PathVariable resourceId:String,@PathVariable standardTypeId:String):ResponseEntity[Boolean] ={
    val responseEntity = new ResponseEntity[Boolean](classifierService.trainMLModel(resourceId,standardTypeId),HttpStatus.OK)
    responseEntity
  }

  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "serverId", value = "请求服务ID", required = true,dataType = "string",  paramType = "path",defaultValue = "server-2134BCE")
    )
  )
  @ApiOperation(value = "reload the lr model",
    notes = "reload the lr model given server id")
  @RequestMapping(value = Array("/{serverId}/train/model/reload"),method = Array(RequestMethod.POST),produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def reloadModel(@PathVariable serverId:String):ResponseEntity[Boolean] ={
   // val responseEntity = new ResponseEntity[Boolean](classifierService.reloadModel(),HttpStatus.OK)
    //responseEntity
  }*/
}
