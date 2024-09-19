package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SpecBuilders {
    public static RequestSpecification request;
    private static PrintStream log;
    private static Workbook workbook;
    private static Sheet sheet;
    private static FileOutputStream excelFile;

    // Initialize the log file and Excel workbook
    static {
        try {
            // Log file initialization
            log = new PrintStream(new FileOutputStream("logging.txt", false));

            // Excel file initialization
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("API Responses");
            // Create header row in Excel
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Order ID");

            // Output stream for the Excel file
            excelFile = new FileOutputStream(new File("OrderDetails.xlsx"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RequestSpecification authorization() throws IOException {
        RequestSpecification requestSpec = requestWithApiKey();
        return requestSpec;
    }

    public static RequestSpecification requestWithApiKey() throws IOException {
        if (request == null) {
            request = new RequestSpecBuilder().setBaseUri(ReusableMethods.getGlobalValue("baseUrl"))
                    .addHeader("Authorization", ReusableMethods.auth())
                    .addHeader("Cookie", ReusableMethods.getGlobalValue("cookie"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return request;
    }

    // Method to write order_id to the Excel file
    public static void writeOrderIdToExcel(String orderId) throws IOException {
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum + 1);  // Create a new row for the order ID
        Cell cell = row.createCell(0);
        cell.setCellValue(orderId);

        // Write to the Excel file
        workbook.write(excelFile);
    }

    // Method to close the resources
    public static void closeResources() throws IOException {
        log.close();
        workbook.close();
        excelFile.close();
    }
}
