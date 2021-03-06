package io.github.yuizho.springsandbox.controllers.rest

import io.github.yuizho.springsandbox.FileProperties
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


@RestController
@RequestMapping("api/file")
class FileDownloadController(
        val fileProperties: FileProperties
) {
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

    @GetMapping("/zip")
    fun downloadZip(): ResponseEntity<StreamingResponseBody> {
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.zip\"")
            add(HttpHeaders.CONTENT_TYPE, "application/zip;")
        }
        return ResponseEntity.ok()
                .headers(headers)
                .body(
                        // https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/StreamingResponseBody.html
                        // Streamで返却する (非同期で処理されるためTaskExecutorを明示的に構成したほうが良いらしい)
                        StreamingResponseBody { outputStream ->
                            ZipOutputStream(outputStream).use { zipOutputStream ->
                                // 圧縮する１つ目のファイル
                                val entry1 = ZipEntry("test/file1.txt")
                                val file1 = "これはfile1です".toByteArray()
                                entry1.size = file1.toUByteArray().size.toLong()
                                zipOutputStream.putNextEntry(entry1)
                                zipOutputStream.write(file1)
                                // 圧縮する２つ目のファイル
                                val entry2 = ZipEntry("test/file2.txt")
                                val file2 = "これはfile2です".toByteArray()
                                entry2.size = file2.toUByteArray().size.toLong()
                                zipOutputStream.putNextEntry(entry2)
                                zipOutputStream.write(file2)
                            }
                        }
                )
    }
}