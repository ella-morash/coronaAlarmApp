package com.example.coronaalarmapp.entity;

import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.example.coronaalarmapp.entity.severitystatus.StatusConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "severity")
    @Convert(converter = StatusConverter.class)
    private SeverityStatus status;

}
