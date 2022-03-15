package com.naderi.phorest.salon.repository;

import com.naderi.phorest.salon.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
}