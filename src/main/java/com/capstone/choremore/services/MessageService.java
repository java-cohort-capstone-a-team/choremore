package com.capstone.choremore.services;

import com.capstone.choremore.models.Message;

import java.util.List;

public interface MessageService {

    void createMessage(Message message);

    List<Message> showMessages();

    List<Message> getChildMessages();

}