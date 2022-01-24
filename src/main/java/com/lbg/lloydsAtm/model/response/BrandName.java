package com.lbg.lloydsAtm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BrandName {
  @JsonProperty("BrandName")
  String brandName;
  @JsonProperty("ATM")
  List<AtmResponse> atm;
}
