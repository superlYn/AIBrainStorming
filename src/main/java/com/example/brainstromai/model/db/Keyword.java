package com.example.brainstromai.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long keywordId;

    public String keyword;

    public String keywordDesc;

    public String keywordUserPrompt = "";

    public Date date = new Date();
}
