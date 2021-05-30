package com.pawelkorniak.comnater.service;

import com.pawelkorniak.comnater.data.Message;
import com.pawelkorniak.comnater.repository.MessageRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    final MessageRepository messageRepository;


    @KafkaListener(topics = "name-topic", groupId = "consumer-group-2")
    public void listenForPerson(String message) {
        System.out.println(message);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        messageRepository.saveAll(Flux.just(
                new Message("comnat1","author1","Message 1"),
                new Message("comnat1","author1","Message 2"),
                new Message("comnat1","author1","Message 3")))
                .subscribe(message -> log.info("Saved : " + message));
    }

    public Single<Message> save(Message message) {
        log.info("Mono saving message : " + message);
        var mono = messageRepository.save(message);
        mono.subscribe(
                message1 -> log.info("Mono saved message : " + message1),
                throwable -> log.error("ERROR, some problem with saving to DB. Error message :" + throwable.getMessage())
        );
        //TODO Find out why single is null and emiting error, hint : .create(SingleOnSubscribe<T>)
        Single<Message> single = Single.fromPublisher(mono);
        single.subscribe(
                message1 -> log.info("message converted to Single : " + message1),
                throwable -> log.error("ERROR, some problem with converting to Single. Error message :" + throwable.getMessage())
        );
        return single;
    }

    public Observable<Message> findAll() {

        var messagesFlux = messageRepository.findAll();
        return Observable.fromPublisher(messagesFlux);
    }

    public Observable<Message> findAllByComnat(String comnat) {
        var messagesFlux = messageRepository.findAllByComnat(comnat);
        return Observable.fromPublisher(messagesFlux);
    }
}
