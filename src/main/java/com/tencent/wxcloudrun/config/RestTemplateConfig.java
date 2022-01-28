package com.tencent.wxcloudrun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class RestTemplateConfig {

//    /**RestTemplateConfig
//     * http连接管理器
//     * @return
//     */
//    @Bean
//    public HttpClientConnectionManager poolingHttpClientConnectionManager() {
//        /*// 注册http和https请求
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", SSLConnectionSocketFactory.getSocketFactory())
//                .build();
//        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);*/
//
//        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
//        // 最大连接数
//        poolingHttpClientConnectionManager.setMaxTotal(500);
//        // 同路由并发数（每个主机的并发）
//        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
//        return poolingHttpClientConnectionManager;
//    }
//
//    /**
//     * HttpClient
//     * @param poolingHttpClientConnectionManager
//     * @return
//     */
//    @Bean
//    public CloseableHttpClient httpClient(HttpClientConnectionManager poolingHttpClientConnectionManager) {
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        // 设置http连接管理器
//        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
//
//        /*// 设置重试次数
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));*/
//
//        // 设置默认请求头
//        /*List<Header> headers = new ArrayList<>();
//        headers.add(new BasicHeader("Connection", "Keep-Alive"));
//        httpClientBuilder.setDefaultHeaders(headers);*/
//
//        return httpClientBuilder.build();
//    }
//
//    /**
//     * 请求连接池配置
//     * @param httpClient
//     * @return
//     */
//    @Bean
//    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//        // httpClient创建器
//        clientHttpRequestFactory.setHttpClient(httpClient);
//        // 连接超时时间/毫秒（连接上服务器(握手成功)的时间，超出抛出connect timeout）
//        clientHttpRequestFactory.setConnectTimeout(5 * 1000);
//        // 数据读取超时时间(socketTimeout)/毫秒（务器返回数据(response)的时间，超过抛出read timeout）
//        clientHttpRequestFactory.setReadTimeout(10 * 1000);
//        // 连接池获取请求连接的超时时间，不宜过长，必须设置/毫秒（超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool）
//        clientHttpRequestFactory.setConnectionRequestTimeout(10 * 1000);
//        return clientHttpRequestFactory;
//    }

    /**
     * rest模板
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        // boot中可使用RestTemplateBuilder.build创建
        RestTemplate restTemplate = new RestTemplate();
//        // 配置请求工厂
//        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

//    @Autowired
//    private RestTemplate restTemplate;

    @PostConstruct
    public void test() throws IOException {
//        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.pdfdrive.com/search?q=江泽明", String.class);
//        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.pdfdrive.com/他改变了中国江泽民传-d191388667.html", String.class);
//        String body = forEntity.getBody();

//        Document parse = Jsoup.parse(body);
//        Elements elementsByClass = parse.getElementsByClass("files-new");
//        String s = elementsByClass.toString();

//        Path path = Paths.get("classpath:test.html");
//        FileWriter fileWriter = new FileWriter("/Users/martin/Downloads/JavaProject/e_booke/src/main/resources/test3.html", true);
//        fileWriter.write(body);
//        fileWriter.close();
//        Files.write(path, body.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
//        System.out.println();
    }
}