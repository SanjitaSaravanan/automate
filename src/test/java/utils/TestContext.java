package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import pojo.ArgsSpecified;
import pojo.OrderRequest;
import testdatabuild.ObjectManager;

public class TestContext {
    public ObjectManager objectManager;
    public OrderRequest orderRequest;
    public static org.apache.poi.ss.usermodel.Sheet sheet;
    public static int testCaseCounter;
    public static String TestSheet;
    public static String sessionId;
    public static String response_body;
    public static String curlCommand;
    public static String scenarioLine;
    public static String endPoint;
    public static String payment_method_type;
    public static String payment_method;
    public static boolean failbuild=false;
    public static int totalScenarios;
    public static int passedScenarios;
    public static int failedScenarios;
//    public static APIResources title;
    public static Row scenarioRow;
    public static String responseBody;
    public static int rowNum =1;
    public static CellRangeAddress mergeCells;
    public RequestSpecification request;
    public static String weblink;

    public static String error;
    public Response response;

    public ArgsSpecified argsSpecified;

    public TestContext() {
        objectManager = new ObjectManager();
    }

}
