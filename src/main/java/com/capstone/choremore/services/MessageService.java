package com.capstone.choremore.services;

import com.capstone.choremore.models.Message;

import java.util.List;

public interface MessageService {

    void createMessage(Message message);

    List<Message> showMessages();

    List<Message> getChildMessages();

    void deleteMessageById(long id);

    void editMessage(Message message, long id, long id2);

}