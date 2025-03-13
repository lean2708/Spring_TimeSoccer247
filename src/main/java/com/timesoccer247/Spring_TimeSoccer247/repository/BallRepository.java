package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.entity.Ball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BallRepository extends JpaRepository<Ball, Long> {
    Set<Ball> findAllByIdIn(Set<Long> ids);
}
