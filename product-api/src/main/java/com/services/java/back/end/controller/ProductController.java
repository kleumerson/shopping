package com.services.java.back.end.controller;

import com.services.java.back.end.clientDto.ProductDto;
import com.services.java.back.end.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getProducts(){
        return productService.getAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDto> getProductByCategory(
            @PathVariable Long categoryId){
        return productService.getProductByCategoriaId(categoryId);
    }

    @GetMapping("/{productIdentifier}")
    public ProductDto findById(
            @PathVariable String productidentifier){
        return productService.findByProductIdentifier(productidentifier);
    }

    @PostMapping
    public ProductDto newProduct(
            @Valid @RequestBody ProductDto productDTO){
        return productService.save(productDTO);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id){
        return productService.delete(id);
    }

    @PostMapping("/{id}")
    public ProductDto editProduct(
            @PathVariable Long id, ProductDto productDTO){
        return productService.editProduct(id, productDTO);
    }

    @GetMapping("/pageable")
    public Page<ProductDto> getProductPage(Pageable pageable){
        return productService.getAllPage(pageable);
    }
}
