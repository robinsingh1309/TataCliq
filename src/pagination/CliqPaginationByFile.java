package pagination;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import extract.DataTab;
import service.UrlConnect;

public class CliqPaginationByFile {

    private static final Logger logger = Logger.getLogger(CliqPaginationByFile.class.getName());
    private UrlConnect urlConnection;
    
    private static final int MAX_RECORDS_PER_FILE = 10_000;

    public CliqPaginationByFile() {
        urlConnection = new UrlConnect();
    }

    public void getAllDataAndWriteExcel(String filePathName, String outputDirectoryPath) throws IOException {

        try ( BufferedReader reader = new BufferedReader( new FileReader(filePathName) ) ) 
        {
            String line;
            int workbookCount = 0;
            int recordCount = 0;

            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = createSheetWithHeader(workbook);
            int rowNum = 1;

            while ( ( line = reader.readLine() ) != null ) 
            {    
                int page = 1;
                int totalPages = 1;

                while (page <= totalPages && page <= 250) {
                    
                    try {
                        String categoryUrl = line.replaceAll("page=\\d+", "page=" + page);
                        logger.info("Fetching category URL: " + categoryUrl);

                        String categoryResponse = urlConnection.getJsonResponse(categoryUrl);

                        
                        int written = DataTab.writeDataToExcel(categoryResponse, sheet, rowNum);
                        int newRecords = written - rowNum;
                        rowNum = written;
                        recordCount += newRecords;

                        if (page == 1) {
                            totalPages = DataTab.countPages(categoryResponse);
                            logger.info("Total pages: " + totalPages);
                        }
                        
                        // Flush workbook when 10,000 records written
                        if (recordCount >= MAX_RECORDS_PER_FILE) {
                            
                            String fileName = String.format("%s/data_%d.xlsx", outputDirectoryPath, workbookCount++);
                            
                            try (FileOutputStream out = new FileOutputStream(fileName)) {
                                workbook.write(out);
                                logger.info("Written file: " + fileName);
                            }
                            
                            workbook.close();

                            workbook = new XSSFWorkbook();
                            sheet = createSheetWithHeader(workbook);
                            rowNum = 1;
                            recordCount = 0;
                        }

                        page++;

                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Error fetching data from URL: " + line + " page: " + page, e);
                        break;
                    }
                }
                
                if (page > 250) {
                    logger.info("Stopped pagination at 250 pages for: " + line);
                }
                
            }
            
            // Write remaining records if any
            if (rowNum > 1) {
             
                String fileName = String.format("%s/data_%d.xlsx", outputDirectoryPath, workbookCount);
                
                try (FileOutputStream out = new FileOutputStream(fileName)) {
                    workbook.write(out);
                    logger.info("Written final file: " + fileName);
                }
                
                if (workbook != null) {
                    workbook.close();
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading file or writing Excel", e);
        }
    }
    
    private Sheet createSheetWithHeader(XSSFWorkbook workbook) {
        
        Sheet sheet = workbook.createSheet("TataCliq Data");
        String[] headers = {"Brand", "Product", "ProductCategory", "SellingPrice", "MRP", "Category", "Image"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
            headerRow.createCell(i).setCellValue(headers[i]);

        return sheet;
    }
    
    
}