package com.naderi.phorest.salon.service;


import com.naderi.phorest.salon.common.Category;

import java.util.List;

public interface CsvFileImportService {
    void importData(List<String> lines, Category category);
}
