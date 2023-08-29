package com.services.java.back.end.repository;

import com.services.java.back.end.model.Shop;
import com.services.java.back.end.shoppingDto.ShopReportDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Shop> getShopByFilters(LocalDate dateInicio, LocalDate dateFim, Float valorMinimo) {

        StringBuilder sb = new StringBuilder();
        sb.append("select s ");
        sb.append("from shop s ");
        sb.append("where s.date >=:dateInicio ");

        if (dateFim != null) {
            sb.append(" and s.date <= :dateFim ");
        }

        if (valorMinimo != null) {
            sb.append(" and s.total <= :valorMinimo ");
        }

        Query query = entityManager.createQuery(sb.toString());
        query.setParameter("dateInicio", dateInicio.atTime(0, 0));

        if (dateFim != null) {
            query.setParameter("dateFim", dateFim.atTime(23, 59));
        }

        if (valorMinimo != null) {
            query.setParameter("valorMinimo", valorMinimo);
        }

        return query.getResultList();
    }

    @Override
    public ShopReportDto getReportByDate(LocalDate dateInicio, LocalDate dateFim) {

        StringBuilder sb = new StringBuilder();
        sb.append("select count(sp.id), sum(sp.total), avg(sp.total) ");
        sb.append("from shopping.shop sp ");
        sb.append("where sp.date >= :dateInicio ");
        sb.append("and sp.date <= :dateFim ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("dateInicio", dateInicio.atTime(0, 0));
        query.setParameter("dateFim", dateFim.atTime(23, 59));

        Object[] result = (Object[]) query.getSingleResult();
        Integer i = new BigInteger(result[0].toString()).intValue();
        ShopReportDto shopReportDto = new ShopReportDto();
        shopReportDto.setCount(new BigInteger(result[0].toString()).intValue());
        shopReportDto.setTotal((Double) result[1]);
        shopReportDto.setMean((Double) result[2]);

        return shopReportDto;
    }
}
