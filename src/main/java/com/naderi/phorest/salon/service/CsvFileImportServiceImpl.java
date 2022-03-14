package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.dto.Category;
import com.naderi.phorest.salon.repository.AppointmentRepository;
import com.naderi.phorest.salon.repository.ClientRepository;
import com.naderi.phorest.salon.repository.PurchaseRepository;
import com.naderi.phorest.salon.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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
        logger.info("File contains {} lines", lines.size());
    }
}
