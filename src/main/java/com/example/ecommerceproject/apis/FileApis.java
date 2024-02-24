package com.example.ecommerceproject.apis;

import com.example.ecommerceproject.payload.response.ResponseObject;
import com.example.ecommerceproject.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileApis {

    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam(name = "image_id") String imageId) throws IOException {
        ByteArrayResource byteArrayResource = fileService.readContentFile(imageId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(byteArrayResource);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileService.saveFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Save image successfully")
                        .data(url)
                        .httpStatus(HttpStatus.OK)
                        .build()
        );
    }
}
