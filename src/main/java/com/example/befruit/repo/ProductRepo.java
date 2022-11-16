package com.example.befruit.repo;

import com.example.befruit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	//    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
	Page<Product> findAllByCategoryIdAndPriceLessThanEqual(Long id, Long price, Pageable pageable);

	Page<Product> findAllByPriceLessThanEqual(Long price, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE %?1%  or upper(p.category.name) like %?1% ")
	Page<Product> findAllByNameOrCategoryName(String q, Pageable pageable);
}
