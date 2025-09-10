package com.bankle.common.util.httpapi;

import com.google.gson.Gson;
import com.bankle.common.util.StringUtil;
import com.bankle.common.util.httpapi.vo.BaseResponseVo;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class HttpApi {

    private int connTimeout = 60000;
    private int respTimeout = 60000;
    private int readTimeout = 60000;
    private int writeTimeout = 60000;
    /*
     * private int connTimeout = 10000; private int respTimeout = 10000; private int
     * readTimeout = 10000; private int writeTimeout = 10000;
     */
    private final String baseUrl;
    private final HttpMethod method;
    private final boolean isLogging;
    private String path;

    /**
     * 저장을 위하여 추가함.
     */
    private String transId;
    private String classType;
    private String createId;
    private String origin;
    private String destination;
    private String apiType;

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getRespTimeout() {
        return respTimeout;
    }

    public void setRespTimeout(int respTimeout) {
        this.respTimeout = respTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    private HttpApi(HttpMethod method, String baseUrl, boolean isLogging) {
        this.method = method;
        this.isLogging = isLogging;
        this.baseUrl = baseUrl;
    }

    /* 무조건 모든 api는 저장을 할 것이므로 true */
    public final static HttpApi create(HttpMethod method, String baseUrl) {
        return new HttpApi(method, baseUrl, true);
    }

    /* 만약 api 저장이 필요없을 경우 false */
    public final static HttpApi create(HttpMethod method, String baseUrl, boolean isLogging) {
        return new HttpApi(method, baseUrl, isLogging);
    }

    private final Map<String, String> headers = new LinkedHashMap<String, String>();

    public final HttpApi header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public final HttpApi header(Map<String, Object> values) {
        for (Entry<String, Object> entry : values.entrySet()) {
            headers.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return this;
    }

    public final HttpApi path(String path) {
        this.path = path;
        return this;
    }

    public final HttpApi transId(String transId) {
        this.transId = transId;
        return this;
    }

    public final HttpApi classType(String classType) {
        this.classType = classType;
        return this;
    }

    public final HttpApi createId(String createId) {
        this.createId = createId;
        return this;
    }

    public final HttpApi origin(String origin) {
        this.origin = origin;
        return this;
    }

    public final HttpApi destination(String destination) {
        this.destination = destination;
        return this;
    }

    public final HttpApi apiType(String apiType) {
        this.apiType = apiType;
        return this;
    }

    private BodyInserter<?, ? super ClientHttpRequest> inserter = null;

    public final HttpApi inserter(BodyInserter<?, ? super ClientHttpRequest> inserter) {
        this.inserter = inserter;
        return this;
    }

    public static void logTraceResponse(Logger log, ClientResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("Response status: {}", response.statusCode());
            log.debug("Response headers: {}", response.headers().asHttpHeaders());
            response.bodyToMono(String.class).publishOn(Schedulers.boundedElastic()).block();
        }
    }

    public final static Map<String, Boolean> GetHeaderListFromAnnotation(Class<?> responseType) {
        Map<String, Boolean> heads = new HashMap<String, Boolean>();
        Annotation[] annotations = responseType.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof HeaderFilters) {
                HeaderFilters hf = (HeaderFilters) annotation;
                for (String headerKey : hf.value()) {
                    heads.put(headerKey, true);
                }
            }
        }
        return heads;
    }

    public static class logDto {
        final boolean isLogging;

        public logDto(boolean isLogging) {
            this.isLogging = isLogging;
        }

        private final static String HttpHeaderToStr(HttpHeaders heads) {
            StringBuilder sbHeader = new StringBuilder();
            for (Entry<String, List<String>> header : heads.entrySet()) {
                for (String headval : header.getValue()) {
                    sbHeader.append(header.getKey()).append(" ").append(headval).append("\n");
                }
            }
            return sbHeader.toString();
        }

        @Getter
        @Setter
        private String url = null;

        @Getter
        @Setter
        private String method = null;

        @Getter
        private String reqHeader = null;
        private StringBuilder sbReqBody = new StringBuilder();

        @Getter
        private String resBody = null;
        private StringBuilder sbResBody = new StringBuilder();

        @Getter
        private String reqBody = null;

        @Getter
        private String resHeader = null;

        @Getter
        @Setter
        private Integer status = null;

        @Getter
        @Setter
        private String transId;

        @Getter
        @Setter
        private String classType;

        @Getter
        @Setter
        private String createId;

        @Getter
        @Setter
        private String origin;

        @Getter
        @Setter
        private String destination;

        @Getter
        @Setter
        private String apiType;

        @Getter
        @Setter
        private String statisticsCode = null;

        @Getter
        @Setter
        private String rspTmMs = null;

        private boolean isSaved = false;

        public void setReqHeader(HttpHeaders heads) {
            this.reqHeader = HttpHeaderToStr(heads);
        }

        public void setResHeader(HttpHeaders heads) {
            this.resHeader = HttpHeaderToStr(heads);
        }

        public void addReqBody(String buffer) {
            sbReqBody.append(buffer);
        }

        public void addResBody(String buffer) {
            sbResBody.append(buffer);
        }

        public void saveLog() {

            if (isSaved) {
                return;
            }
            isSaved = true;
            reqBody = sbReqBody.toString();
            resBody = sbResBody.toString();
//            if (this.isLogging) {
//                SqlSessionTemplate sql = AppCntxtPrvidr.getSqlSession();
//
//                if (sql != null) {
//                    sql.insert("HttpMapper.logHttp", this);
//                }
//            }
        }
    }

    public final <T extends BaseResponseVo> Mono<ResponseEntity<T>> async(Class<T> responseType) {
        boolean logging = this.isLogging;

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)).build(); // to unlimited memory
        // size .build();
        HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connTimeout)
                .responseTimeout(Duration.ofMillis(respTimeout))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)))
                .wiretap("WEBLOG", LogLevel.DEBUG, AdvancedByteBufFormat.HEX_DUMP, Charset.forName("UTF-8"));

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies)
                .filter(new ExchangeFilterFunction() {
                    logDto logVo = new logDto(logging);

                    @Override
                    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
                        logVo.setUrl(request.url().toString());
                        logVo.setMethod(request.method().name());
                        logVo.setReqHeader(request.headers());
                        logVo.setTransId(transId);
                        logVo.setClassType(classType);
                        logVo.setOrigin(origin);
                        logVo.setDestination(destination);
                        logVo.setApiType(apiType);
                        logVo.setCreateId(createId);

                        BodyInserter<?, ? super ClientHttpRequest> originalBodyInserter = request.body();

                        ClientRequest loggingClientRequest = ClientRequest.from(request)
                                .body((outputMessage, context) -> {
                                    ClientHttpRequestDecorator loggingOutputMessage = new ClientHttpRequestDecorator(
                                            outputMessage) {
                                        private final AtomicBoolean alreadyLogged = new AtomicBoolean(false);

                                        @Override
                                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                                            boolean needToLog = alreadyLogged.compareAndSet(false, true);
                                            if (needToLog) {
                                                body = DataBufferUtils.join(body).doOnNext(content -> {
                                                    logVo.addReqBody(content.toString(StandardCharsets.UTF_8));
                                                });
                                            }
                                            return super.writeWith(body);
                                        }
                                    };
                                    return originalBodyInserter.insert(loggingOutputMessage, context);
                                }).build();

                        return next.exchange(loggingClientRequest).elapsed()
                                .doOnNext(tuple -> logVo.setRspTmMs(String.valueOf(tuple.getT1()))).map(tuple -> {
                                    logVo.setStatus(tuple.getT2().statusCode().value());
                                    logVo.setResHeader(tuple.getT2().headers().asHttpHeaders());

                                    log.debug("################################################");
                                    log.debug("HTTP API Info: {}", new Gson().toJson(logVo));
                                    log.debug("################################################");
                                    return tuple.getT2().mutate().body(f -> f.map(dataBuffer -> {
                                        logVo.addResBody(dataBuffer.toString(StandardCharsets.UTF_8));
                                        return dataBuffer;
                                    }).doFinally(df -> {
                                        if (df == SignalType.ON_COMPLETE || df == SignalType.ON_ERROR) {
                                            logVo.saveLog();
                                        }
                                    })).build();
                                });
                    }
                }).clientConnector(new ReactorClientHttpConnector(httpClient)).uriBuilderFactory(factory)
                .baseUrl(this.baseUrl).build();

        UriSpec<RequestBodySpec> uriSpec = client.method(method);
        RequestBodySpec bodySpec = uriSpec.uri(this.path);
        RequestHeadersSpec<?> headersSpec = bodySpec.body(inserter);

        for (Entry<String, String> header : headers.entrySet()) {
            headersSpec.header(header.getKey(), header.getValue());
        }

        return headersSpec.exchangeToMono(response -> {
            Map<String, Boolean> headCheck = GetHeaderListFromAnnotation(responseType);
            StringBuilder sbResLog = new StringBuilder();
            HttpHeaders heads = new HttpHeaders();
            sbResLog.append("\n--Check Response Skip Header--\n");
            boolean isOctetStream = false;
            boolean isTextPlain = false;

            for (Entry<String, List<String>> head : response.headers().asHttpHeaders().entrySet()) {

                boolean first = false;
                boolean skipCond = (!headCheck.isEmpty() && !headCheck.containsKey(head.getKey()))
                        || (headCheck.isEmpty() && head.getKey().indexOf("x-") != 0);

                if (response.statusCode() == HttpStatus.FOUND && "Location".equals(head.getKey())) {
                    skipCond = false;
                }

                if (skipCond) {
                    sbResLog.append("[skip]");
                }

                sbResLog.append(head.getKey()).append(" : ");
                sbResLog.append("[");
                for (String str : head.getValue()) {
                    if ("Content-Type".equals(head.getKey())) {
                        if ("application/octet-stream".equals(str.toLowerCase())) {
                            isOctetStream = true;
                        } else if (str != null && str.toLowerCase().indexOf("text/plain") > -1) {
                            isTextPlain = true;
                        }
                    }

                    if (!skipCond) {
                        heads.add(head.getKey(), str);
                    }

                    if (first) {
                        sbResLog.append(",");
                    } else {
                        first = true;
                    }
                    sbResLog.append(str);
                }
                sbResLog.append("]\n");
            }
            sbResLog.append("--Check Response Skip Header End--\n");

            Mono<ResponseEntity<T>> mono = null;

            if (isOctetStream) {
                mono = response.bodyToMono(ByteArrayResource.class).map(arrByte -> {
                    T res = null;
                    try {
                        res = responseType.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                    }
                    if (res != null) {
                        res.setBody(new String(arrByte.getByteArray()));
                    }
                    return new ResponseEntity<T>(res, heads, response.statusCode());
                });
            } else if (response.statusCode().value() < 300 && !isTextPlain) { // Json의 경우 Object변환
                mono = response.bodyToMono(responseType).map(res -> {
                    return new ResponseEntity<T>(res, heads, response.statusCode());
                });
            } else if (StringUtil.isInObj(response.statusCode(), HttpStatus.FOUND)) { // 302의 경우 본문이 존재하지 않음.
                T res = null;
                try {
                    res = responseType.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                }
                mono = Mono.just(new ResponseEntity<T>(res, heads, response.statusCode()));
            } else {
                mono = response.bodyToMono(String.class).map(body -> {
                    T res = null;
                    try {
                        res = responseType.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                    }
                    if (res != null) {
                        res.setBody(String.valueOf(body));
                    }
                    return new ResponseEntity<T>(res, heads, response.statusCode());
                });
            }
            log.debug(sbResLog.toString());
            return mono;
        });
    }

    public final <T extends BaseResponseVo> ResponseEntity<T> sync(Class<T> responseType) {
        return async(responseType).block();
    }
}