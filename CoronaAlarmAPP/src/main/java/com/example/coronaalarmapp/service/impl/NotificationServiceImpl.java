package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.Notification;
import com.example.coronaalarmapp.entity.NotificationPeople;
import com.example.coronaalarmapp.entity.People;
import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.example.coronaalarmapp.repository.*;
import com.example.coronaalarmapp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationPeopleRepository notificationPeopleRepository;
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private CityRepository cityRepository;

    @Override
    @Transactional
    public void notifyPeople(String areaCode, SeverityStatus severity) {

        Optional<Area> areaOptional = Optional.ofNullable(areaRepository.findAreaByAreaCode(areaCode));
        if (areaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("No area with area code %s",areaCode));
        }
        Area area = areaOptional.get();

        List<People> peopleInArea = peopleRepository.findAllByArea(area); // to print

        if (peopleInArea.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("no people found in the area %s",area.getName()));
        }

        Notification notification = Notification.builder()
                .area(area)
                .severityStatus(severity)
                .build();

        notificationRepository.save(notification);

        peopleInArea.forEach(people -> {
           var notificationPeople =  NotificationPeople.builder()
                    .notification(notification)
                    .people(people)
                    .build();
          notificationPeopleRepository.save(notificationPeople);
          var city = people.getCity();
          city.setStatus(severity);
          peopleRepository.save(people);
          cityRepository.save(city);

        });

    }
}
