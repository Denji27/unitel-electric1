package la.com.unitel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : Tungct
 * @since : 12/6/2022, Tue
 **/
@Service
@Slf4j
public class ExcelUtil {
    @Autowired
    Util util;
    private Workbook workbook;
    private Sheet sheet;

    public Map<String, Set<String>> readFileCodeUpload(String filePath, String password) {
        /*if (!isExcelFileProtected(filePath)) {
            log.warn("This file {} is not protected", filePath);
            return null;
        }*/
        Map<String, Set<String>> codeExtractMap= new HashMap<>();
        try {
            if (password != null) {
                workbook = WorkbookFactory.create(new FileInputStream(filePath), password);
            } else {
                workbook = WorkbookFactory.create(new FileInputStream(filePath));
            }
            sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (!util.isDateStringInvalidFormat(row.getCell(2).getStringCellValue())) {
                    log.error("Row {}, {} is invalid date format", i, row.getCell(2).getStringCellValue());
                    return null;
                }
                String merchandiseCode = row.getCell(0).getStringCellValue();
                String stockCode = row.getCell(1).getStringCellValue();
                if (codeExtractMap.containsKey(merchandiseCode)){
                    Set<String> curVal = codeExtractMap.get(merchandiseCode);
                    curVal.add(stockCode);
                    codeExtractMap.put(merchandiseCode, curVal);
                } else {
                    codeExtractMap.put(merchandiseCode, new HashSet<>(Collections.singleton(stockCode)));
                }
            }
        } catch (IOException e) {
            log.error("Read file error due to ", e);
            return null;
        }
        return codeExtractMap;
    }

    public void createSiteRouterRequestTemplate(HttpServletResponse response) {
        // Create the Excel workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a sheet in the workbook
        XSSFSheet sheet = workbook.createSheet("Sheet 1");

        // Create a row in the sheet
        XSSFRow row = sheet.createRow(0);

        // Create a cell in the row
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("SITE_ROUTER_CODE");

        cell = row.createCell(1);
        cell.setCellValue("RING_SITE_ROUTER_CODE");

        cell = row.createCell(2);
        cell.setCellValue("IP_CONNECT_MAP");

        XSSFCellStyle style = workbook.createCellStyle();

        // Create a font for the style
        XSSFFont font = workbook.createFont();
        font.setBold(true); // Set the font to bold
        style.setFont(font); // Set the font in the style
        style.setAlignment(HorizontalAlignment.CENTER); // Set the alignment to center

        // Apply the style to the cell
        cell.setCellStyle(style);

        // Set the response headers
        response.setHeader("Content-Disposition", "attachment; filename=template_srt.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Write the Excel file to the response output stream
        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExcelFileProtected(String filePath) {
        try {
            try {
                new POIFSFileSystem(new FileInputStream(filePath));
            } catch (IOException ex) {
                log.error("File exception due to ", ex);
                return false;
            }
            log.info("File protected");
            return true;
        } catch (OfficeXmlFileException e) {
            log.info("File not protected");
            return false;
        }
    }

    /*public void createExampleFile(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=staff_creation_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        try{
            workbook = new XSSFWorkbook();
            writeHeaderForTemplate();
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }catch (IOException e){
            log.error("ERROR: {}",e.getMessage());
            log.error("ERROR WHILE GETTING A TEMPLATE");
            throw new ExcelEx();
        }
    }*/
}
