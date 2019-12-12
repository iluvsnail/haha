package cn.gfire.gdp.cloud.resource.classifier.component

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties
class CloudConfig {
    @Bean
    def getRestTemplate: RestTemplate = {
        new RestTemplate()
    }
}
