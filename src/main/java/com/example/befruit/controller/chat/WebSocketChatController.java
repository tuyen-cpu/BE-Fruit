package com.example.befruit.controller.chat;

import com.example.befruit.converter.ChatMessageConverter;
import com.example.befruit.dto.request.MessageRoomRequest;
import com.example.befruit.entity.ChatRoom;
import com.example.befruit.entity.MessageRoom;
import com.example.befruit.repo.chat.ChatRoomRepo;
import com.example.befruit.repo.chat.MessageRoomRepo;
import com.example.befruit.service.IChatRoomService;
import com.example.befruit.service.IMessageRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class WebSocketChatController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
@Autowired
private IChatRoomService chatRoomService;
	@Autowired
	private IMessageRoomService messageRoomService;

	@Autowired
	private ChatRoomRepo chatRoomRepo;
	@Autowired
	private MessageRoomRepo messageRoomRepo;
@Autowired
private ChatMessageConverter chatMessageConverter;
	@SubscribeMapping("/room/get")
	public List<ChatRoom> findAll() {
		return chatRoomService.getAll();
	}

	@MessageMapping("/room/create")
	@SendTo("/topic/room/created")
	public ChatRoom save(ChatRoom chatRoom) {
	return	chatRoomRepo.save(chatRoom);
	}

	@SubscribeMapping("/room/{id}/get")
	public List<MessageRoom> getMessageRoomByChatRoomId(@DestinationVariable Long id) {
		System.out.println(id);
		return messageRoomRepo.findByChatRoomId(id);
	}
	@MessageMapping("/message-room/create")
	@SendTo("/topic/message-room/created")
	public MessageRoom save(MessageRoomRequest messageRoomRequest) {
		return messageRoomRepo.save(chatMessageConverter.convertToEntity(messageRoomRequest));
	}

}
