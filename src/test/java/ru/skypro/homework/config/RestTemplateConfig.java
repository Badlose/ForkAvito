//package ru.skypro.homework.config;
//
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.context.annotation.Bean;
//
//import java.util.function.Supplier;
//
//@Configuration
//public class RestTemplateConfig {
//
//    @Bean
//    public Supplier<ClientHttpRequestFactory> clientHttpRequestFactorySupplier() {
//        return () -> {
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            return new HttpComponentsClientHttpRequestFactory(httpClient);
//        };
//    }
//
//    @Bean
//    public RestTemplateBuilder restTemplateBuilder(Supplier<ClientHttpRequestFactory> clientHttpRequestFactorySupplier) {
//        return new RestTemplateBuilder().requestFactory(clientHttpRequestFactorySupplier);
//    }
//
//    @Bean
//    @Primary
//    public TestRestTemplate restTemplate(RestTemplateBuilder builder) {
//        return new TestRestTemplate(builder);
//    }
//
//}
