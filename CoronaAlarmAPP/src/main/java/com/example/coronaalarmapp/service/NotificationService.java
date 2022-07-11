package com.example.coronaalarmapp.service;

import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;

public interface NotificationService {
    void notifyPeople(String areaCode, SeverityStatus severity);
}
