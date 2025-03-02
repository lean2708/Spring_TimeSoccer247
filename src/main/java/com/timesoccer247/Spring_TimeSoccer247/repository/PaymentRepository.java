package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
}
