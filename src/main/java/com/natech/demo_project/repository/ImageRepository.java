package com.natech.demo_project.repository;

import com.natech.demo_project.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
