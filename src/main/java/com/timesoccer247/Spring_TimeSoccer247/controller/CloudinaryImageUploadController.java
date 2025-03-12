package com.timesoccer247.Spring_TimeSoccer247.controller;

import com.timesoccer247.Spring_TimeSoccer247.constants.FileType;
import com.timesoccer247.Spring_TimeSoccer247.entity.FileEntity;
import com.timesoccer247.Spring_TimeSoccer247.exception.FileException;
import com.timesoccer247.Spring_TimeSoccer247.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class CloudinaryImageUploadController {

    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileEntity> uploadImage(@RequestParam("image") MultipartFile file) throws IOException, FileException {
        return new ResponseEntity<FileEntity>(cloudinaryService.uploadFile(file, FileType.IMAGE), HttpStatus.OK);
    }

    @PostMapping(value = "/upload/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileEntity> uploadVideo(@RequestParam("fileVideo") MultipartFile file) throws IOException, FileException {
        return new ResponseEntity<FileEntity>(cloudinaryService.uploadFile(file, FileType.VIDEO), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        return ResponseEntity.ok(cloudinaryService.getAllFiles());
    }

    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String publicId) throws Exception {
        cloudinaryService.deleteFile(publicId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

