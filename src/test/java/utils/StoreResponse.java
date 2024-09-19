package utils;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StoreResponse {
    public static TestContext context;
    public static void WriteToFile(String content) {
        Path outputFilePath = Paths.get("src/test/java/response/response.txt");

        JSONObject jsonObject = new JSONObject(content);
        String orderId = jsonObject.getString("order_id");
        String webLink = jsonObject.getJSONObject("payment_links").getString("web");

        TestContext.weblink = webLink;
        System.err.println("weblink: " + TestContext.weblink);
        try (FileWriter writer = new FileWriter(outputFilePath.toFile(), true)) {
            writer.write("Order ID: " + orderId + System.lineSeparator());
            writer.write("Web Link: " + webLink + System.lineSeparator());
            writer.write(System.lineSeparator());

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

}
