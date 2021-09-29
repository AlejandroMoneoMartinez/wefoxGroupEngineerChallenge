package com.wefox.demo.service;

import com.wefox.domain.dto.Error;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Service
public class LogService {

    private static final Log LOG = LogFactory.getLog(LogService.class);

    @Value("${global.logEndpoint}")
    private String logEndpoint;

    private RestTemplate restTemplate;


    public LogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendLog(Error error) {
        try {
            restTemplate.postForObject(logEndpoint, new HttpEntity<>(error), Error.class);
        } catch (HttpStatusCodeException e) {
            LOG.error(MessageFormat.format("{0}, {1}", e.getStatusText(), error.toString()));
        } catch (RestClientException e) {
            LOG.error(MessageFormat.format("Runtime exception, {0}", error.toString()));
        }
    }
}