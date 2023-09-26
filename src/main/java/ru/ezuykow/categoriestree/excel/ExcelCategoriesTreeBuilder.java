package ru.ezuykow.categoriestree.excel;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.entities.Category;
import ru.ezuykow.categoriestree.services.CategoryService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class ExcelCategoriesTreeBuilder {

    private static final String DESC = "➡";

    private final CategoryService categoryService;

    private Set<Category> allCategories;
    private int maxLevel;
    private int rowNum;

    private XSSFCellStyle descriptorCellStyle;

    //-----------------API START-----------------

    /**
     * Собирает дерево категорий в файл Excel
     * @author ezuykow
     */
    public void build(long ownerId, File file) throws IOException {
        allCategories = new HashSet<>(categoryService.findAllByOwnerId(ownerId));

        if (allCategories.isEmpty()) {
            return;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            changeWorkBookFont(workbook);
            createDescriptorCellStyle(workbook);

            XSSFSheet sheet = workbook.createSheet("Категории");
            maxLevel = 1;
            rowNum = 0;
            fillSheet(sheet, 1, null);

            for (int i = 0; i < maxLevel; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(new FileOutputStream(file));
        }
    }

    //-----------------API END-------------------

    private void fillSheet(XSSFSheet sheet, int level, Category parent) {
        TreeSet<Category> categories = new TreeSet<>(Comparator.comparing(Category::getName));

        maxLevel = Math.max(maxLevel, level);

        categories.addAll(allCategories.parallelStream()
                .filter(c -> (parent == null && c.getParent() == null) || c.getParent().equals(parent))
                .collect(Collectors.toSet()));
        allCategories.removeAll(categories);

        for (Category category : categories) {
            XSSFRow row = sheet.createRow(rowNum++);
            XSSFCell cell = row.createCell(level - 1);
            cell.setCellStyle(descriptorCellStyle);
            cell.setCellValue(DESC);
            cell = row.createCell(level);
            cell.setCellValue(category.getName());
            fillSheet(sheet, level + 1, category);
        }
    }

    private void changeWorkBookFont(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createCellStyle().getFont();
        font.setFontHeightInPoints((short) 14);
        workbook.createCellStyle().setFont(font);
    }

    private void createDescriptorCellStyle(XSSFWorkbook workbook) {
        descriptorCellStyle = workbook.createCellStyle();
        descriptorCellStyle.setAlignment(HorizontalAlignment.RIGHT);
    }
}
