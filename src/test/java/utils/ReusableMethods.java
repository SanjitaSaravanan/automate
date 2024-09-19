package utils;

import lombok.Data;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class ReusableMethods {

//    private Properties properties = new Properties();
//    File file = new File("src/test/resources/global.properties");
//    public String getproperty(String key){
//        System.out.println(key + "key--------");
//        System.out.println(properties.getProperty(key) + "from global -------------");
//        return properties.getProperty(key);
//    }
        public static String getGlobalValue(String key) throws IOException {
            Map<String, String> properties = loadOrCreateProperties();
            return properties.get(key);
        }


            public static void setGlobalValue(String key , String value) throws IOException {
                Map<String, String> properties = loadOrCreateProperties();
                updateProperty(properties, key, value);
                saveProperties(properties, "src/test/java/resources/global.properties");
            }
            private static Map<String, String> loadOrCreateProperties() throws IOException {
                File file = new File("src/test/java/resources/global.properties");
                Map<String, String> defaultProperties = new LinkedHashMap<>();
                if (file.exists()) {
                    return loadProperties();
                } else {
                    defaultProperties.put("baseUrl", "https://sandbox.juspay.in");
                    defaultProperties.put("payment_page_client_id", "cashify");
                }
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    saveProperties(defaultProperties, "src/test/java/resources/global.properties"); //
                }// Save default properties to the file

                return defaultProperties;
            }

        private static Map<String, String> loadProperties() {
            Map<String, String> properties = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("src/test/java/resources/global.properties"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#") && line.contains("=")) {
                        String[] parts = line.split("=", 2);
                        properties.put(parts[0], parts[1]);
                    } else if (line.trim().isEmpty()) {
                        properties.put("", ""); // Add blank line to properties map
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return properties;
        }

        private static void updateProperty(Map<String, String> properties, String key, String value) {
            properties.put(key, value);
        }
        private static void saveProperties(Map<String, String> properties, String fileName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("#Updated properties\n");
                writer.write("#" + new java.util.Date() + "\n");
                for (Map.Entry<String, String> entry : properties.entrySet()) {
                    if (!entry.getKey().isEmpty()) {
                        writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
                    } else {
                        writer.write("\n"); // Write a blank line
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Generates a random digit between 0 and 9
        }
        return sb.toString();
    }
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static String auth() throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(RequestBodyMapper.Authorization());
        String decodedApiKey = new String(decodedBytes);
        return decodedApiKey;

    }
}
