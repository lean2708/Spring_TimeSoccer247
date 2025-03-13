package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
import com.timesoccer247.Spring_TimeSoccer247.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b.id, b.startTime, b.endTime, u.name, b.status " +
            "FROM Booking b " +
            "JOIN b.field f " +
            "JOIN b.user u " +
            "WHERE b.startTime BETWEEN :startOfDay AND :endOfDay " +
            "AND f.id = :fieldId " +
            "AND b.status = :status " +
            "ORDER BY b.startTime ASC")
    Page<Object[]> findBookedTimesByField(@Param("fieldId") Long fieldId,
                                          @Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("endOfDay") LocalDateTime endOfDay,
                                          @Param("status") BookingStatus status,
                                          Pageable pageable);

    @Query("SELECT COUNT(b) FROM Booking b " +
            "WHERE b.field.id = :fieldId " +
            "AND ((b.startTime <= :endTime AND b.endTime >= :startTime)) " +
            "AND b.status = :status")
    long countByFieldAndTimeRangeAndStatus(@Param("fieldId") Long fieldId,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime,
                                           @Param("status") BookingStatus status);



    @Query("SELECT COUNT(b) FROM Booking b " +
            "JOIN b.balls ball " +
            "WHERE ball.id IN (:ballIds) " +
            "AND ((b.startTime <= :endTime AND b.endTime >= :startTime)) " +
            "AND b.status = :status")
    long countByBallsAndTimeRangeAndStatus(@Param("ballIds") Set<Long> ballIds,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime,
                                           @Param("status") BookingStatus status);


}
