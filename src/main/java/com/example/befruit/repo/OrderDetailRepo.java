package com.example.befruit.repo;

import com.example.befruit.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail,Long> {
    @Query("select o from OrderDetail o where o.bill.id=:billId")
List<OrderDetail> findAllByBillId(@Param("billId") Long id);
}
