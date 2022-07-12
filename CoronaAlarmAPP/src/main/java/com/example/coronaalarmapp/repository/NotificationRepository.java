package com.example.coronaalarmapp.repository;

import com.example.coronaalarmapp.entity.Notification;
import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    SeverityStatus findByArea_Id(Long id);
}
