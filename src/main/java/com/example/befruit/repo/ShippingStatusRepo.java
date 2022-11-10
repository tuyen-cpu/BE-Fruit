package com.example.befruit.repo;

import com.example.befruit.entity.ShippingStatus;
import com.example.befruit.service.impl.ShippingStatusService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingStatusRepo extends JpaRepository<ShippingStatus,Long> {
    ShippingStatus findByName(String name);
}
