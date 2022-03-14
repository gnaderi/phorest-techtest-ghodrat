package com.naderi.phorest.salon.service;

import com.naderi.phorest.salon.dto.Category;

import java.util.List;

public interface CsvFileImportService {
    void importData(List<String> lines, Category category);
}
