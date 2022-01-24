package com.lbg.lloydsAtm.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbg.lloydsAtm.model.request.AtmRequest;
import com.lbg.lloydsAtm.model.response.AtmResponse;
import com.lbg.lloydsAtm.model.response.Brand;
import com.lbg.lloydsAtm.model.response.Location;
import com.lbg.lloydsAtm.model.response.BrandName;
import com.lbg.lloydsAtm.model.response.AtmResponseData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AtmOperationTest {

  private static final String URL = "https://api.lloydsbank.com/open-banking/v2.2/atms";
  private static final String IDENTIFICATION = "123";

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private AtmOperation atmOperation;

  @Test
  void getAtms_WithIdentificationNumber() throws JsonProcessingException {

    AtmRequest atmRequest = AtmRequest.builder()
        .url(URL)
        .identification(IDENTIFICATION).build();

    Map<String, String> responseEntityMap = new HashMap<>();
    responseEntityMap.put("meta", "meta");
    responseEntityMap.put("data", "data");

    AtmResponseData atmResponseData = getResponseData(IDENTIFICATION);

    ResponseEntity<String> responseEntity = new ResponseEntity<>(String.valueOf(responseEntityMap), HttpStatus.OK);
    when(restTemplate.getForEntity(atmRequest.getUrl(), String.class)).thenReturn(responseEntity);
    when(objectMapper.readValue(responseEntity.getBody(), AtmResponseData.class)).thenReturn(
        atmResponseData);

    List<AtmResponse> atmResponseList1 = atmOperation.getAtms(atmRequest);
    assertThat(atmResponseList1.get(0).getIdentification()).isEqualTo(IDENTIFICATION);
  }

  @Test
  void getAtms_WithUnkownIdentificationNumber() throws JsonProcessingException {

    AtmRequest atmRequest = AtmRequest.builder()
        .url(URL)
        .identification("12345").build();

    Map<String, String> responseEntityMap = new HashMap<>();
    responseEntityMap.put("meta", "meta");
    responseEntityMap.put("data", "data");

    AtmResponseData atmResponseData = getResponseData(IDENTIFICATION);

    ResponseEntity<String> responseEntity = new ResponseEntity<>(String.valueOf(responseEntityMap), HttpStatus.OK);
    when(restTemplate.getForEntity(atmRequest.getUrl(), String.class)).thenReturn(responseEntity);
    when(objectMapper.readValue(responseEntity.getBody(), AtmResponseData.class)).thenReturn(
        atmResponseData);

    List<AtmResponse> atmResponseList1 = atmOperation.getAtms(atmRequest);
    assertThat(atmResponseList1.size()).isZero();
  }

  private AtmResponseData getResponseData(String identification) {
    List<String> supportedCountries = new ArrayList<>();
    supportedCountries.add("GB");
    AtmResponse atmResponse = AtmResponse.builder().identification(identification)
        .location(Location.builder().build()).supportedCurrencies(supportedCountries).build();
    List<AtmResponse> atmResponseList = new ArrayList<>();
    atmResponseList.add(atmResponse);
    BrandName brandName = BrandName.builder().atm(atmResponseList).build();
    List<BrandName> brandNameList = new ArrayList<>();
    brandNameList.add(brandName);
    Brand brand = Brand.builder().brand(brandNameList).build();
    List<Brand> brandList = new ArrayList<>();
    brandList.add(brand);
    return AtmResponseData.builder()
        .data(brandList).build();
  }
}
