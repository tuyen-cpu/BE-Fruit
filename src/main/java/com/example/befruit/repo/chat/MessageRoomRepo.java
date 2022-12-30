package com.example.befruit.repo.chat;

import com.example.befruit.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRoomRepo extends JpaRepository<MessageRoom, Long> {
	List<MessageRoom> findByChatRoomId(Long id);
}
