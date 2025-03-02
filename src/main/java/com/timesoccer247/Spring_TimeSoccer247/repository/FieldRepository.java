package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    boolean existsByName(String name);
}
