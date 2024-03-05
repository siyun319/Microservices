package com.siyun.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.siyun.productservice.dto.ProductRequest;
import com.siyun.productservice.dto.ProductResponse;
import com.siyun.productservice.model.Product;
import com.siyun.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                            .name(productRequest.getName())
                            .description(productRequest.getDescription())
                            .price(productRequest.getPrice())
                            .build();

        productRepository.save(product);

        log.info("Product " + product.getId() + " is saved");
        
    }

    public List<ProductResponse> getAllProducts() {
        
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> mapProductResponse(product)).toList();
    }

    private ProductResponse mapProductResponse(Product product) {
        return ProductResponse.builder()
                            .id(product.getId())
                            .description(product.getDescription())
                            .name(product.getName())
                            .price(product.getPrice())
                            .build();
    }
    
}
