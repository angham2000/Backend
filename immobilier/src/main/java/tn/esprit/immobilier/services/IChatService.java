package tn.esprit.immobilier.services;

import tn.esprit.immobilier.entities.ChatMessageModel;

import java.util.List;

public interface IChatService {

    public int save(ChatMessageModel chatMessageModel);
    public List<ChatMessageModel> findByRoomId(String roomId);
}
