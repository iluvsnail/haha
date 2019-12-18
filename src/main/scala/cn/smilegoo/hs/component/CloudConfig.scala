package cn.smilegoo.hs.component

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties
class CloudConfig {
    @Value("${redis.host.ip}")
    val redisIP:String = null
    @Value("${redis.host.port}")
    val redisPort:Int = 0
    @Bean
    def getRestTemplate: RestTemplate = {
        new RestTemplate()
    }

    @Bean
    def getRedisIP:String = {
        redisIP
    }
    @Bean
    def getRedisPort:Int = {
        redisPort
    }
}
