package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.common.CsvFileCategory;
import com.naderi.phorest.salon.common.Gender;
import com.naderi.phorest.salon.common.ServiceType;
import com.naderi.phorest.salon.entity.Appointment;
import com.naderi.phorest.salon.entity.Client;
import com.naderi.phorest.salon.entity.Service;
import com.naderi.phorest.salon.repository.AppointmentRepository;
import com.naderi.phorest.salon.repository.ClientRepository;
import com.naderi.phorest.salon.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class CsvFileImportServiceImpl implements CsvFileImportService {
    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    Logger logger = LoggerFactory.getLogger(CsvFileImportServiceImpl.class);

    public CsvFileImportServiceImpl(ClientRepository clientRepository, AppointmentRepository appointmentRepository, ServiceRepository serviceRepository) {
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void importData(List<String> lines, CsvFileCategory csvFileCategory) {
        if (lines.get(0).startsWith("id,")) lines.remove(0);
        switch (csvFileCategory) {
            case CLIENT -> parseClients(lines);
            case APPOINTMENT -> parseAppointments(lines);
            case SERVICE -> parseServices(lines, ServiceType.SERVICE);
            case PURCHASE -> parseServices(lines, ServiceType.PURCHASE);
        }

    }

    @Override
    public Map<String, Object> importCsvFile(MultipartFile csvFile, CsvFileCategory csvFileCategory) {
        List<String> lines = null;
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("result", Boolean.FALSE);
        try {
            if (csvFile.isEmpty()) {
                propertiesMap.put("message", "File is empty or invalid!");
                logger.error("File is empty or invalid!");
            } else {
                try (InputStream inputStream = csvFile.getInputStream()) {
                    Path tempFile = Files.createTempFile(csvFileCategory.getValue(), ".txt");
                    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                    lines = Files.readAllLines(tempFile);
                }
                this.importData(lines, csvFileCategory);
                String message = String.format("Csv file[%sS] imported successfully!!!", csvFileCategory.name());
                propertiesMap.put("message", message);
                logger.info(message);
                propertiesMap.put("result", Boolean.TRUE);
            }
        } catch (IOException ex) {
            propertiesMap.put("message", "Failed to upload the csv file![" + ex + "]");
            logger.error("Failed to upload the csv file![" + ex + "]");
        }
        return propertiesMap;
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

    private void parseServices(List<String> lines, ServiceType type) {
        lines.forEach(line -> {
            String[] fields = line.split(",");
            Service service = new Service();
            service.setId(fields[0]);
            service.setAppointmentId(fields[1]);
            service.setType(type.getValue());
            service.setName(fields[2]);
            service.setPrice(Double.valueOf(fields[3]));
            service.setLoyaltyPoints(Integer.valueOf(fields[4]));
            service.setCreationDate(LocalDateTime.now());
            service.setUpdatedDate(LocalDateTime.now());
            serviceRepository.save(service);
        });
    }
}
