package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import com.timesoccer247.Spring_TimeSoccer247.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "JOIN p.booking b " +
            "WHERE p.status = :status " +
            "AND b.field.id = :fieldId " +
            "AND p.paymentTime BETWEEN :startTime AND :endTime")
    double getTotalRevenueByTimeRangeForField(@Param("fieldId") Long fieldId,
                                              @Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime,
                                              @Param("status") PaymentStatus status);

}
