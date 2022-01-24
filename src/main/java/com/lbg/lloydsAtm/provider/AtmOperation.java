package com.lbg.lloydsAtm.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbg.lloydsAtm.model.request.AtmRequest;
import com.lbg.lloydsAtm.model.response.AtmResponse;
import com.lbg.lloydsAtm.model.response.AtmResponseData;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AtmOperation {

  @Resource
  private RestTemplate restTemplate;

  @Resource
  private ObjectMapper objectMapper;

  public List<AtmResponse> getAtms(AtmRequest atmRequest) throws JsonProcessingException {

    log.info("In Atm operation with request {}", atmRequest);
    try {
      ResponseEntity<String> response = restTemplate.getForEntity(atmRequest.getUrl(),
          String.class);
      AtmResponseData atmResponseData = objectMapper.readValue(response.getBody(), AtmResponseData.class);
      List<AtmResponse> atmResponseList = atmResponseData.getData().get(0).getBrand().get(0).getAtm();
      return atmResponseList.stream()
          .filter(a -> a.getIdentification().equals(atmRequest.getIdentification())).collect(
              Collectors.toList());
    } catch (NullPointerException | JsonProcessingException ex) {
      log.info("Exception thrown for identification number {} with message {}",
          atmRequest.getIdentification(), ex.getMessage());
      throw ex;
    }
  }

}
