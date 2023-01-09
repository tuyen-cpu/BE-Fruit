package com.example.befruit.service.impl;

import com.example.befruit.dto.RevenueDTO;
import com.example.befruit.repo.OrderRepo;
import com.example.befruit.repo.RevenueRepo;
import com.example.befruit.service.IAnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnalyticService implements IAnalyticService {
	@Autowired
	private RevenueRepo revenueRepo;
	@Override
	public Page<RevenueDTO> getRevenueInMonth(Integer month,Integer year,Integer page,Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueInMonth(month,year,pageable);
	}

	@Override
	public Page<RevenueDTO> getRevenueInWithDayMonthYearBetween(Integer dayStart, Integer dayEnd, Integer monthStart, Integer monthEnd, Integer yearStart, Integer yearEnd, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueWithDayMonthYear(dayStart,dayEnd,monthStart,monthEnd,yearStart,yearEnd,pageable);

	}

	@Override
	public Page<RevenueDTO> getRevenueInWithMonthYearBetween(Integer monthStart, Integer monthEnd, Integer yearStart, Integer yearEnd, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueWithMonthYear(monthStart,monthEnd,yearStart,yearEnd,pageable);
	}

	@Override
	public Page<RevenueDTO> getRevenueInYear(Integer year, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueInYear(year,pageable);
	}
}
