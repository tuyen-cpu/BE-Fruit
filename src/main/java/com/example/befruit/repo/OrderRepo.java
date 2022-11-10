package com.example.befruit.repo;

import com.example.befruit.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Bill,Long> {


}
