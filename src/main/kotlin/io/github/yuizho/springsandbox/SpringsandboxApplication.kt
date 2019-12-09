package io.github.yuizho.springsandbox;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.runApplication

@EnableConfigurationProperties(FileProperties::class)
@SpringBootApplication
class SpringsandboxApplication

fun main(args: Array<String>) {
    runApplication<SpringsandboxApplication>(*args)
}

