package com.example.coronaalarmapp.repository;

import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {

    List<City> findAllByArea(Area area);

    City findByName(String name);

    boolean existsByName(String name);
}
