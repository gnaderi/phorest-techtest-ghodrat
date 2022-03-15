package com.naderi.phorest.salon.repository;

import com.naderi.phorest.salon.entity.Purchase;
import com.naderi.phorest.salon.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ServiceRepository extends CrudRepository<Service, String> {
    @Query("select u from Service  u where u.appointmentId=:appointmentId")
    List<Service> findAllByAppointmentId(@Param("appointmentId") String appointmentId);
}