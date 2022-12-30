package com.example.befruit.service;

import com.example.befruit.entity.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IChatRoomService {
	List<ChatRoom> getAll();
}
