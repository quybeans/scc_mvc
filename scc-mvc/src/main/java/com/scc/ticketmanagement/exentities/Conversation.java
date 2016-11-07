package com.scc.ticketmanagement.exentities;

/**
 * Created by Thien on 11/2/2016.
 */
public class Conversation {
    private String senderName;
    private String senderId;
    private String senderPicture;
    private String lastMessage;
    private String lastMessageId;
    private String sentTime;
    private boolean read;

    public boolean isRead() {
        return read;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Conversation() {
    }

    public Conversation(String senderName, String senderPicture, String lastMessage, String sentTime) {
        this.senderName = senderName;
        this.senderPicture = senderPicture;
        this.lastMessage = lastMessage;
        this.sentTime = sentTime;
    }

    public Conversation(String senderName, String senderId, String senderPicture, String lastMessage, String sentTime) {
        this.senderName = senderName;
        this.senderId = senderId;
        this.senderPicture = senderPicture;
        this.lastMessage = lastMessage;
        this.sentTime = sentTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }
}
