package com.lbg.lloydsAtm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbg.lloydsAtm.model.request.AtmRequest;
import com.lbg.lloydsAtm.model.response.AtmResponse;
import com.lbg.lloydsAtm.model.response.Location;
import com.lbg.lloydsAtm.provider.AtmOperation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtmServiceTest {

  private static final String URL = "https://api.lloydsbank.com/open-banking/v2.2/atms";
  private static final String IDENTIFICATION = "123";

  @Mock
  private AtmOperation mockAtmOperation;

  @InjectMocks
  private AtmService atmService;

  @Test
  void getAtmDetails() throws JsonProcessingException {

    AtmRequest atmRequest = AtmRequest.builder().url(URL).identification(IDENTIFICATION).build();

    List<String> supportedCountries = new ArrayList<>();
    supportedCountries.add("GB");
    AtmResponse atmResponse = AtmResponse.builder().identification(IDENTIFICATION)
        .location(Location.builder().build()).supportedCurrencies(supportedCountries).build();

    List<AtmResponse> atmResponseList = new ArrayList<>();
    atmResponseList.add(atmResponse);

    when(mockAtmOperation.getAtms(atmRequest)).thenReturn(atmResponseList);
    assertThat(atmService.getAtmDetails(atmRequest).size()).isEqualTo(1);
  }

  @Test
  void getAtmDetails_ThrowsException() throws JsonProcessingException {

    AtmRequest atmRequest = AtmRequest.builder().url(URL).identification(IDENTIFICATION).build();
    when(mockAtmOperation.getAtms(atmRequest)).thenThrow(new NullPointerException("Exception"));
    assertThrows(NullPointerException.class, ()-> atmService.getAtmDetails(atmRequest));
  }
}
