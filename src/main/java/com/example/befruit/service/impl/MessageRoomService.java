package com.example.befruit.service.impl;

import com.example.befruit.entity.MessageRoom;
import com.example.befruit.repo.chat.MessageRoomRepo;
import com.example.befruit.service.IMessageRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageRoomService implements IMessageRoomService {
	@Autowired
	private MessageRoomRepo messageRoomRepo;
	@Override
	public List<MessageRoom> getAll() {
		return messageRoomRepo.findAll();
	}
}
