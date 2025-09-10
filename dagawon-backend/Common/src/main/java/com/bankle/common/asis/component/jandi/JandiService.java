package com.bankle.common.asis.component.jandi;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor
public class JandiService {

    public boolean callJandi(JandiData jandiData, String strUrl) {

        boolean result = false;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost   = new HttpPost(strUrl);

        try {

            Gson gson = new Gson();
            log.debug("==============================================================================");
            log.debug("JandiService callJandi");
            log.debug("==============================================================================");
            log.debug("JANDI URL   : " + strUrl);
            log.debug("String JSON : " + gson.toJson(jandiData));
            log.debug("==============================================================================");

            StringEntity se = new StringEntity(gson.toJson(jandiData), "UTF-8");
            httpPost  .addHeader("Accept"      , "application/vnd.tosslab.jandi-v2+json");
            httpPost  .addHeader("Content-Type", "application/json;charset=UTF-8"       );
            httpPost  .setEntity(se);
            httpClient.execute  (httpPost);
            result = true;
        } catch (Exception Ex) {
            Ex.printStackTrace();
        } finally {
            try { httpClient.close(); } catch (Exception subEx) {}
        }

        return result;
    }
}
