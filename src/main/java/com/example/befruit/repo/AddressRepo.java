package com.example.befruit.repo;


import com.example.befruit.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
	Page<Address> findAllByUserIdAndStatus(Long id, Integer status, Pageable pageable);
}
