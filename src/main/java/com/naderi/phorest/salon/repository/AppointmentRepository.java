package com.naderi.phorest.salon.repository;

import com.naderi.phorest.salon.entity.Appointment;
import com.naderi.phorest.salon.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String> {
    @Query("select u from Appointment  u where u.clientId=:clientId")
    List<Appointment> findAppointmentsByClientId(@Param("clientId") String clientId);
}