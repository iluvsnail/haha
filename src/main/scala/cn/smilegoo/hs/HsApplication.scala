package cn.smilegoo.hs

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
class HsApplication {
}

object HsApplication {

	def  main(args: Array[String]): Unit = {
		SpringApplication.run(classOf[HsApplication])
	}
}