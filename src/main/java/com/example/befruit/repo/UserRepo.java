package com.example.befruit.repo;

import com.example.befruit.entity.Product;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

//@Repository
public interface UserRepo extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
	User findByVerificationCode(String code);

	User findByEmail(String email);

	User findByUserNameAndStatus(String userName, Integer status);

	User findByEmailAndStatus(String userName, Integer status);

	boolean existsByUserName(String username);

	boolean existsByEmail(String email);

	User findByToken(String token);
	@Query("select count(u) from User u join u.roles r where r.name=?1")
	Integer countByRoleName(String name);

	@Query("select count(b) from User b join b.roles r where r.name='client'")
	Integer totalOrders();
	@Query("select count(b) from User b join b.roles r where r.name='client' and cast(b.createdAt as date) = cast(?1 as date)")
	Integer totalOrdersInDay(Date date);


}
