package com.services.java.back.end.repository;

import com.services.java.back.end.model.Shop;
import com.services.java.back.end.shoppingDto.ShopReportDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {

    public List<Shop> getShopByFilters(
            LocalDate dateInicio,
            LocalDate dateFim,
            Float valorMinimo);

    public ShopReportDto getReportByDate(
            LocalDate dateInicio,
            LocalDate dateFim);
}
