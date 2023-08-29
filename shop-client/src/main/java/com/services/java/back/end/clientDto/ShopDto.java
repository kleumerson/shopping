package com.services.java.back.end.clientDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    private Long id;
    @NotBlank
    private String userIdentifier;
    @NotNull
    private float total;
    private LocalDateTime date;
    @NotNull
    private List<ItemDto> items;

}
