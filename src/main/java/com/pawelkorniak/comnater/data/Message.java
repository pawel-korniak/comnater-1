package com.pawelkorniak.comnater.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Message {


    String id;
    String comnat;
    String author;
    String body;
    LocalDateTime time = LocalDateTime.now();

    public Message(String comnat,String author,String body) {
        this.body = body;
        this.comnat = comnat;
        this.author = author;
    }

    public Message() {
    }

    public Message(String body) {
        this.body = body;
    }
}
