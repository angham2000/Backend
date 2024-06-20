package tn.esprit.immobilier.controllers;

//import com.chat.socket.dto.ChatMessage; //hethi sarli feha problem
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.immobilier.entities.ChatMessageModel;
import tn.esprit.immobilier.entities.Message;
import tn.esprit.immobilier.entities.User;
import tn.esprit.immobilier.repositories.IChatRepository;
import tn.esprit.immobilier.services.UserService;

import java.util.List;

@Controller
@CrossOrigin("*")
public class WebSocketController {

    //zedet this
    @Autowired
    private IChatRepository iChatRepository;

    ///erb3a
    @Autowired
    UserService userService;


    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public Message chat(@DestinationVariable String roomId, Message message) {

        System.out.println(message);

// zedet those
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setUser_name(message.getUser());
        chatMessageModel.setMessage(message.getMessage());
        chatMessageModel.setRoom_id(roomId);
        iChatRepository.save(chatMessageModel);


        return new Message(message.getMessage(), message.getUser());
    }

//zedet this bch njib les les messages lkol
    @GetMapping("/api/chat/{roomId}")
    public ResponseEntity<List<ChatMessageModel>> getAllChatMessages(@PathVariable String roomId) {
        List<ChatMessageModel> result = iChatRepository.findByRoomId(roomId);
        return ResponseEntity.ok(result);
    }


    /////khedemti bel erb3aaaaa:
     @GetMapping("/getALLUsers")
       public ResponseEntity<List<User>> getAllUsers() {
          List<User> users = userService.getAllUsers();
          return ResponseEntity.ok(users);
        }
}
