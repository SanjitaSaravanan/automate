//package utils;
//
//import io.cucumber.java.After;
//import io.cucumber.java.AfterAll;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import io.restassured.specification.FilterableRequestSpecification;
//import org.apache.poi.openxml4j.util.ZipSecureFile;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import pojo.RequestDetails;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.*;
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ReportGeneration {
//    public TestContext context;
//    public Scenario scenario;
//    private static Workbook workbook;
//    Row apiRow;
//    private String segment;
//    private static List<String> scenarioNames = new ArrayList<>();
//    private static int totalScenarios = 0;
//    private static int passedScenarios = 0;
//    private static int failedScenarios = 0;
//
//    public ReportGeneration(TestContext context) {
//        this.context = context;
//    }
//
//    static {
//        if (workbook == null) {
//            workbook = new XSSFWorkbook();
//        }
//        ZipSecureFile.setMinInflateRatio(0.005);
//        TestContext.sheet = workbook.getSheet("TestSheet");
//        if (TestContext.sheet == null) {
//            TestContext.sheet = workbook.createSheet("TestSheet");
//            Row headerRow0 = TestContext.sheet.createRow(0);
//            CellStyle style =   TestContext.sheet.getWorkbook().createCellStyle();
//            Font boldFont =   TestContext.sheet.getWorkbook().createFont();
//            boldFont.setBold(true);
//            TestContext.sheet.addMergedRegion(CellRangeAddress.valueOf("A1:L1"));
//            Row headerRow = TestContext.sheet.createRow(1);
//            try {
//                XSSFColor lightblue = new XSSFColor(new byte[] { (byte) 135, (byte) 206, (byte) 250 });
//                style.setFillForegroundColor(lightblue);
//                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                style.setFont(boldFont);
//                headerRow0.createCell(0).setCellValue("MerchantId:    " + ReusableMethods.getGlobalValue("merchant_id"));
//                headerRow0.getCell(0).setCellStyle(style);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            headerRow.createCell(0).setCellValue("TestCaseId");
//            headerRow.createCell(1).setCellValue("OrderId");
//            headerRow.createCell(2).setCellValue("Title");
//            headerRow.createCell(3).setCellValue("Gateway");
//            headerRow.createCell(4).setCellValue("PaymentMethodType");
//            headerRow.createCell(5).setCellValue("PaymentMethod");
//            headerRow.createCell(6).setCellValue("RequestCurl");
//            headerRow.createCell(7).setCellValue("Response");
//            headerRow.createCell(8).setCellValue("SessionId");
//            headerRow.createCell(9).setCellValue("Status");
//            headerRow.createCell(10).setCellValue("FailureReasons");
//            headerRow.createCell(11).setCellValue("Logs");
//            for (int i = 0; i < 11; i++) {
//                TestContext.sheet.setColumnWidth(i, 30 * 306);
//            }
//
//        }
//        TestContext.testCaseCounter = 1;
//    }
//    public RequestDetails requestDetails() {
//        RequestDetails requestDetails = context.objectManager.getRequestDetails();
//        requestDetails
//                .setBaseUri(((FilterableRequestSpecification) context.request.log().uri()).getBaseUri().toString());
//        requestDetails.setBasePath(TestContext.endPoint);
//
//        requestDetails.setMethod(((FilterableRequestSpecification) context.request.log().uri()).getMethod().toString());
//        if (((FilterableRequestSpecification) context.request.log().uri()).getBody() != null) {
//            requestDetails.setBody(((FilterableRequestSpecification) context.request.log().uri()).getBody().toString());
//        }
//        return requestDetails;
//
//    }
//
//    @Before
//    public void before(Scenario scenario) throws Exception {
//        this.scenario = scenario;
//        scenarioNames = new ArrayList<>();
//        if (scenario.getName() != null) {
//            TestContext.scenarioLine = extractFeatureLine(scenario.getName());
//            scenarioNames.add(TestContext.scenarioLine);
//            ReusableMethods.extractPmt();
//        }
//        SpecBuilders.blackBoxSetUp();
//    }
//
//    public static List<String> getScenarioNames() {
//        return scenarioNames;
//    }
//
//    //merging cell to store scenario name
//    @Before
//    public void before() {
//        List<ReportConfigurations> reportConfigurations = new ArrayList<>();
//        ReportConfigurations reportConfiguration = new ReportConfigurations();
//        reportConfiguration.setRequest("RequestCurl");
//        reportConfiguration.setResponse("Response");
//        reportConfiguration.setSession("Session");
//        reportConfigurations.add(reportConfiguration);
//        TestContext.rowNum = TestContext.sheet.getLastRowNum() + 1;
//        for (ReportConfigurations reportConfig : reportConfigurations) {
//            TestContext.scenarioRow = TestContext.sheet.createRow(TestContext.rowNum);
//
//            TestContext.mergeCells = new CellRangeAddress(TestContext.rowNum, TestContext.rowNum, 0, 11); // A2:B2
//            boolean canMerge = true;
//
//            for (int i = 0; i < TestContext.sheet.getNumMergedRegions(); i++) {
//                CellRangeAddress existingMerge = TestContext.sheet.getMergedRegion(i);
//                if (existingMerge.isInRange(TestContext.mergeCells.getFirstRow(),
//                        TestContext.mergeCells.getFirstColumn())
//                        || existingMerge.isInRange(TestContext.mergeCells.getLastRow(),
//                        TestContext.mergeCells.getLastColumn())) {
//                    canMerge = false;
//                    break;
//                }
//            }
//
//            if (canMerge) {
//                TestContext.sheet.addMergedRegion(TestContext.mergeCells);
//            }
//
////			TestContext.scenarioRow.createCell(0).setCellValue(TestContext.scenarioLine);
//
//        }
//    }
//
//    //storing the datas in excel sheet
//    public void storeDatas() throws IOException {
//        TestContext.rowNum = TestContext.sheet.getLastRowNum() +1;
//        apiRow = TestContext.sheet.createRow(TestContext.rowNum);
//        apiRow.createCell(0).setCellValue("TC" + String.format("%03d", TestContext.testCaseCounter++));
//        apiRow.createCell(1).setCellValue(TestContext.scenarioLine);
//        apiRow.createCell(2).setCellValue(TestContext.title.toString());
//        apiRow.createCell(3).setCellValue(ReusableMethods.gateway());
//        apiRow.createCell(4).setCellValue(TestContext.payment_method_type);
//        apiRow.createCell(5).setCellValue(TestContext.payment_method);
//
//        apiRow.createCell(6).setCellValue(TestContext.curlCommand);
//        int maxCellLength = 32767; // Maximum length for each cell
//
//        // Split the long text into smaller segments
//        int segmentCount = (int) Math.ceil((double) TestContext.response_body.length() / maxCellLength);
//
//        for (int i = 0; i < segmentCount; i++) {
//            int startIndex = i * maxCellLength;
//            int endIndex = Math.min((i + 1) * maxCellLength, TestContext.response_body.length());
//
//            segment = TestContext.response_body.substring(startIndex, endIndex);
//        }
//        apiRow.createCell(7).setCellValue(segment.replaceAll("\\\\", ""));
//        apiRow.createCell(8).setCellValue(TestContext.sessionId);
//
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setWrapText(true);
//        apiRow.getCell(1).setCellStyle(cellStyle);
//        apiRow.getCell(2).setCellStyle(cellStyle);
//        apiRow.getCell(3).setCellStyle(cellStyle);
//        apiRow.getCell(4).setCellStyle(cellStyle);
//        TestContext.sheet.autoSizeColumn(0);
//        apiRow.setHeightInPoints(121);
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream("TestSheet.xlsx");
//            workbook.write(outputStream);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String extractFeatureLine(String stepText) {
//        Pattern pattern = Pattern.compile("Scenario:(.*)");
//        Matcher matcher = pattern.matcher(stepText);
//
//        if (matcher.find()) {
//            return matcher.group(2).trim();
//
//        }
//
//        return stepText;
//    }
//
//
//    @After
//    public void after(Scenario scenario) throws InterruptedException {
//        // Your existing code
//        // Increment passedScenarios or failedScenarios based on scenario status
//        if (scenario.getStatus().toString().equalsIgnoreCase("PASSED")) {
//            TestContext.passedScenarios++;
//        } else if (scenario.getStatus().toString().equalsIgnoreCase("FAILED")) {
//            TestContext.failedScenarios++;
//            TestContext.failbuild=true;
//        }
//        TestContext.totalScenarios++;
//
//    }
//    @After
//    public void after() throws InterruptedException {
//
//        Cell scenarioNameCell = TestContext.scenarioRow.createCell(0);
//        String status = scenario.getStatus().toString();
//
//        Row lastRow = TestContext.sheet.getRow( TestContext.sheet.getLastRowNum());
//
//        if (status.equalsIgnoreCase("FAILED")) {
//            CellStyle failedCellStyle = workbook.createCellStyle();
//            XSSFColor lightRed = new XSSFColor(new byte[] { (byte) 255, (byte) 99, (byte) 71 });
//            failedCellStyle.setFillForegroundColor(lightRed);
//            failedCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            Cell statusCell = lastRow.createCell(9);
//            statusCell.setCellValue("Failed");
//            statusCell.setCellStyle(failedCellStyle);
//            TestContext.scenarioRow.createCell(0).setCellStyle(failedCellStyle);
//            CommonStepDefinations commonStepDefinations = context.objectManager.getCommonStepDefinations(context);
//            CommonStepDefinations.logError(scenario);
//            Cell statusCell1=lastRow.createCell(10);
//            statusCell1.setCellValue(TestContext.error);
//        } else if (status.equalsIgnoreCase("PASSED")) {
//            CellStyle passedCellStyle = workbook.createCellStyle();
//            XSSFColor lightGreen = new XSSFColor(new byte[] { (byte) 0, (byte) 148, (byte) 0 });
//            passedCellStyle.setFillForegroundColor(lightGreen);
//            passedCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            Cell statusCell = lastRow.createCell(9);
//            statusCell.setCellValue("PASSED");
//            statusCell.setCellStyle(passedCellStyle);
//            TestContext.scenarioRow.createCell(0).setCellStyle(passedCellStyle);
//        }
//        TestContext.error=null;
//        try {
//            FileOutputStream outputStream = new FileOutputStream(
//                    "TestSheet.xlsx");
//            workbook.write(outputStream);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterAll
//    public static void generateSummary() {
//
//        Workbook workbook = TestContext.sheet.getWorkbook();
//        Sheet sheet = TestContext.sheet;
//
//        // Look for existing summary row
//        Row summaryRow = null;
//        for (Row row : sheet) {
//            Cell firstCell = row.getCell(0);
//            if (firstCell != null && firstCell.getStringCellValue() != null
//                    && firstCell.getStringCellValue().equals("Summary")) {
//                summaryRow = row;
//                break;
//            }
//        }
//
//        // If summary row does not exist, create a new one
//        if (summaryRow == null) {
//            TestContext.sheet.shiftRows(0,  TestContext.sheet.getLastRowNum(), 1, true, false);
//            summaryRow =  TestContext.sheet.createRow(0);
//
//            // Create cell style for summary row
//            CellStyle summaryStyle = workbook.createCellStyle();
//            summaryStyle.setFillForegroundColor(new XSSFColor(new byte[]{11, 83, (byte) 148}));
//            summaryStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//            // Create font for cell style
//            Font font = workbook.createFont();
//            font.setColor(IndexedColors.WHITE.getIndex());
//            summaryStyle.setFont(font);
//
//            // Apply style to each cell in the summary row
//            for (int i = 0; i < 12; i++) {
//                Cell cell = summaryRow.createCell(i);
//                cell.setCellStyle(summaryStyle);
//            }
//
//            // Set the identifier text in the first cell of the summary row
//            summaryRow.getCell(0).setCellValue("Summary");
//        }
//
//        // Reset counts to zero at the beginning
//
//
//        // Your logic to update totalScenarios, passedScenarios, and failedScenarios
//
//        // Set the values for the summary row
//        summaryRow.getCell(1).setCellValue("Total Scenarios: " + TestContext.totalScenarios);
//        summaryRow.getCell(2).setCellValue("Passed Scenarios: " + TestContext.passedScenarios);
//        summaryRow.getCell(3).setCellValue("Failed Scenarios: " + TestContext.failedScenarios);
//
//        // Set the height of the summary row
//        summaryRow.setHeightInPoints(25);
//
//        // Your existing code to write to workbook and save file
//        try {
//            FileOutputStream outputStream = new FileOutputStream("TestSheet.xlsx");
//            workbook.write(outputStream);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        scheduleFileArchiving();
//    }
//
//
//    public void countScenarios() throws IOException {
//        try {
//            FileInputStream inputStream = new FileInputStream("TestSheet.xlsx"); // Update with your report file path
//            workbook = new XSSFWorkbook(inputStream);
//            TestContext.sheet = workbook.getSheetAt(0);
//
//            int lastRowWithSummary = -1;
//
//            // Iterate through the rows to find the last row with "summary"
//            for (int i = 0; i <= TestContext.sheet.getLastRowNum(); i++) {
//                Row row = TestContext.sheet.getRow(i);
//                Cell cell = row.getCell(1); // Assuming the "summary" is in the first cell/column
//
//                if (cell != null && cell.getCellType() == CellType.STRING
//                        && cell.getStringCellValue().equals("Summary")) {
//                    lastRowWithSummary = i;
//                }
//            }
//
//            // Iterate through the rows to delete rows with "summary" except the very last
//            // one
//            for (int i = 0; i < TestContext.sheet.getLastRowNum(); i++) {
//                if (i != lastRowWithSummary) {
//                    Row row = TestContext.sheet.getRow(i);
//                    Cell cell = row.getCell(1); // Assuming the "summary" is in the first cell/column
//
//                    if (cell != null && cell.getCellType() == CellType.STRING
//                            && cell.getStringCellValue().equals("Summary")) {
//                        TestContext.sheet.removeRow(row);
//                        TestContext.sheet.shiftRows(i + 1, TestContext.sheet.getLastRowNum(), -1); // Shift remaining
//                        // rows up
//                        i--; // Adjust the index after row removal
//                    }
//                }
//            }
//
//            inputStream.close();
//
//            // Save the modified Excel file
//            FileOutputStream fos = new FileOutputStream("TestSheet.xlsx");
//            workbook.write(fos);
//            fos.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static void scheduleFileArchiving() {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//        // Schedule the file archiving task to run every 24 hours
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
//                archiveFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }, 0, 24, TimeUnit.HOURS);
//    }
//
//    private static void archiveFile() throws IOException {
//
//        String archiveFolderPath = "archive";
//
//        // Ensure that the archive folder exists
//        createArchiveFolder(archiveFolderPath);
//
//        // Construct the file paths
//        Path sourceDirectory = Paths.get("TestSheet.xlsx");
//        Path archiveDirectory = Paths.get("archive");
//
//        String timestamp = new SimpleDateFormat("ddMM_HHmmss").format(new Date(System.currentTimeMillis()));
//        TestContext.TestSheet="TestSheet_" + timestamp + ".xlsx";
//        Path destinationPath = archiveDirectory.resolve(TestContext.TestSheet);
//        Files.move(sourceDirectory, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//
//        System.out.println("File moved to archive: " + destinationPath);
//
//        long currentTimeMillis = System.currentTimeMillis();
//
//        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(archiveDirectory, "*.xlsx")) {
//            for (Path filePath : directoryStream) {
//                long lastModifiedMillis = Files.getLastModifiedTime(filePath).toMillis();
//
//                if (currentTimeMillis - lastModifiedMillis > 24 * 60 * 60 * 10000) {
//                    Files.delete(filePath);
//                    System.out.println("File deleted: " + filePath);
//                }
//            }
//        }
//    }
//
//    private static void createArchiveFolder(String archiveFolderPath) {
//        File archiveFolder = new File(archiveFolderPath);
//        if (!archiveFolder.exists()) {
//            archiveFolder.mkdirs();
//        }
//    }
//}
