package com.example.brainstromai.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class ChatRecord {
    @Id
    public String chatRecordId;

    public String chatRecordContent;

    public Date date = new Date();
}
