package com.example.coronaalarmapp.controller;

import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.example.coronaalarmapp.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;

    @PostMapping(path = "/api/notifications/notify")//?area_code={areaCode}&severity={severity}
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> notifyPeople(@RequestParam(name = "areaCode") String areaCode,
                                     @RequestParam(name = "severity") SeverityStatus severity) {
        return notificationService.notifyPeople(areaCode,severity);
    }
}
