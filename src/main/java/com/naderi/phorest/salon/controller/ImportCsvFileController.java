package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.Category;
import com.naderi.phorest.salon.service.CsvFileImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class ImportCsvFileController {

    private final CsvFileImportService csvFileImportService;

    @Autowired
    public ImportCsvFileController(CsvFileImportService csvFileImportService) {
        this.csvFileImportService = csvFileImportService;
    }

    @GetMapping("/secure/uploadCsvFile")
    public String listUploadedFiles(Model model) {
        model.addAttribute("importFileResult", "Import your file:");
        return "pages/importCsvFile";
    }


    @PostMapping("/secure/uploadCsvFile")
    public String importFile(Model model, @RequestParam("csvFile") MultipartFile csvFile, @RequestParam("category") Category category) {
        List<String> lines = null;
        try {
            if (csvFile.isEmpty()) {
                model.addAttribute("error", "File is empty or invalid!");
            }
            try (InputStream inputStream = csvFile.getInputStream()) {
                Path tempFile = Files.createTempFile(category.getValue(), ".txt");
                Files.copy(inputStream, tempFile,
                        StandardCopyOption.REPLACE_EXISTING);
                lines = Files.readAllLines(tempFile);
            }
            model.addAttribute("importFileResult", "Csv file[" + category.getValue() + "] imported successfully!!!");

        } catch (IOException ex) {
            model.addAttribute("error", "Failed to upload the csv file![" + ex + "]");

        }
        csvFileImportService.importData(lines, category);
        return "pages/importCsvFile";
    }


}
