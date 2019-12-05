package io.github.yuizho.springsandbox.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping



@RestController
@RequestMapping("api/file")
class FileDownloadController {
    val logger = LoggerFactory.getLogger(FileDownloadController::class.java)

    @GetMapping("/*.csv",
            produces = ["${MediaType.APPLICATION_OCTET_STREAM_VALUE}; charset=utf-8; Content-Disposition: attachment"])
    fun downloadCsvWithFileName(): String {
        val csv = """
            1,taro
            2,hanako
        """.trimIndent()
        logger.info(csv)
        return csv
    }

    @GetMapping("/csv")
    fun downloadCsv(): ResponseEntity<String> {
        val csv = """
            1,taro
            2,hanako
        """.trimIndent()
        logger.info(csv)
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.csv\"")
            add(HttpHeaders.CONTENT_TYPE, "${MediaType.APPLICATION_OCTET_STREAM_VALUE}; charset=utf-8;")
        }
        return ResponseEntity(csv, headers, HttpStatus.OK)
    }
}