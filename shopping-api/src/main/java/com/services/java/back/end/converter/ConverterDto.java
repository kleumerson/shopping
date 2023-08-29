package com.services.java.back.end.converter;

import com.services.java.back.end.clientDto.ItemDto;
import com.services.java.back.end.clientDto.ShopDto;
import com.services.java.back.end.model.Item;
import com.services.java.back.end.model.Shop;

import java.util.stream.Collectors;

public class ConverterDto {

    public static ShopDto convert(Shop shop){
        ShopDto shopDto = new ShopDto();
        shopDto.setUserIdentifier(shop.getUserIdentifier());
        shopDto.setTotal(shop.getTotal());
        shopDto.setDate(shop.getDate());
        shopDto.setItems(shop
                .getItems()
                .stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList()));
        return shopDto;
    }

    public static ItemDto convert(Item item){
        ItemDto itemDto = new ItemDto();
        itemDto.setProductIdentifier(item.getProductIdentifier());
        itemDto.setPrice(item.getPrice());
        return itemDto;
    }

}
