package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.MessageRepo;
import com.capstone.choremore.repositories.UserRepo;

import com.capstone.choremore.services.MessageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService {

    private final MessageRepo messageDao;

    private final UserRepo userDao;

//    private final AvatarRepo avatarDao;

    public MessageServiceImp(MessageRepo messageDao, UserRepo userDao, AvatarRepo avatarDao) {

        this.messageDao = messageDao;
        this.userDao = userDao;
//        this.avatarDao = avatarDao;

    }


    @Override
    public List<Message> profileShowUserMessages() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Message> messages = userDao.findByUsername(user.getUsername()).getMessages();

        return messages;

    }

    @Override
    public void createMessage(Message message) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        message.setChild(user);
        messageDao.save(message);

    }

    @Override
    public List<Message> showMessages() {

        List<Message> messages = messageDao.findAll();

        return messages;

    }

    @Override
    public Message showById(long id) {

        Message message = messageDao.getReferenceById(id);

        return message;

    }

//    @Override
//    public Message getUserIdById(long id) {
//        return null;
//    }

    @Override
    public Message editMessageById(long id) {

        Message message = messageDao.getReferenceById(id);

        return message;

    }

    @Override
    public void editMessage(Message message) {

        messageDao.save(message);

    }

    @Override
    public void deleteMessageById(long id) {

        messageDao.deleteById(id);

    }

    @Override
    public Message findByTitle(String title) {

        return messageDao.findByTitle(title);

    }

}
