package com.example.coronaalarmapp.repository;

import com.example.coronaalarmapp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
