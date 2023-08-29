package com.services.java.back.end.clientDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank(message = "Is important inform the field name")
    private String nome;
    @NotNull
    private Float preco;
    @NotBlank(message = "Is important inform the field description")
    private String descricao;
    @NotNull
    private String productIdentifier;
    @NotNull
    private CategoryDto category;
}
