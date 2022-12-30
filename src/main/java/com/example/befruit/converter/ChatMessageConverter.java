package com.example.befruit.converter;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.dto.request.MessageRoomRequest;
import com.example.befruit.entity.Category;
import com.example.befruit.entity.MessageRoom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageConverter {
	@Autowired
	private ModelMapper modelMapper;
	public MessageRoomRequest convertToRequest(MessageRoom entity) {
		return modelMapper.map(entity, MessageRoomRequest.class);
	}
	public MessageRoom convertToEntity(MessageRoomRequest dto) {
		return modelMapper.map(dto, MessageRoom.class);
	}
}
