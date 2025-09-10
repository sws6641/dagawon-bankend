package com.bankle.common.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.nimbusds.oauth2.sdk.ParseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static com.nimbusds.oauth2.sdk.util.JSONUtils.toList;
import static org.hibernate.internal.util.collections.CollectionHelper.toMap;

/**
 * GsonUtil
 *
 * @author sh.lee
 * @date 2023-09-18
 **/
@Slf4j
public class GsonUtil {
    private static String PATTERN_DATE = "yyyy-MM-dd";
    private static String PATTERN_TIME = "HH:mm:ss";
    private static String PATTERN_DATETIME = String.format("%s %s", PATTERN_DATE, PATTERN_TIME);

    private static Gson gson = new GsonBuilder().disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setDateFormat(PATTERN_DATETIME)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdaptor())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdaptor())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdaptor()).create();

    public static String toJson(Object o) {
        String result = gson.toJson(o);
        if ("null".equals(result))
            return null;
        return result;
    }

    public static String getHeader(HttpServletRequest request) {

        Enumeration<String> headerElement = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();

        while (headerElement.hasMoreElements()) {
            String headerName = headerElement.nextElement();
            String headerValue = request.getHeader(headerName);
            sb.append(headerName + " : " + headerValue + System.getProperty("line.separator"));
            log.warn("sanghyup:" + headerName + " : " + headerValue);
        }
        return sb.toString();
    }

    public static String getBody(Object o) {
        String result = gson.toJson(o);
        if ("null".equals(result))
            return "";
        return result;
    }

    public static <T> T fromJson(String s, Class<T> clazz) {
        try {
            return gson.fromJson(s, clazz);
        } catch (JsonSyntaxException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Object> toLMap(JSONObject object) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        Iterator<String> keysItr = object.keySet().iterator();

        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                try {
                    value = toList((JSONArray) value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    static class LocalDateTimeAdaptor extends TypeAdapter<LocalDateTime> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DATETIME);

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value != null)
                out.value(value.format(format));
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString(), format);
        }
    }

    static class LocalDateAdaptor extends TypeAdapter<LocalDate> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DATE);

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.format(format));
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString(), format);
        }
    }

    static class LocalTimeAdaptor extends TypeAdapter<LocalTime> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_TIME);

        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.value(value.format(format));
        }

        @Override
        public LocalTime read(JsonReader in) throws IOException {
            return LocalTime.parse(in.nextString(), format);
        }
    }
}
