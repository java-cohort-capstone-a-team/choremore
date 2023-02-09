package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.models.User;
import com.capstone.choremore.models.UserWithRoles;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.MessageRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageRepo messageDao;

    @Autowired
    private UserRepo userDao;

    @Autowired
    private AvatarRepo avatarDao;

    @Override
    public void createMessage(Message message) {

        UserWithRoles user = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        message.setChild(user);

        messageDao.save(message);

    }

    @Override
    public List<Message> showMessages() {

        List<Message> messages = messageDao.findAll();

        return messages;

    }

    public List<Message> getChildMessages() {

        UserWithRoles user = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = user.getId();
        List<Avatar> children = avatarDao.findAvatarsByParentId(id);
        List<Message> childMessages = children.stream().map(child -> messageDao.getMessagesByChildId(child.getChild().getId())).flatMap(List::stream).collect(Collectors.toList());

        return childMessages;

    }

    public void deleteMessageById(long id) {

        messageDao.deleteById(id);

    }

    public void editMessage(Message message, long id, long id2) {

        Message editedMessage = messageDao.findById(id2).get();

        editedMessage.setTitle(message.getTitle());
        editedMessage.setBody(message.getBody());

        User child = userDao.findById(id).get();

        editedMessage.setChild(child);

        messageDao.save(editedMessage);

    }

}
