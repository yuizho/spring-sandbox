package io.github.yuizho.springsandbox.controllers.rest

import io.github.yuizho.springsandbox.FileProperties
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.*

@RestController
@RequestMapping("api/upload")
class FileUploadController(
        val fileProperties: FileProperties
) {
    @PostMapping("/stream/{fileName}")
    fun uploadHandledByStream(
            @PathVariable fileName: String,
            @RequestParam("file") file: MultipartFile
    ): String {
        val folderPath = FileSystems.getDefault().getPath(fileProperties.path)
        Files.createDirectories(folderPath)
        val filePath = folderPath.resolve(Paths.get(fileName))

        file.resource.inputStream.use { input ->
            Files.newOutputStream(filePath).buffered().use { output ->
                input.copyTo(output)
            }
        }

        return "OK"
    }

    @PostMapping("/array/{fileName}")
    fun uploadHandledByArray(
            @PathVariable fileName: String,
            @RequestParam("file") file: MultipartFile
    ): String {
        val folderPath = FileSystems.getDefault().getPath(fileProperties.path)
        Files.createDirectories(folderPath)
        val filePath = folderPath.resolve(Paths.get(fileName))

        Files.write(filePath, file.bytes, StandardOpenOption.WRITE)
        return "OK"
    }
}