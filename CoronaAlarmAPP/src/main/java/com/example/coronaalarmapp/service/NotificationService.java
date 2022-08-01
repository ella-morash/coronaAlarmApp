package com.example.coronaalarmapp.service;

import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;

import java.util.List;

public interface NotificationService {
    List<String> notifyPeople(String areaCode, SeverityStatus severity);
}
