package com.example.befruit.repo;

import com.example.befruit.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	List<Category> findAllByStatus(Integer status);
}
