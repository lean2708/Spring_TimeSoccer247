package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
}
