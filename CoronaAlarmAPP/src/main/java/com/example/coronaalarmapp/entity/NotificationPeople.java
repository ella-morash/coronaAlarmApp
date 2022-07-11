package com.example.coronaalarmapp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notification_people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationPeople {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private People people;

    @OneToOne
    private Notification notification;

}
