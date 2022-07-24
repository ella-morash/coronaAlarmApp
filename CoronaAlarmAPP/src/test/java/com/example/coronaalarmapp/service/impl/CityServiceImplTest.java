package com.example.coronaalarmapp.service.impl;


import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.repository.NotificationRepository;
import com.example.coronaalarmapp.util.Convertor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private Convertor convertor;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private CityServiceImpl cityService;

}
