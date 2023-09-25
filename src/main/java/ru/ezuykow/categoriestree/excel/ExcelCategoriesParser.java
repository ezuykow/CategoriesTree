package ru.ezuykow.categoriestree.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.entities.Category;
import ru.ezuykow.categoriestree.exceptions.DocumentParsingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ezuykow
 */
@Component
public class ExcelCategoriesParser {

    private static final String DESC = "➡";

    //-----------------API START-----------------

    /**
     * Принимает скачанный с апдейта Excel файл, находит в нем дерево категорий и возвращает его списком
     * @param file скачанный с апдейта Excel файл
     * @return {@link List<Category>} с найденными в файле категориями
     * @author ezuykow
     */
    public List<Category> parse(File file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            return findNewCategoriesInSheet(workbook.getSheetAt(0));
        } catch (IOException e) {
            throw new DocumentParsingException();
        } finally {
            file.delete();
        }
    }

    //-----------------API END-------------------

    private List<Category> findNewCategoriesInSheet(XSSFSheet sheet) {
        List<Category> newCategories = new ArrayList<>();
        Map<Integer, Category> categoriesLevels = new HashMap<>();
        AtomicInteger maxLevel = new AtomicInteger(0);

        sheet.rowIterator().forEachRemaining(row -> {
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getStringCellValue().equals(DESC)) {
                    break;
                }
            }

            if (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String categoryName = cell.getStringCellValue();
                int categoryLevel = cell.getColumnIndex();
                for (int i = categoryLevel; i <= maxLevel.get() ; i++) {
                    categoriesLevels.remove(i);
                }
                maxLevel.set(categoryLevel);
                Category newCategory = new Category(categoriesLevels.get(categoryLevel - 1), categoryName);
                categoriesLevels.put(categoryLevel, newCategory);
                newCategories.add(newCategory);
            }
        });

        return newCategories;
    }
}
