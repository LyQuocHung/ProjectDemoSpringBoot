package com.natech.demo_project.service.image;

import com.natech.demo_project.dto.ImageDto;
import com.natech.demo_project.exceptions.ResourNotFoundException;
import com.natech.demo_project.model.Image;
import com.natech.demo_project.model.Product;
import com.natech.demo_project.repository.ImageRepository;
import com.natech.demo_project.repository.ProductRepository;
import com.natech.demo_project.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourNotFoundException("Not image found with id : " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository
                .findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourNotFoundException("Not image found with id : " + id);
                });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/image/downnload/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDowloandUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDowloandUrl());


                savedImageDto.add(imageDto);
                imageRepository.save(savedImage);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public Image updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileType(file.getContentType());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
