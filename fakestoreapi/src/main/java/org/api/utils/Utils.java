package org.api.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Map<String, String> createHeader(String key, String value) {
        Map<String, String> header = new HashMap<>();
        header.put(key, value);
        return header;
    }


}
