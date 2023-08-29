package com.services.java.back.end.service;

import com.services.java.back.end.clientDto.ItemDto;
import com.services.java.back.end.clientDto.ProductDto;
import com.services.java.back.end.clientDto.ShopDto;
import com.services.java.back.end.clientDto.UserDto;
import com.services.java.back.end.model.Shop;
import com.services.java.back.end.repository.ShopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private ShopRepository shopRepository;

    @Test
    public void testSaveShop() {

        ItemDto itemDto = new ItemDto();
        itemDto.setProductIdentifier("123");
        itemDto.setPrice(100F);

        ShopDto shopDto = new ShopDto();
        shopDto.setUserIdentifier("123");
        shopDto.setItems(new ArrayList<>());
        shopDto.getItems().add(itemDto);
        shopDto.setTotal(100F);

        ProductDto productDTO = new ProductDto();
        productDTO.setProductIdentifier("123");
        productDTO.setPreco(100F);

        Mockito.when(userService.getUserByCpf("123", "123"))
                .thenReturn(new UserDto());

        Mockito.when(productService.getProductByIndentifier("123"))
                .thenReturn(productDTO);

        Mockito.when(shopRepository.save(Mockito.any()))
                .thenReturn(Shop.convert(shopDto));

        shopDto = shopService.save(shopDto, "123");
        Assertions.assertEquals(100F, shopDto.getTotal());
        Assertions.assertEquals(1, shopDto.getItems().size());
        Mockito.verify(shopRepository, Mockito.times(1)).save((Mockito.any()));

    }


}