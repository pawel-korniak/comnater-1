package com.pawelkorniak.comnater.repository;

import com.pawelkorniak.comnater.data.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message,String> {

    Flux<Message> findAllByComnat(String comnat);
}
