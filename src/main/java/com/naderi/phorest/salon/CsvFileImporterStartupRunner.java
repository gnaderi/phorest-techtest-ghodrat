package com.naderi.phorest.salon;

import com.naderi.phorest.salon.common.CsvFileCategory;
import com.naderi.phorest.salon.service.CsvFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

@Component
public class CsvFileImporterStartupRunner implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(CsvFileImporterStartupRunner.class);
    @Autowired
    private CsvFileImportService csvFileImportService;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(CsvFileCategory.values()).forEach(c-> {
            try {
                csvFileImportService.importData(readFile("https://raw.githubusercontent.com/gnaderi/phorest-techtest-ghodrat/master/DATA_FILES/"+c.getValue()+"s.csv"),c);
            } catch (IOException e) {
                e.printStackTrace();
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