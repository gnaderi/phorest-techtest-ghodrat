package com.naderi.phorest.salon.util;

import com.naderi.phorest.salon.common.CsvFileCategory;
import com.naderi.phorest.salon.service.CsvFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvFileImporterStartupRunner implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(CsvFileImporterStartupRunner.class);
    private CsvFileImportService csvFileImportService;
    @Value("${preload:true}")
    private boolean preload = true;

    public CsvFileImporterStartupRunner(CsvFileImportService csvFileImportService) {
        this.csvFileImportService = csvFileImportService;
    }

    @Override
    public void run(String... args) {
        if (!preload) {
            return;
        }
        Arrays.stream(CsvFileCategory.values()).forEach(c -> {
            try {
                csvFileImportService.importData(readFile("https://raw.githubusercontent.com/gnaderi/phorest-techtest-ghodrat/master/DATA_FILES/" + c.getValue() + "s.csv"), c);
                log.warn("Loaded the Salon app with the {"+c.getValue() + "s.csv"+"} Csv that provided!!!");
            } catch (IOException ex) {
                log.error("Unable to preload the Salon app with CSV files:{}", ex.getMessage());
            }
        });

    }

    private List<String> readFile(String url) throws IOException {
        URL clientFileUrl = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(clientFileUrl.openStream()));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}