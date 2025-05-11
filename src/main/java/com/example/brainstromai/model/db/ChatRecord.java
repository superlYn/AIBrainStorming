package com.example.brainstromai.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity
public class ChatRecord {
    @Id
    public long chatRecordId = UUID.randomUUID().getLeastSignificantBits();
    
    public String chatRecordContent;

    public Date date = new Date();
}
