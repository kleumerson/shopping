package com.services.java.back.end.model;

import com.services.java.back.end.clientDto.ShopDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity(name= "shop")
@NoArgsConstructor
public class Shop {

    private Item item;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userIdentifier;
    private float total;
    private LocalDateTime date;

    @ElementCollection(fetch = FetchType.EAGER)
      @CollectionTable(name = "item",
            joinColumns = @JoinColumn(name = "shop_id"))
    private List<Item> items;

    public static Shop convert(ShopDto shopDto){
        Shop shop = new Shop();
        shop.setUserIdentifier(shopDto.getUserIdentifier());
        shop.setTotal(shopDto.getTotal());
        shop.setDate(shopDto.getDate());
        if (shopDto.getItems() != null){
            shop.setItems(shopDto
                    .getItems()
                    .stream()
                    .map(Item::convert)
                    .collect(Collectors.toList()));
        }

        return shop;
    }
}
