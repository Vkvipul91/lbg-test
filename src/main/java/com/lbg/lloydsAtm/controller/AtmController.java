package com.lbg.lloydsAtm.controller;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbg.lloydsAtm.model.request.AtmRequest;
import com.lbg.lloydsAtm.model.response.AtmResponse;
import com.lbg.lloydsAtm.service.AtmService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AtmController {

  private final AtmService atmService;

  public AtmController(AtmService atmService) {
    this.atmService = atmService;
  }

  @GetMapping(path = "/atm")
  public List<AtmResponse> getAtm(@RequestParam String url, @RequestParam String identification)
      throws JsonProcessingException {

    log.info("Request received with url {} & identification {}", url, identification);

    if (isNotBlank(url) && isNotBlank(identification)) {
      AtmRequest atmRequest = AtmRequest.builder()
          .url(url)
          .identification(identification)
          .build();
      List<AtmResponse> atmResponseList =  atmService.getAtmDetails(atmRequest);
      log.info("Sending response for identification {}: {}", identification, atmResponseList);
      return atmResponseList;
    }
    return new ArrayList<>();
  }

}
