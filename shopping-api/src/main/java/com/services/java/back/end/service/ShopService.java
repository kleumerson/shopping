package com.services.java.back.end.service;

import com.services.java.back.end.clientDto.ItemDto;
import com.services.java.back.end.clientDto.ProductDto;
import com.services.java.back.end.clientDto.ShopDto;
import com.services.java.back.end.converter.ConverterDto;
import com.services.java.back.end.exception.ProductNotFoundException;
import com.services.java.back.end.exception.UserNotFoundException;
import com.services.java.back.end.model.*;
import com.services.java.back.end.repository.*;
import com.services.java.back.end.shoppingDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public ShopService(ShopRepository shopRepository, ProductService productService, UserService userService) {
        this.shopRepository = shopRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<ShopDto> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDto> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository
                .findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    /*
   public List<ShopDto> getByDate(ShopDto shopDto){
       List<Shop> shops = shopRepository
               .findAllByDateGreaterThanEquals(shopDto.getDate());
       return shops
               .stream()
               .map(ShopDto::convert)
               .collect(Collectors.toList());
   }

    */
    public ShopDto findById(Long ProductId) {
        Optional<Shop> shop = shopRepository
                .findById(ProductId);
        if (shop.isPresent()) {
            return ConverterDto.convert(shop.get());
        }
        throw new ProductNotFoundException();
    }

    public ShopDto save(ShopDto shopDto, String key) {

        if (userService.getUserByCpf(shopDto.getUserIdentifier(), key) == null){
            throw new UserNotFoundException();
        }

        if (!validateProducts(shopDto.getItems())){
            throw new ProductNotFoundException();
        }

        shopDto.setTotal(shopDto.
                getItems()
                .stream()
                .map(x -> x.getPrice())
                .reduce((float) 0, Float::sum));

        Shop shop = Shop.convert(shopDto);
        shop.setDate(LocalDateTime.now());

        shop = shopRepository.save(shop);

        return ConverterDto.convert(shop);
    }

    private boolean validateProducts(List<ItemDto> items) {
        for (ItemDto item:items){
            ProductDto productDTO = productService
                    .getProductByIndentifier(item.getProductIdentifier());
            if (productDTO == null){
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }

    public List<ShopDto> getShopsByFilter(LocalDate dateInicio, LocalDate dateFim, Float valorMinimo) {
        List<Shop> shops =
                shopRepository.getShopByFilters(dateInicio, dateFim, valorMinimo);
        return shops
                .stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    public ShopReportDto getRepodtByDate(LocalDate dateInicio, LocalDate dateFim) {
        return shopRepository
                .getReportByDate(dateInicio, dateFim);
    }

    @GetMapping("/shopping/report")
    public ShopReportDto getReportByDate(@RequestParam(name = "dataInicio", required = true)
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateInicio,
                                         @RequestParam(name = "dataFim", required = true)
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFim){
        return shopRepository
                .getReportByDate(dateInicio, dateFim);
    }
}
