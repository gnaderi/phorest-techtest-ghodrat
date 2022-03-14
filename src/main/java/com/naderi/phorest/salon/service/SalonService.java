package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.entity.Client;
import com.naderi.phorest.salon.dto.ClientDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public interface SalonService {
    @Transactional
    Client createClient(ClientDto clientDto);
}
