package com.example.befruit.repo;

import com.example.befruit.dto.RevenueDTO;
import com.example.befruit.entity.Order;
import com.example.befruit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RevenueRepo extends JpaRepository<Order,Long>{
	@Query("select new com.example.befruit.dto.RevenueDTO(cast(o.createdAt as date),cast(count(o) as integer ) ,sum(o.total + o.shippingCost),o.shippingCost,sum(o.total)) from Order o where month(cast(o.createdAt as date))=?1 and year(cast(o.createdAt as date))=?2 group by cast(o.createdAt as date)")
	Page<RevenueDTO> getRevenueInMonth(Integer month,Integer year, Pageable pageable);

	@Query("select new com.example.befruit.dto.RevenueDTO(cast(o.createdAt as date),cast(count(o) as integer ) ,sum(o.total + o.shippingCost),sum(o.shippingCost),sum(o.total)) from Order o where (year(cast(o.createdAt as date))*1000000 + month(cast(o.createdAt as date))*10000 + day(cast(o.createdAt as date))*100) >= ?5*1000000 + ?3*10000 + ?1*100 and ((year(cast(o.createdAt as date))*1000000 + month(cast(o.createdAt as date))*10000 + day(cast(o.createdAt as date))*100) <= ?6*1000000 + ?4*10000 + ?2*100 )group by day(cast(o.createdAt as date)),month(cast(o.createdAt as date)),year(cast(o.createdAt as date))")
	Page<RevenueDTO> getRevenueWithDayMonthYear(Integer dayStart,Integer dayEnd,Integer monthStart,Integer monthEnd,Integer yearStart,Integer yearEnd, Pageable pageable);
	@Query("select new com.example.befruit.dto.RevenueDTO(DATE_FORMAT(cast(o.createdAt as date), '%m/%Y'),cast(count(o) as integer ) ,sum(o.total + o.shippingCost),sum(o.shippingCost),sum(o.total)) from Order o where (month(cast(o.createdAt as date))>=?1 and year (cast(o.createdAt as date))>=?3 )and (month(cast(o.createdAt as date))<=?2 and year(cast(o.createdAt as date))<=?4) group by month(cast(o.createdAt as date)),year(cast(o.createdAt as date))")
	Page<RevenueDTO> getRevenueWithMonthYear(Integer monthStart,Integer monthEnd,Integer yearStart,Integer yearEnd, Pageable pageable);

	@Query("select new com.example.befruit.dto.RevenueDTO(cast(o.createdAt as date),cast(count(o) as integer ) ,sum(o.total + o.shippingCost),o.shippingCost,sum(o.total)) from Order o where  year(cast(o.createdAt as date))=?1 group by cast(o.createdAt as date)")
	Page<RevenueDTO> getRevenueInYear(Integer year, Pageable pageable);

//	@Query("select new com.example.befruit.dto.RevenueDTO(cast(o.createdAt as date),cast(count(o) as integer ) ,sum(o.total + o.shippingCost),o.shippingCost,sum(o.total)) from Order o where  year(cast(o.createdAt as date))=?2 group by cast(o.createdAt as date)")
//	Page<RevenueDTO> getRevenueWithDayIn(Integer year, Pageable pageable);
}
