package org.api.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
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


}

