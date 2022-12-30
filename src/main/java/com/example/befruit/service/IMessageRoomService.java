package com.example.befruit.service;

import com.example.befruit.entity.ChatRoom;
import com.example.befruit.entity.MessageRoom;

import java.util.List;

public interface IMessageRoomService {
	List<MessageRoom> getAll();
}
