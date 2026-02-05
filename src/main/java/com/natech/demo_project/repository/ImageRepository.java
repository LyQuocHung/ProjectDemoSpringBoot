package com.natech.demo_project.repository;

import com.natech.demo_project.model.Image;
import com.natech.demo_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductById(Long id);

}
