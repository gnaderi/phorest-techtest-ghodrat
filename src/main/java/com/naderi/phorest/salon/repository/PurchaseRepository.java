package com.naderi.phorest.salon.repository;

import com.naderi.phorest.salon.entity.Appointment;
import com.naderi.phorest.salon.entity.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, String> {
    @Query("select u from Purchase  u where u.appointmentId=:appointmentId")
    List<Purchase> findAllByAppointmentId(@Param("appointmentId") String appointmentId);
}