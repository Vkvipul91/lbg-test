package com.lbg.lloydsAtm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbg.lloydsAtm.model.request.AtmRequest;
import com.lbg.lloydsAtm.model.response.AtmResponse;
import com.lbg.lloydsAtm.provider.AtmOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AtmService {

  private final AtmOperation atmOperation;

  public AtmService(AtmOperation atmOperation) {
    this.atmOperation = atmOperation;
  }

  public List<AtmResponse> getAtmDetails(AtmRequest atmRequest)
      throws JsonProcessingException {
    log.info("In Atm service with request {}", atmRequest);
    try {
      return atmOperation.getAtms(atmRequest);
    } catch (NullPointerException | JsonProcessingException ex){
      log.info("Exception thrown for identification number {} with message {}",
          atmRequest.getIdentification(), ex.getMessage());
      throw ex;
    }
  }

}
