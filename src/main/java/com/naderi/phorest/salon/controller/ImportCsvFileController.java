package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.CsvFileCategory;
import com.naderi.phorest.salon.service.CsvFileImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
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
    public String importCsvFile(Model model, @NotNull @RequestParam("csvFile") MultipartFile csvFile, @NotNull @RequestParam("csvFileCategory") CsvFileCategory csvFileCategory) {
        model.addAllAttributes(csvFileImportService.importCsvFile(csvFile, csvFileCategory));
        model.addAttribute("category", csvFileCategory.name());
        return "pages/importCsvFile";
    }

    @PutMapping("/restapi/v1/upload-csv-file")
    @ResponseBody
    public ResponseEntity<?> importCsvFile(@NotNull @RequestParam("csvFile") MultipartFile csvFile, @NotNull @RequestParam("csvFileCategory") CsvFileCategory csvFileCategory) {
        Map<String, Object> map = csvFileImportService.importCsvFile(csvFile, csvFileCategory);
        return (boolean) map.get("result") ? ResponseEntity.ok(map.get("message")) : new ResponseEntity<>(map.get("message"), HttpStatus.BAD_REQUEST);
    }


}
