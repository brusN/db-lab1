package ru.nsu.brusn.lab1.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.mapper.CrackHashManagerRequestToXmlMapper;
import ru.nsu.brusn.lab1.mapper.XmlToCrackHashWorkerResponseMapper;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskManager;
import ru.nsu.brusn.lab1.model.worker.WorkerManager;
import ru.nsu.ccfit.schema.crack_hash_request.ObjectFactory;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebMvc {

    private HttpComponentsClientHttpRequestFactory getConfiguredHttpRequestFactory() {
        var clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(2000);
        return clientHttpRequestFactory;
    }

    private List<HttpMessageConverter<?>> getConfiguredMessageConverters() {
        var messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        return messageConverters;
    }

    private RestTemplate getRestTemplate() {
        var restTemplate = new RestTemplate(getConfiguredHttpRequestFactory());
        restTemplate.setMessageConverters(getConfiguredMessageConverters());
        return restTemplate;
    }

    @Bean
    public CrackHashTaskManager getTaskManager() {
        var restTemplate = getRestTemplate();
        var workerManager = new WorkerManager(restTemplate);
        workerManager.addWorker("localhost", "8080");
        return new CrackHashTaskManager(new ObjectFactory(), new CrackHashManagerRequestToXmlMapper(), new XmlToCrackHashWorkerResponseMapper(), workerManager);
    }
}
