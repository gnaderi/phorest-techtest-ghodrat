package com.naderi.phorest.salon.repository;

import com.naderi.phorest.salon.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
    @Query(value = "select c.* from (select client_id from (select a.client_id,sum(s.loyalty_points) lp from appointment a left join service  s on a.id=s.appointment_id where a.end_time>:localDateTime group by a.client_id ORDER BY lp DESC limit :toplc)) topc left join client c on topc.client_id=c.id",nativeQuery = true)
    List<Client> topNLLoyalClientsByLoyaltiesPoints(@Param("toplc")Integer toplc, @Param("localDateTime") LocalDateTime localDateTime);
    @Query(value = "select c.* from (select client_id from (select a.client_id,sum(s.loyalty_points) lp from appointment a left join service  s on a.id=s.appointment_id group by a.client_id ORDER BY lp DESC)) topc left join client c on topc.client_id=c.id",nativeQuery = true)
    List<Client> sortAllClientByLoyalty();

}

