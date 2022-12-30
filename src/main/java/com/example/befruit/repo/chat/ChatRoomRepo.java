package com.example.befruit.repo.chat;

import com.example.befruit.entity.Address;
import com.example.befruit.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
}
