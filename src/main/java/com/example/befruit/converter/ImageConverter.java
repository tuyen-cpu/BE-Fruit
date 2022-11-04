package com.example.befruit.converter;

import com.example.befruit.dto.ImageDTO;
import com.example.befruit.entity.Image;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageConverter {
    @Autowired
    private ModelMapper modelMapper;
    public ImageDTO convertToDto(Image entity){
        return modelMapper.map(entity, ImageDTO.class);
    }

    public Image convertToEntity(ImageDTO dto){
        return modelMapper.map(dto, Image.class);
    }
}
