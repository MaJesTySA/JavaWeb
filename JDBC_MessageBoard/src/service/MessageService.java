package service;

import bean.Message;
import dao.MessageDAO;

import java.util.Date;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public boolean addMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDAO.save(message);
    }

    public List<Message> getMessages(int page, int pageSize) {
        return messageDAO.getMessages(page, pageSize);
    }

    public int countMessages() {
        return messageDAO.countMessages();
    }

    public int countMyMessages(String username) {
        return messageDAO.countMyMessages(username);
    }

    public List<Message> getMyMessages(int page, int pageSize,String username) {
        return messageDAO.getMyMessages(page, pageSize,username);
    }
}
