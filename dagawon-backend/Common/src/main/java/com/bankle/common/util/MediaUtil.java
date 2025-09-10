package com.bankle.common.util;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * MediaUtil
 *
 * @author sh.lee
 * @date 2023-09-18
 **/
public class MediaUtil {
    private static Map<String, MediaType> mediaMap;

    static {
        mediaMap = new HashMap<String, MediaType>();
        mediaMap.put("JPG", MediaType.IMAGE_JPEG);
        mediaMap.put("GIF", MediaType.IMAGE_GIF);
        mediaMap.put("PNG", MediaType.IMAGE_PNG);
    }

    public static MediaType getMediaType(String mediaType) {
        return mediaMap.get(mediaType.toUpperCase());
    }
}

