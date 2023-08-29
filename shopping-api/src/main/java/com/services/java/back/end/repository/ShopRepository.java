package com.services.java.back.end.repository;

import com.services.java.back.end.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ReportRepository {

     List<Shop> findAllByUserIdentifier(String uerIdentifier);
     //List<Shop> findAllByDateGreaterThanEquals(LocalDateTime date);



}
