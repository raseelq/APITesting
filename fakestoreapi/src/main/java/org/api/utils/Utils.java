package org.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import okhttp3.Response;
import org.api.clients.RestClient;
import org.api.models.api.HTTPResponse;
import org.api.models.api.HttpMethod;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static Map<String, String> createHeader(String key, String value) {
        Map<String, String> header = new HashMap<>();
        header.put(key, value);
        return header;
    }

    public static Map<String, String> createJsonHeader() {
        Map<String, String> jsonHeader = createHeader("content-type", "application/json; charset=UTF-8");
        return jsonHeader;
    }

    public static LocalDate dateFormatter(String date) {
        DateTimeFormatter fullDateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(date, fullDateformatter);
        }
    }

    public static <T> T mapResponseToObject(HTTPResponse response, Class<T> type) throws IOException, RestClient.HttpRequestException {
        if (response.getCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            if (response.getBody() != "") {
                return mapper.readValue(response.getBody(), type);
            } else return null;
        }else{
            throw new RestClient.HttpRequestException("Failed to execute HTTP request "+ response.getCode());
        }
    }

    public static <T> List<T> mapResponseToObjectsList(HTTPResponse response, Class<T> type) throws JsonProcessingException, RestClient.HttpRequestException {
        if (response.getCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            //helps in creating a CollectionType for a list containing elements of the specified type.
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.readValue(response.getBody(), listType);
        }else{
            throw new RestClient.HttpRequestException("Failed to execute HTTP request "+ response.getCode());
        }

    }

    private static <T> TypeReference<List<T>> getListTypeReference(Class<T> type) {
        return new TypeReference<List<T>>() {
        };

    }

    public static List<List<String>> readCSVResourceFile(String filepath, String delimiter) throws IOException {
        String line;
        List<List<String>> values = new ArrayList<>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            while ((line = reader.readLine()) != null) {
                List<String> splitline = Arrays.asList(line.split(delimiter));
                values.add(splitline);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());

        }
        return values;
    }
    /**
     * Generate random strings that can be used by tests.
     * @param length The length of the generated string.
     * @return Generated String.
     */
    public static String generateRandomString(int length){
        String uuid =UUID.randomUUID().toString().replace("-","");
        if(length> uuid.length()){
            throw new IllegalArgumentException("Requested length is too long");
        }
        return uuid.substring(0,length);
    }
    public static int generateRandomInt(int min,int max){
        Random random=new Random();
        return random.nextInt((max-min)+1 +min);
    }
    public static double generateRandomDouble(){
        Random random=new Random();
        return random.nextDouble();
    }
    public static LocalDate generateRandomDate(String startDate,String endDate){
        long startEpochDay=dateFormatter(startDate).toEpochDay();
        long endEpochDay=dateFormatter(endDate).toEpochDay();
        long randomEpochDay=ThreadLocalRandom.current().nextLong(startEpochDay,endEpochDay+1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
    public static <T> T mapPayloadToObject(Map<String,Object> payload,Class<T> type) throws Exception {
        T instance;
        try {
            instance =type.getDeclaredConstructor().newInstance();
            for(Map.Entry<String ,Object> entry:payload.entrySet()){
                String fieldName=entry.getKey();
                Object fieldValue=entry.getValue();
                Field field=type.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance,fieldValue);
            }
        }
         catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("Field not found or not accessible: " + e.getMessage());
        }
        catch (Exception e) {
            throw new Exception("Failed to map payload to object");
        }
        return instance;

    }
}


