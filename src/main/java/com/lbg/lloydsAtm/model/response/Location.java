package com.lbg.lloydsAtm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class Location {
  @JsonProperty("PostalAddress")
  PostalAddress postalAddress;

}
