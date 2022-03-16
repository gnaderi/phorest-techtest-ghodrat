package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.Category;
import com.naderi.phorest.salon.service.CsvFileImportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ImportCsvFileController {

    private final CsvFileImportService csvFileImportService;

    @Autowired
    public ImportCsvFileController(CsvFileImportService csvFileImportService) {
        this.csvFileImportService = csvFileImportService;
    }

    @GetMapping("/uploadCsvFile")
    public String listUploadedFiles(Model model) {
        return "pages/importCsvFile";
    }

    @PostMapping("/uploadCsvFile")
    public String importCsvFile(Model model, @NotNull @RequestParam("csvFile") MultipartFile csvFile, @NotNull @RequestParam("category") Category category) {
        model.addAllAttributes(importFile(csvFile, category));
        model.addAttribute("category", category.name());
        return "pages/importCsvFile";
    }

    @PutMapping("/restapi/v1/upload-csv-file")
    @ResponseBody
    public ResponseEntity<?> importCsvFile(@NotNull @RequestParam("csvFile") MultipartFile csvFile, @NotNull @RequestParam("category") Category category) {
        Map<String, Object> map = importFile(csvFile, category);
        return (boolean) map.get("result") ? ResponseEntity.ok(map.get("message")) : new ResponseEntity<>(map.get("message"), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> importFile(MultipartFile csvFile, Category category) {
        List<String> lines = null;
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("result", Boolean.FALSE);
        try {
            if (csvFile.isEmpty()) {
                propertiesMap.put("message", "File is empty or invalid!");
            } else {
                try (InputStream inputStream = csvFile.getInputStream()) {
                    Path tempFile = Files.createTempFile(category.getValue(), ".txt");
                    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                    lines = Files.readAllLines(tempFile);
                }
                csvFileImportService.importData(lines, category);
                propertiesMap.put("message", String.format("Csv file[%sS] imported successfully!!!", category.name()));
                propertiesMap.put("result", Boolean.TRUE);
            }
        } catch (IOException ex) {
            propertiesMap.put("message", "Failed to upload the csv file![" + ex + "]");
        }
        return propertiesMap;
    }
}
