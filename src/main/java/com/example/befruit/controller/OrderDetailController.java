package com.example.befruit.controller;

import com.example.befruit.dto.OrderDetailDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    @Autowired
    private IOrderDetailService orderDetailService;
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderDetailsById(@PathVariable(name ="id" ,required = false) long id){
        try{
            List<OrderDetailDTO> orderResponses = orderDetailService.getAllByOrderId(id);
            return ResponseEntity.ok().body(new ResponseObject("ok","Get order detail successful!",orderResponses));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(),""));

        }
    }
}
