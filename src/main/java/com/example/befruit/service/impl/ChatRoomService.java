package com.example.befruit.service.impl;

import com.example.befruit.entity.ChatRoom;
import com.example.befruit.repo.chat.ChatRoomRepo;
import com.example.befruit.service.IChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatRoomService implements IChatRoomService {
	@Autowired
	private ChatRoomRepo chatRoomRepo;

	@Override
	public List<ChatRoom> getAll() {
		return chatRoomRepo.findAll();
	}
}
