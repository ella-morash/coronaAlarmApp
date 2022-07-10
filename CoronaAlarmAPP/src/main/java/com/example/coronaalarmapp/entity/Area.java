package com.example.coronaalarmapp.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "area")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id", nullable = false)
    private Long id;
    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "name",unique = true,nullable = false)
    private String name;


}
