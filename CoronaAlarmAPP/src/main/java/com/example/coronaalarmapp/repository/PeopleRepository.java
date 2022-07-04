package com.example.coronaalarmapp.repository;

import com.example.coronaalarmapp.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People,Long> {
}
