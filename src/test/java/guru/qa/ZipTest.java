package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipTest {
    private final ClassLoader cl = ZipTest.class.getClassLoader();

    @DisplayName("Проверка pdf контента в zip архиве")
    @Test
    void checkPdfContentTest() throws Exception {
        try (ZipInputStream zipInputStream = openZip()) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                final String name = entry.getName();
                if (name.contains(".pdf")) {
                    PDF pdf = new PDF(zipInputStream);
                    Assertions.assertTrue(pdf.text.contains("Lorem ipsum"));
                }
            }
        }
    }

    @DisplayName("Проверка csv контента в zip архиве")
    @Test
    void checkCsvContentTest() throws Exception {
        try (ZipInputStream zipInputStream = openZip();
             Reader reader = new InputStreamReader(zipInputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                final String name = entry.getName();
                if (name.contains(".csv")) {
                    final CSVReader csv = new CSVReader(reader);
                    List<String[]> strings = csv.readAll();

                    Assertions.assertEquals(3, strings.size());
                    Assertions.assertArrayEquals(new String[]{"Jobs", "Salary"}, strings.get(0));
                    Assertions.assertArrayEquals(new String[]{"QA Engineer", "150000"}, strings.get(1));
                    Assertions.assertArrayEquals(new String[]{"Designer", "100000"}, strings.get(2));
                }
            }
        }
    }

    @DisplayName("Проверка xlsx контента в zip архиве")
    @Test
    void checkXlsxContentTest() throws Exception {
        try (ZipInputStream zipInputStream = openZip()) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                final String name = entry.getName();
                if (name.contains(".xlsx")) {
                    XLS xls = new XLS(zipInputStream);
                    Assertions.assertEquals("Car", xls.excel.getSheetAt(0)
                            .getRow(1)
                            .getCell(0)
                            .getStringCellValue());
                }
            }
        }
    }

    private ZipInputStream openZip() {
        InputStream is = cl.getResourceAsStream("result.zip");
        return new ZipInputStream(is);
    }
}
