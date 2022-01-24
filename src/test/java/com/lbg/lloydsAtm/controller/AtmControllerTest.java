package com.lbg.lloydsAtm.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbg.lloydsAtm.model.request.AtmRequest;
import com.lbg.lloydsAtm.model.response.AtmResponse;
import com.lbg.lloydsAtm.model.response.Location;
import com.lbg.lloydsAtm.service.AtmService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtmControllerTest {

  private static final String URL = "https://api.lloydsbank.com/open-banking/v2.2/atms";
  private static final String IDENTIFICATION = "123";

  @Mock
  private AtmService mockAtmService;

  @InjectMocks
  private AtmController atmController;

  @Test
  void getAtm() throws JsonProcessingException {

    List<String> supportedCountries = new ArrayList<>();
    supportedCountries.add("GB");

    AtmResponse atmResponse = AtmResponse.builder()
        .supportedCurrencies(supportedCountries)
        .location(Location.builder().build())
        .identification(IDENTIFICATION)
        .build();

    List<AtmResponse> atmResponseList = new ArrayList<>();
    atmResponseList.add(atmResponse);

    AtmRequest atmRequest = AtmRequest.builder().url(URL).identification(IDENTIFICATION).build();

    when(mockAtmService.getAtmDetails(atmRequest)).thenReturn(atmResponseList);
    assertThat(atmController.getAtm(URL, IDENTIFICATION).size()).isEqualTo(1);
  }

  @Test
  void getAtm_WithoutUrl() throws JsonProcessingException {
    assertThat(atmController.getAtm("", IDENTIFICATION).size()).isZero();
  }

  @Test
  void getAtm_ThrowsException() throws JsonProcessingException {
    AtmRequest atmRequest = AtmRequest.builder().url(URL).identification(IDENTIFICATION).build();
    when(mockAtmService.getAtmDetails(atmRequest)).thenThrow(new NullPointerException());
    assertThrows(NullPointerException.class, ()-> atmController.getAtm(URL, IDENTIFICATION));
  }
}
