package com.example.befruit.repo;

import com.example.befruit.entity.Bill;
import com.example.befruit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Bill, Long> , JpaSpecificationExecutor<Bill> {

//	@Query("select b from Bill b where b.address.user.id=:userId and b.status=:status")
	Page<Bill> findAllByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status, Pageable pageable);
	Page<Bill> findAllByStatus( Integer status, Pageable pageable);

}
