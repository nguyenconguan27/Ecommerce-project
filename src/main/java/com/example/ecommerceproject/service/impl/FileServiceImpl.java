package com.example.ecommerceproject.service.impl;

import com.example.ecommerceproject.util.Constants;
import com.example.ecommerceproject.service.FileService;
import com.example.ecommerceproject.util.FileUploadUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public ByteArrayResource readContentFile(String imageId) throws IOException {
                Path fileName = Paths.get(Constants.pathImage, imageId);
                byte[] buffer = Files.readAllBytes(fileName);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return byteArrayResource;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
            Path pathFile = FileUploadUtil.save(file, Constants.pathImage);
            String pathStr = pathFile.toString().replace("\\", "/");
            String urlImage = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(pathStr)
                    .toUriString();
            return pathStr.substring(pathStr.lastIndexOf("/")+1);

    }
}
