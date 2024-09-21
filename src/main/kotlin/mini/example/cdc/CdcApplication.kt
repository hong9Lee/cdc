package mini.example.cdc

import mini.example.cdc.config.RedisProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableConfigurationProperties(RedisProperties::class)
@EnableAsync
class CdcApplication

fun main(args: Array<String>) {
	runApplication<CdcApplication>(*args)
}
