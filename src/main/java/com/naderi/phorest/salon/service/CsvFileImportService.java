package com.naderi.phorest.salon.service;


import com.naderi.phorest.salon.common.CsvFileCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CsvFileImportService {
    void importData(List<String> lines, CsvFileCategory csvFileCategory);

    Map<String, Object> importCsvFile(MultipartFile csvFile, CsvFileCategory csvFileCategory);
}
