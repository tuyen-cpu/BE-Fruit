package com.example.befruit.controller;

import com.example.befruit.dto.filter.OrderFilter;
import com.example.befruit.dto.request.OrderRequest;
import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.entity.EShippingStatus;
import com.example.befruit.entity.Filter;
import com.example.befruit.entity.QueryOperator;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.repo.specs.OrderSpecification;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IOrderDetailService;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {
  @Autowired
  private IOrderService orderService;
  @Autowired
  private IOrderDetailService orderDetailService;
  @PostMapping("")
  public ResponseEntity<ResponseObject> add(@RequestBody OrderRequest orderRequest) {

    try {
      Boolean result = orderService.addOrder(orderRequest);

      return ResponseEntity.ok().body(new ResponseObject("ok", "Checkout success!", result));


    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

    }
  }
  @GetMapping("/all/{userId}")
  public ResponseEntity<ResponseObject> getByUserId(@PathVariable(name ="userId" ,required = false) long userId,
                                                    @RequestParam(name = "page",defaultValue = "0") int page,
                                                    @RequestParam(name = "size",defaultValue = "10") int size){
    try{

      Page<OrderResponse> orderResponses = orderService.getByUserId(userId,page,size);
      return ResponseEntity.ok().body(new ResponseObject("ok","Get orders successful!",orderResponses));

    }catch (Exception e){
      return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(),""));

    }
  }
//  @PostAuthorize("returnObject.user.id == authentication.principal.id")
  @GetMapping("/{id}")
  public ResponseEntity<ResponseObject> getById(@PathVariable(name ="id" ,required = false) long id){
    try{
      OrderResponse orderResponses = orderService.getById(id);
      return ResponseEntity.ok().body(new ResponseObject("ok","Get order successful!",orderResponses));

    }catch (Exception e){
      return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(),""));

    }
  }
  @PutMapping("/update-shipping-status")
  public ResponseEntity<ResponseObject> updateStatusShipping(@RequestBody OrderRequest orderRequest){
    try{
      OrderResponse orderResponses = orderService.updateStatusShipping(orderRequest.getId(), EShippingStatus.CANCELING.getName());
      return ResponseEntity.ok().body(new ResponseObject("ok","Get order successful!",orderResponses));

    }catch (Exception e){
      return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(),""));

    }
  }
  @PostMapping("/filter")
  public ResponseEntity<ResponseObject> filter(@RequestBody OrderFilter orderFilter
  ) {
    try {

      Authentication authentication = SecurityContextHolder.getContext()
          .getAuthentication();
      UserDetail u =(UserDetail) authentication.getPrincipal();
      OrderSpecification orderSpecification = new OrderSpecification();
      if(orderFilter.getCreatedDate()==null||orderFilter.getCreatedDate().size()<1){
      }
      else if(orderFilter.getCreatedDate().get(0)==null){
        orderSpecification.add(new Filter("createdDate", QueryOperator.IN ,orderFilter.getCreatedDate().get(1)));
      }else if(orderFilter.getCreatedDate().get(1)==null){
        orderSpecification.add(new Filter("createdDate", QueryOperator.IN ,orderFilter.getCreatedDate().get(0)));
      }else if(orderFilter.getCreatedDate().get(0)!=null && orderFilter.getCreatedDate().get(1)!=null){
        orderSpecification.add(new Filter("createdDate", QueryOperator.IN ,orderFilter.getCreatedDate()));
      }
      if(u!=null){
        orderSpecification.add(new Filter("userId", QueryOperator.EQUAL ,u.getId()));
      }
      if(orderFilter.getShippingStatusId()!=null){
        orderSpecification.add(new Filter("shippingStatusId", QueryOperator.EQUAL ,orderFilter.getShippingStatusId()));
      }

      Page<OrderResponse>  orderResponsePage = orderService.filter(orderSpecification,orderFilter.getPage(),orderFilter.getSize());


      return ResponseEntity.ok()
          .body(new ResponseObject("ok", "Filter product successfully!", orderResponsePage));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ResponseObject("failed", e.getMessage(), ""));
    }
  }
}
