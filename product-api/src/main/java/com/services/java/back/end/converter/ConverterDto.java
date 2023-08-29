package com.services.java.back.end.converter;

import com.services.java.back.end.clientDto.CategoryDto;
import com.services.java.back.end.clientDto.ProductDto;
import com.services.java.back.end.model.Category;
import com.services.java.back.end.model.Product;

public class ConverterDto {

    public static ProductDto convert(Product product) {
        ProductDto productDTO = new ProductDto();
        productDTO.setNome(product.getNome());
        productDTO.setPreco(product.getPreco());
        if (productDTO.getCategory() != null) {
            productDTO.setCategory(
                    ConverterDto.convert(product.getCategory()));
        }
        return productDTO;
    }

    public static CategoryDto convert(Category category) {
        CategoryDto categoryDTO = new CategoryDto();
        categoryDTO.setId(category.getId());
        categoryDTO.setNome(category.getNome());
        return categoryDTO;
    }
}
