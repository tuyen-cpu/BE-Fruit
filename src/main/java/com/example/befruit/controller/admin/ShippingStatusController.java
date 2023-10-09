package com.example.befruit.controller.admin;

import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.ShippingStatus;
import com.example.befruit.service.impl.ShippingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@RestController
@RequestMapping("/api/admin/shipping-status")
public class ShippingStatusController {
  @Autowired
  private ShippingStatusService shippingStatusService;

  @GetMapping("/all")
  public ResponseEntity<ResponseObject> getAll() {
    try {
      List<ShippingStatus> shippingStatuses = shippingStatusService.getAll();
      return ResponseEntity.ok()
        .body(new ResponseObject("ok", "Get shipping status successfully!", shippingStatuses));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(new ResponseObject("failed", "Get failed!", ""));
    }

  }
}
