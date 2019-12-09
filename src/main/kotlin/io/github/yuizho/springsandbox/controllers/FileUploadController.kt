package io.github.yuizho.springsandbox.controllers

import io.github.yuizho.springsandbox.FileProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@RestController
@RequestMapping("api/file/upload")
class FileUploadController(
        val fileProperties: FileProperties
) {
    @GetMapping("/test/{fileName}")
    fun upload(@PathVariable fileName: String): String {
        val folderPath = FileSystems.getDefault().getPath(fileProperties.path)
        Files.createDirectories(folderPath)
        val filePath = folderPath.resolve(Path.of(fileName))
        // https://www.ne.jp/asahi/hishidama/home/tech/java/files.html
        Files.writeString(filePath, "content", StandardOpenOption.CREATE_NEW)

        return Files.readString(filePath)
    }
}