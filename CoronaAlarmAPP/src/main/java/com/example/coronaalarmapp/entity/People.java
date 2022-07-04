package com.example.coronaalarmapp.entity;

import com.example.coronaalarmapp.dto.PeopleDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
