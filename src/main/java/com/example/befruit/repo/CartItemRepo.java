package com.example.befruit.repo;

import com.example.befruit.entity.CartItem;
import com.example.befruit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    Page<CartItem> findAllByUserId(Long userId, Pageable pageable );
    Optional<CartItem> findByProductIdAndUserId(Long productId,Long userId);
}
