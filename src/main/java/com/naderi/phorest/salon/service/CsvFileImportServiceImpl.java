package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.common.Category;
import com.naderi.phorest.salon.common.Gender;
import com.naderi.phorest.salon.entity.Appointment;
import com.naderi.phorest.salon.entity.Client;
import com.naderi.phorest.salon.entity.Purchase;
import com.naderi.phorest.salon.entity.Service;
import com.naderi.phorest.salon.repository.AppointmentRepository;
import com.naderi.phorest.salon.repository.ClientRepository;
import com.naderi.phorest.salon.repository.PurchaseRepository;
import com.naderi.phorest.salon.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class CsvFileImportServiceImpl implements CsvFileImportService {
    Logger logger = LoggerFactory.getLogger(CsvFileImportServiceImpl.class);
    private ClientRepository clientRepository;
    private AppointmentRepository appointmentRepository;
    private ServiceRepository serviceRepository;
    private PurchaseRepository purchaseRepository;

    public CsvFileImportServiceImpl(ClientRepository clientRepository, AppointmentRepository appointmentRepository, ServiceRepository serviceRepository, PurchaseRepository purchaseRepository) {
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void importData(List<String> lines, Category category) {
        if (lines.get(0).startsWith("id,")) lines.remove(0);
        switch (category) {
            case CLIENT -> parseClients(lines);
            case APPOINTMENT -> parseAppointments(lines);
            case SERVICE -> parseServices(lines);
            case PURCHASE -> parsePurchases(lines);
        }

    }

    private void parseClients(List<String> lines) {
        lines.forEach(line -> {
            String[] fields = line.split(",");
            Client client = new Client();
            client.setId(fields[0]);
            client.setFirstName(fields[1]);
            client.setLastName(fields[2]);
            client.setEmail(fields[3]);
            client.setPhone(fields[4]);
            client.setGender(Gender.of(fields[5]));
            client.setBanned(Boolean.valueOf(fields[6]));
            client.setCreationDate(LocalDateTime.now());
            client.setUpdatedDate(LocalDateTime.now());
            clientRepository.save(client);
        });
    }

    private void parseAppointments(List<String> lines) {
        lines.forEach(line -> {
            String[] fields = line.split(",");
            Appointment apt = new Appointment();
            apt.setId(fields[0]);
            apt.setClientId(fields[1]);
            apt.setStartDatetime(ZonedDateTime.parse(fields[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")).toLocalDateTime());
            apt.setEndDatetime(ZonedDateTime.parse(fields[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")).toLocalDateTime());
            apt.setCreationDate(LocalDateTime.now());
            apt.setUpdatedDate(LocalDateTime.now());
            appointmentRepository.save(apt);
        });
    }

    private void parseServices(List<String> lines) {
        lines.forEach(line -> {
            String[] fields = line.split(",");
            Service service = new Service();
            service.setId(fields[0]);
            service.setAppointmentId(fields[1]);
            service.setName(fields[2]);
            service.setPrice(Double.valueOf(fields[3]));
            service.setLoyaltyPoints(Integer.valueOf(fields[4]));
            service.setCreationDate(LocalDateTime.now());
            service.setUpdatedDate(LocalDateTime.now());
            serviceRepository.save(service);
        });
    }

    private void parsePurchases(List<String> lines) {
        lines.forEach(line -> {
            String[] fields = line.split(",");
            Purchase purchase = new Purchase();
            purchase.setId(fields[0]);
            purchase.setAppointmentId(fields[1]);
            purchase.setName(fields[2]);
            purchase.setPrice(Double.valueOf(fields[3]));
            purchase.setLoyaltyPoints(Integer.valueOf(fields[4]));
            purchase.setCreationDate(LocalDateTime.now());
            purchase.setUpdatedDate(LocalDateTime.now());

            purchaseRepository.save(purchase);
        });
    }
}
