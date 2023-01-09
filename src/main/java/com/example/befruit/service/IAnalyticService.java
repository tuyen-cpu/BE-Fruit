package com.example.befruit.service;

import com.example.befruit.dto.RevenueDTO;
import org.springframework.data.domain.Page;

public interface IAnalyticService {
	Page<RevenueDTO> getRevenueInMonth(Integer month,Integer year,Integer page,Integer size);
	Page<RevenueDTO> getRevenueInWithDayMonthYearBetween(Integer dayStart,Integer dayEnd,Integer monthStart,Integer monthEnd,Integer yearStart,Integer yearEnd,Integer page,Integer size);
	Page<RevenueDTO> getRevenueInWithMonthYearBetween(Integer monthStart,Integer monthEnd,Integer yearStart,Integer yearEnd,Integer page,Integer size);

	Page<RevenueDTO> getRevenueInYear(Integer year,Integer page,Integer size);
}
