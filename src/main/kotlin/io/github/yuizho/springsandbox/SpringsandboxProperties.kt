package io.github.yuizho.springsandbox

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix="file")
class FileProperties {

    lateinit var path: String
}