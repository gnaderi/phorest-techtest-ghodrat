package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.common.exception.InvalidRequestException;
import com.naderi.phorest.salon.dto.AppointmentDto;
import com.naderi.phorest.salon.dto.ClientDto;
import com.naderi.phorest.salon.dto.PurchaseDto;
import com.naderi.phorest.salon.dto.ServiceDto;
import com.naderi.phorest.salon.entity.Client;
import com.naderi.phorest.salon.repository.AppointmentRepository;
import com.naderi.phorest.salon.repository.ClientRepository;
import com.naderi.phorest.salon.repository.PurchaseRepository;
import com.naderi.phorest.salon.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalonServiceImpl implements SalonService {
    private ClientRepository clientRepository;
    private AppointmentRepository appointmentRepository;
    private ServiceRepository serviceRepository;
    private PurchaseRepository purchaseRepository;

    public SalonServiceImpl(ClientRepository clientRepository, AppointmentRepository appointmentRepository, ServiceRepository serviceRepository, PurchaseRepository purchaseRepository) {
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Client createClient(ClientDto clientDto) {
        return null;
    }

    @Override
    public ClientDto findClientById(String clientId) {
        Client c = clientRepository.findById(clientId).orElseThrow(() -> new InvalidRequestException("Client Id[" + clientId + "] is not valid!"));
        return new ClientDto(c.getId(), c.getFirstName(),
                c.getLastName(), c.getEmail(), c.getPhone(), c.getGender(), c.getBanned());

    }

    @Override
    public List<ClientDto> findAllClients() {
        List<ClientDto> clients = new ArrayList<>();
        clientRepository.findAll().forEach(c -> {
            clients.add(new ClientDto(c.getId(), c.getFirstName(),
                    c.getLastName(), c.getEmail(), c.getPhone(), c.getGender(), c.getBanned()));
        });
        return clients;
    }

    @Override
    public List<AppointmentDto> findClientAppointments(String clientId) {
        List<AppointmentDto> appointments = new ArrayList<>();
        appointmentRepository.findAppointmentsByClientId(clientId).forEach(ap -> {
            appointments.add(new AppointmentDto(ap.getId(), ap.getClientId(), ap.getStartDatetime(), ap.getEndDatetime()));
        });
        return appointments;
    }

    @Override
    public List<ServiceDto> findAppointmentServices(String appointmentId) {
        List<ServiceDto> services = new ArrayList<>();

        serviceRepository.findAllByAppointmentId(appointmentId).forEach(service -> {
            services.add(new ServiceDto(service.getId(), service.getAppointmentId(), service.getName(), service.getPrice(),service.getLoyaltyPoints()));
        });
        return services;
    }

    @Override
    public List<PurchaseDto> findAppointmentPurchases(String appointmentId) {
        List<PurchaseDto> purchases = new ArrayList<>();
        purchaseRepository.findAllByAppointmentId(appointmentId).forEach(service -> {
            purchases.add(new PurchaseDto(service.getId(), service.getAppointmentId(), service.getName(), service.getPrice(),service.getLoyaltyPoints()));
        });
        return purchases;
    }
}