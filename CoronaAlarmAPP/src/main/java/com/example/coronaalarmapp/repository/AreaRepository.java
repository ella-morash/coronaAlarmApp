package com.example.coronaalarmapp.repository;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area,Long> {

    boolean existsById(Long id);

    Area getAreaByName(String name);

    Area findAreaByAreaCode(String areaCode);


}
