package com.timesoccer247.Spring_TimeSoccer247.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.timesoccer247.Spring_TimeSoccer247.constants.FileType;
import com.timesoccer247.Spring_TimeSoccer247.entity.FileEntity;
import com.timesoccer247.Spring_TimeSoccer247.exception.FileException;
import com.timesoccer247.Spring_TimeSoccer247.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    private static final List<String> IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp");
    private static final List<String> VIDEO_TYPES = Arrays.asList("video/mp4", "video/avi", "video/mov", "video/mkv");

    @Value("${cloud.folder-image}")
    private String folderImage;

    @Value("${cloud.max-size-image}")
    private String maxSizeImage;

    @Value("${cloud.folder-video}")
    private String folderVideo;

    @Value("${cloud.max-size-video}")
    private String maxSizeVideo;


    private long parseSize(String size) {
        size = size.toUpperCase();
        return Long.parseLong(size.replace("MB", "").trim()) * 1024 * 1024;
    }

    public FileEntity uploadFile(MultipartFile file, FileType type) throws IOException, FileException {
        if (file == null || file.isEmpty()) {
            throw new FileException("File trống. Không thể lưu trữ file");
        }
        String folder = determineUploadFolder(file, type);

        Map<String, Object> options = ObjectUtils.asMap("folder", folder);
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

        FileEntity fileEntity = FileEntity.builder()
                .id(uploadResult.get("public_id").toString())
                .fileName(file.getOriginalFilename())
                .type(type.name())
                .url(uploadResult.get("url").toString())
                .build();

        return fileRepository.save(fileEntity);
    }

    private String determineUploadFolder(MultipartFile file, FileType type) throws FileException {
        switch (type){
            case IMAGE->{
                if (!IMAGE_TYPES.contains(file.getContentType())) {
                    throw new FileException("File " +  file.getOriginalFilename() + " không hợp lệ. Định dạng file hoặc tên file không được hỗ trợ.");
                }
                if (file.getSize() > parseSize(maxSizeImage)) {
                    throw new FileException("File quá lớn! Ảnh chỉ được tối đa " + maxSizeImage + ".");
                }
                return folderImage;
            }
            case VIDEO->{
                if (!VIDEO_TYPES.contains(file.getContentType())) {
                    throw new FileException("File " +  file.getOriginalFilename() + " không hợp lệ. Định dạng file hoặc tên file không được hỗ trợ.");
                }
                if (file.getSize() > parseSize(maxSizeVideo)) {
                    throw new FileException("File quá lớn! Ảnh chỉ được tối đa " + maxSizeVideo + ".");
                }
                return folderVideo;
            }
            default -> throw new FileException("Loại file không hỗ trợ");
        }
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(String publicId) throws Exception {
        FileEntity fileEntity = fileRepository.findById(publicId)
                .orElseThrow(()-> new FileException("File không tồn tại trong hệ thống"));

        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        fileRepository.delete(fileEntity);
    }

}

