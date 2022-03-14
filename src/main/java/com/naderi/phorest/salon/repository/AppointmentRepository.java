package com.naderi.phorest.salon.repository;

import com.naderi.phorest.salon.entity.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}