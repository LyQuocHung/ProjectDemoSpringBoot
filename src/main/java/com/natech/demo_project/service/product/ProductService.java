package com.natech.demo_project.service.product;

import com.natech.demo_project.exceptions.ProductNotFoundException;
import com.natech.demo_project.model.Category;
import com.natech.demo_project.model.Product;
import com.natech.demo_project.repository.CategoryRepository;
import com.natech.demo_project.repository.ProductRepository;
import com.natech.demo_project.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.natech.demo_project.request.AddProductRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Product addProduct(AddProductRequest request) throws IllegalAccessException {
        if (request == null) {
            throw new IllegalAccessException("Request cannot be null");
        }
        if (request.getCategory() == null || request.getCategory().getName() == null) {
            throw new IllegalAccessException("Category cannot be null");
        }
        Category category = getOrCreateCategory(request.getCategory().getName());
        request.setCategory(category);
        //create new product
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    private Category getOrCreateCategory(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name))
                .orElseGet(() -> {
                    Category newCategory = new Category(name);
                    return categoryRepository.save(newCategory);
                });
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product not found!");
                        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        //Update attributes
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());
        //Update category ( Find by name and update )
        Category category = getOrCreateCategory(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByNameAndBrand(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductByBrandAndName(brand, name);
    }


}
