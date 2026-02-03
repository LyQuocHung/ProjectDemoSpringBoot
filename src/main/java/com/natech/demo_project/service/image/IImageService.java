package com.natech.demo_project.service.image;

import com.natech.demo_project.dto.ImageDto;
import com.natech.demo_project.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    Image updateImage(MultipartFile file, Long imageId);
}
