package com.lbg.lloydsAtm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class AtmResponse {

  @JsonProperty("Identification")
  String identification;
  @JsonProperty("SupportedCurrencies")
  List<String> supportedCurrencies;
  @JsonProperty("Location")
  Location location;

}
