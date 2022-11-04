package com.example.befruit.controller;

import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "productId",required = false) long productId
                                                                     ){
        try{
            ImageDTO image = imageService.getByProductId(productId);
            return ResponseEntity.ok().body(new ResponseObject("ok","Get image successful!",image));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseObject("failed",e.getMessage(),""));

        }
    }
}
