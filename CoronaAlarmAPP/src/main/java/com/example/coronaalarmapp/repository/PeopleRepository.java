package com.example.coronaalarmapp.repository;

import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People,Long> {

    List<People> findPeopleByGuardianId(Long id);

    People findByEmail(String email);

    List<People> findAllByArea(Area area);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);




}
