package tn.esprit.immobilier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.immobilier.entities.ChatMessageModel;

import java.util.List;


@Repository
public interface IChatRepository extends JpaRepository<ChatMessageModel,Integer> {

    @Query("SELECT c FROM ChatMessageModel c WHERE c.room_id = :roomId")
    List<ChatMessageModel> findByRoomId(@Param("roomId") String roomId);

}
