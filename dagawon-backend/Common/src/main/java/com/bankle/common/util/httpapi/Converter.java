package com.bankle.common.util.httpapi;

import com.bankle.common.util.httpapi.vo.BaseRequestVo;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class Converter {

    public final static String MultiValueMap2UriStr(String url, MultiValueMap<String, String> queryParams) {
        if (queryParams == null)
            return url;
        return UriComponentsBuilder.fromUriString(url)
                .queryParams(queryParams)
                .build()
                .encode()
                .toUri().toString();
    }

    public final static String MultiValueMap2UriStrExt(String url, MultiValueMap<String, String> queryParams) {
        String Str = MultiValueMap2UriStr(url, queryParams);
        String[] strs = Str.split("\\?");
        StringBuilder sb = new StringBuilder();
        sb.append(strs[0]);
        if (strs.length > 1) {
            sb.append("?");
            sb.append(strs[1].replaceAll("\\+", "%2B").replaceAll("/", "%2F"));
        }
        return sb.toString();
    }

    public final static void SetHeader(HttpApi api, BaseRequestVo req, String header) {
        if (req.getHeaders().containsKey(header)) {
            List<String> vals = req.getHeaders().get(header);
            for (String val : vals) {
                System.out.println(String.format("SetHeader [%s:%s]", header, val));
                api.header(header, val);
            }
        }
    }
}
