package org.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static Map<String, String> createHeader(String key, String value) {
        Map<String, String> header = new HashMap<>();
        header.put(key, value);
        return header;
    }
    public static Map<String,String> createJsonHeader(){
        Map<String,String> jsonHeader=createHeader("content-type","application/json; charset=UTF-8");
        return jsonHeader;
    }
    public static LocalDate dateFormatter(String date) {
        DateTimeFormatter fullDateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        }catch (DateTimeParseException e){
            return LocalDate.parse(date,fullDateformatter);
        }
    }
    public static <T> T mapResponseToObject(String body, Class<T> type) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.readValue(body,type);
    }
    public static <T> List<T> mapResponseToObjectsList(String body,Class<T> type) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        //helps in creating a CollectionType for a list containing elements of the specified type.
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
        return mapper.readValue(body, listType);

    }
    private static <T> TypeReference<List<T>> getListTypeReference(Class<T> type){
        return new TypeReference<List<T>>() {};

    }


}

