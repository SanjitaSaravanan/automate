package utils;

import java.io.IOException;

import static constants.RequestBodyConstants.apiKeyMap;

public class RequestBodyMapper {

    public static String Authorization() throws IOException {
        String apiKey;
            String merchant_id = ReusableMethods.getGlobalValue("merchant_id");
            apiKey = apiKeyMap.get(merchant_id);
        return apiKey;
    }
}
