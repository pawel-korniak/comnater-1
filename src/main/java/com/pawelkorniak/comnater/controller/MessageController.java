package com.pawelkorniak.comnater.controller;

import com.pawelkorniak.comnater.data.Message;
import com.pawelkorniak.comnater.service.MessageService;
import io.reactivex.Observable;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("messages")
public class MessageController {

    final MessageService messageService;
    final KafkaTemplate<String,Message> kafkaTemplate;

    @PostMapping("send")
    public void sendMessage(@RequestParam String body,String author,String comnat){
        Message message = new Message(comnat,author,body);
         messageService.save(message);
         kafkaTemplate.send("name-topic",message);
    }

    @GetMapping("{comnat}")
    public Observable<Message> findAllByComnat(@PathVariable String comnat){
        return messageService.findAllByComnat(comnat);
    }

    @GetMapping
    public Observable<Message> reactive(){
        return messageService.findAll();
    }
}
