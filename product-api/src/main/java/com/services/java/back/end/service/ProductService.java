package com.services.java.back.end.service;

import com.services.java.back.end.clientDto.ProductDto;
import com.services.java.back.end.exception.CategoryNotFoundException;
import com.services.java.back.end.exception.ProductNotFoundException;
import com.services.java.back.end.converter.ConverterDto;
import com.services.java.back.end.model.Product;
import com.services.java.back.end.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${PRODUCT_API_URL:http://localhost:8081}")
    private String productApiURL;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductByCategoriaId(Long categoryId) {
        List<Product> products = productRepository.getProductByCategory(categoryId);
        return products.stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    public ProductDto findByProductIdentifier(String productIdentifier) {
        Product product = productRepository
                .findByProductIdentifier(productIdentifier);
        if (product != null) {
            return ConverterDto.convert(product);
        }
        throw new ProductNotFoundException();
    }

    public ProductDto save(ProductDto productDTO) {
        Boolean existsCategory = productRepository
                .existsById(productDTO.getCategory().getId());
        if (!existsCategory) {
            throw new CategoryNotFoundException();
        }
        Product product =
                productRepository.save(Product.convert(productDTO));
        return ConverterDto.convert(product);
    }

    public ProductDto delete(long productId) {
        Optional<Product> product =
                productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.delete(product.get());
        }
        throw new ProductNotFoundException();
    }

    public ProductDto editProduct(long id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getNome() != null || !dto.getNome().isEmpty()) {
            product.setNome(dto.getNome());
        }

        if (dto.getPreco() != null) {
            product.setPreco(dto.getPreco());
        }

        return ConverterDto.convert(productRepository.save(product));

    }

    public Page<ProductDto> getAllPage(Pageable page) {
        Page<Product> products = productRepository.findAll(page);
        return products
                .map(ConverterDto::convert);
    }

    public ProductDto getProductByIndentifier(String productIdentifier) {
        try {

            WebClient webClient = WebClient
                    .builder()
                    .baseUrl(productApiURL)
                    .build();
            Mono<ProductDto> productDTOMono = webClient.get()
                    .uri("/product/" + productIdentifier)
                    .retrieve()
                    .bodyToMono(ProductDto.class);
            return productDTOMono.block();

        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }
}
