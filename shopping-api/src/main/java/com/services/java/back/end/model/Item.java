package com.services.java.back.end.model;

import com.services.java.back.end.clientDto.ItemDto;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Item {

    private String productIdentifier;
    private float price;
    public static Item convert(ItemDto itemDto){
        Item item = new Item();
        item.setProductIdentifier(itemDto.getProductIdentifier());
        item.setPrice(itemDto.getPrice());
        return item;
    }
}
