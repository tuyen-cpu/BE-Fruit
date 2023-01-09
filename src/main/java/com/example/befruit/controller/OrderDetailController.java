package com.example.befruit.controller;

import com.example.befruit.dto.OrderDetailDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
  @Autowired
  private IOrderDetailService orderDetailService;
  @GetMapping("/{id}")
  public ResponseEntity<ResponseObject> getOrderDetailsById(@PathVariable(name ="id" ,required = false) Long id){
    try{
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetail userDetail = (UserDetail) authentication.getPrincipal();

      List<OrderDetailDTO> orderResponses = orderDetailService.getAllByOrderIdAndUserId(id,userDetail.getId());
      return ResponseEntity.ok().body(new ResponseObject("ok","Get order detail successful!",orderResponses));

    }catch (Exception e){
      return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(),""));

    }
  }
}
