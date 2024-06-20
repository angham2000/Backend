package tn.esprit.immobilier.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.immobilier.entities.ChatMessageModel;
import tn.esprit.immobilier.repositories.IChatRepository;

import java.util.List;



@Slf4j
@Service
public class ChatService implements IChatService {



    @Autowired
    IChatRepository iChatRepository;
    @Override
    public int save(ChatMessageModel chatMessageModel) {
        ChatMessageModel savedMessage = iChatRepository.save(chatMessageModel);
        return savedMessage != null ? 1 : 0; // Returning 1 if the save was successful, 0 otherwise

    }

    @Override
    public List<ChatMessageModel> findByRoomId(String roomId) {
        return iChatRepository.findByRoomId(roomId);
    }
}
