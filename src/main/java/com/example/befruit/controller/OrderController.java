package com.example.befruit.controller;
import com.example.befruit.dto.request.OrderRequest;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> google(@RequestBody OrderRequest orderRequest) {

        try {
            Boolean result = orderService.addOrder(orderRequest);

            return ResponseEntity.ok().body(new ResponseObject("ok", "Checkout success!", result));


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("faild", e.getMessage(), ""));

        }
    }

}
