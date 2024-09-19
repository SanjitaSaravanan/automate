package constants;

import java.util.HashMap;
import java.util.Map;

public class RequestBodyConstants {

    public static Map<String, String> apiKeyMap = new HashMap<>();
    static {
        apiKeyMap.put("testcredit", "QmFzaWMgTmpSRE5FVTBOamcyTURJME1UTXpPVE5CTURNMk56azJRakUxTWpNeA==");
        apiKeyMap.put("amity", "Basic Q0I0NzdEMDREOTc0NTM2QkI3QUQ1MkIwOEMzNzdG");
        apiKeyMap.put("starhealth", "QmFzaWMgTkVWQ05EVkZOa05DT0VJMFFUUkJRVE01UmpNNE9UUXhNVFEzUWtZMk9nPT0=");
        apiKeyMap.put("cashify", "Basic MENFRUEyRDQzMjc0OUUyOEU2MkMzODg3QzE4N0E3Og==");
    }
}
