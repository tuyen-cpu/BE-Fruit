package com.example.befruit.repo;

import com.example.befruit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//	   @Query("SELECT p FROM Product p WHERE p.category.id = ?1 and p.price<=?2")
	Page<Product> findAllByCategoryIdAndPriceLessThanEqualAndStatus(Long id, Long price,Integer status, Pageable pageable);
	Page<Product> findAllByCategorySlugAndPriceLessThanEqualAndStatus(String slug, Long price,Integer status, Pageable pageable);
	//	   @Query("SELECT p FROM Product p WHERE p.price<=?1")
	Page<Product> findAllByPriceLessThanEqualAndStatus(Long price,Integer status, Pageable pageable);
	Page<Product> findAllByCategoryIdAndPriceLessThanEqual(Long id, Long price, Pageable pageable);
	Page<Product> findAllByCategorySlugAndPriceLessThanEqual(String slug, Long price, Pageable pageable);
	//	   @Query("SELECT p FROM Product p WHERE p.price<=?1")
	Page<Product> findAllByPriceLessThanEqual(Long price, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE %?1%  or upper(p.category.name) like %?1% ")
	Page<Product> findAllByNameOrCategoryName(String q, Pageable pageable);
	Product findBySlug(String slug);

}
