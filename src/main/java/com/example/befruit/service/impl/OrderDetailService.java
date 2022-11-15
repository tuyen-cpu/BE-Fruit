package com.example.befruit.service.impl;

import com.example.befruit.converter.OrderDetailConverter;
import com.example.befruit.dto.OrderDetailDTO;
import com.example.befruit.entity.OrderDetail;
import com.example.befruit.repo.OrderDetailRepo;
import com.example.befruit.repo.OrderRepo;
import com.example.befruit.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private OrderDetailConverter orderDetailConverter;
    @Override
    public List<OrderDetailDTO> getAllByOrderId(Long orderId) {
        List<OrderDetail> list =orderDetailRepo.findAllByBillId(orderId);

        return list.stream().map(e->orderDetailConverter.convertToResponse(e)).collect(Collectors.toList());
    }
}
