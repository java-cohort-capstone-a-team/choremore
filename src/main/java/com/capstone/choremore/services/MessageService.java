package com.capstone.choremore.services;

import com.capstone.choremore.models.Message;

import java.util.List;

public interface MessageService {

    List<Message> profileShowUserMessages();

    void createMessage(Message message);

    List<Message> showMessages();

    Message showById(long id);

//    Message getUserIdById(long id);

    Message editMessageById(long id);

    void editMessage(Message message);

    void deleteMessageById(long id);

    Message findByTitle(String title);

}