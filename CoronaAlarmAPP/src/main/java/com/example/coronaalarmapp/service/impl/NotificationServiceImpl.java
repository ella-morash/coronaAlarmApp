package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.Notification;
import com.example.coronaalarmapp.entity.NotificationPeople;
import com.example.coronaalarmapp.entity.People;
import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.example.coronaalarmapp.repository.*;
import com.example.coronaalarmapp.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository notificationRepository;

    private final NotificationPeopleRepository notificationPeopleRepository;

    private final PeopleRepository peopleRepository;

    private final AreaRepository areaRepository;

    private final CityRepository cityRepository;

    private final MessageSource messageSource;

    @Override
    @Transactional
    public List<String> notifyPeople(String areaCode, SeverityStatus severity) {

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

        List<String> notificationMessages = new ArrayList<>();

        peopleInArea.forEach(person -> {
           var notificationPeople =  NotificationPeople.builder()
                    .notification(notification)
                    .people(person)
                    .build();
          notificationPeopleRepository.save(notificationPeople);
          var city = person.getCity();
          city.setStatus(severity);
          cityRepository.save(city);

          String status = city.getStatus().getExternalStatusId();

          String notificationMessage = getNotificationMessage(person,status);

          notificationMessages.add(notificationMessage);

        });

        return notificationMessages;



    }

    private String getNotificationMessage (People person, String status) {

        String statusInNotificationMessage = messageSource.getMessage(
                "status." + status,
                null,
                new Locale(person.getLanguage().getLanguageType())
        );

        String statusMessage = messageSource.getMessage(
                "message." + status,
                null,
                new Locale(person.getLanguage().getLanguageType())
        );

        return messageSource.getMessage(
                "message.notification.baseMessage",
                new Object[] {person.getFirstName(),person.getLastName(),
                        statusInNotificationMessage,statusMessage},
                new Locale(person.getLanguage().getLanguageType())


        );
    }
}
