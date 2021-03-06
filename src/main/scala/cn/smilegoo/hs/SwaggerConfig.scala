package cn.smilegoo.hs

import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.{PathSelectors, RequestHandlerSelectors}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

  import org.springframework.context.annotation.Bean

  @Bean def api: Docket = new Docket(DocumentationType.SWAGGER_2)
    .select
    .apis(RequestHandlerSelectors.basePackage("cn.smilegoo.hs"))
    .paths(PathSelectors.any())
    .build

}
