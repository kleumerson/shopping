package com.services.java.back.end.controller;

import com.services.java.back.end.clientDto.ShopDto;
import com.services.java.back.end.service.ShopService;
import com.services.java.back.end.shoppingDto.ShopReportDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/shopping")
    public List<ShopDto> getShops() {
        return shopService.getAll();
    }

    @GetMapping("/shopping/shopByUser/{userIdentifier}")
    public List<ShopDto> getShops(@PathVariable String userIdentifier) {
        System.out.print(userIdentifier);
        return shopService.getByUser(userIdentifier);
    }

    /*
    @GetMapping("/shopping/shopByDate")
    public List<ShopDto> getShops(@RequestBody ShopDto shopDto){
        return shopService.getByDate(shopDto);
    }
    */

    @GetMapping("/shopping/{id}")
    public ShopDto findById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PostMapping("/shopping")
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDto newShop(
             @Valid @RequestBody ShopDto shopDto,
             @RequestHeader(name="key", required = true) String key) {

        return shopService.save(shopDto, key);

    }

    @GetMapping("/shopping/search")
    public List<ShopDto> getShopsByFilter(@RequestParam(name = "dataInicio", required = true)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateInicio,
                                          @RequestParam(name = "dataFim", required = true)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFim,
                                          Float valorMinimo) {
        return shopService.getShopsByFilter(dateInicio, dateFim, valorMinimo);
    }

    @GetMapping("/shopping/report")
    public ShopReportDto getReportByDate(@RequestParam(name = "dataInicio", required = true)
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateInicio,
                                         @RequestParam(name = "dataFim", required = true)
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFim) {
        return shopService.getReportByDate(dateInicio, dateFim);
    }

}
