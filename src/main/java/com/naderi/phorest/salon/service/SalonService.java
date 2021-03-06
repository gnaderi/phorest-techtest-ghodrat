package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.common.ServiceType;
import com.naderi.phorest.salon.dto.AppointmentDto;
import com.naderi.phorest.salon.dto.ClientDto;
import com.naderi.phorest.salon.dto.ServiceDto;
import com.naderi.phorest.salon.entity.Client;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface SalonService {
    ClientDto findClientById(String clientId);

    @Transactional
    Client createClient(ClientDto clientDto);

    @Transactional
    ClientDto updateClient(ClientDto clientDto);

    @Transactional
    void deleteClient(String clientId);


    List<ClientDto> findAllClients();

    List<AppointmentDto> findClientAppointments(String clientId);

    List<ServiceDto> findAppointmentServices(String appointmentId, ServiceType serviceType);

    List<ClientDto> topNLLoyalClientsByLoyaltiesPoints(Integer top, LocalDateTime startDate);

    List<ClientDto> sortAllClientByLoyalty();
};
