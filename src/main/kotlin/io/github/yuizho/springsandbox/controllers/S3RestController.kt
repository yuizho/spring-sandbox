package io.github.yuizho.springsandbox.controllers

import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/s3")
class S3RestController(
        val resourcePatternResolver: ResourcePatternResolver
) {

    @GetMapping("/resources/{name}")
    fun downloadSingle(@PathVariable name: String): String {
        // s3://test/testの形式だとs3Mockはアクセスできんかった
        val resource = resourcePatternResolver.getResource("http://localhost:9090/test/$name")
        return resource.inputStream.bufferedReader().use { it.readText() }
    }

    @GetMapping("/resources")
    fun download(): List<String> {
        // s3://test/testの形式だとs3Mockはアクセスできんかった
        // TODO: まだうまく取得出来ていない
        val resources = resourcePatternResolver.getResources("http://localhost:9090/test/*")
        return resources.map { resource -> resource.inputStream.bufferedReader().use { it.readText() } }
    }
}