package com.natech.demo_project.service.product;

import com.natech.demo_project.dto.ProductDto;
import com.natech.demo_project.model.Product;
import com.natech.demo_project.request.AddProductRequest;
import com.natech.demo_project.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request) throws IllegalAccessException;

    Product getProductById(Long id);

    void deleteProduct(Long id);

    Product updateProduct(ProductUpdateRequest  product, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByNameAndBrand(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> toConvertedProduct(List<Product> products);

    ProductDto convertToDto(Product product);
}
