package com.example.befruit.repo;

import com.example.befruit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    Page<Product> findAllByCategoryIdAndPriceLessThanEqual(Long id,Long price,Pageable pageable );
    Page<Product> findAllByPriceLessThanEqual(Long price,Pageable pageable );
}
