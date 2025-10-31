package com.example.demo.repository;
import com.example.demo.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepo extends JpaRepository<Courier, Integer> {
    Optional<Courier> findByPhoneNumber(String phoneNumber);
    Optional<Courier> findByEmail(String email);

    @Query("SELECT c FROM Courier c WHERE c.courierId = :courierId")
    List<Courier> findCourierByCourierId(@Param("courierId") Integer courierId);

    Optional<Courier> findById(int courier_id);
    List<Courier> findByAvailabilityStatus(Courier.AvailabilityStatus status);

    Optional<Courier> findFirstByAvailabilityStatusOrderByCourierIdAsc(Courier.AvailabilityStatus availabilityStatus);
}