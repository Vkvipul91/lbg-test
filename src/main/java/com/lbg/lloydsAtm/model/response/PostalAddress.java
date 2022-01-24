package com.lbg.lloydsAtm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


@Builder
@Jacksonized
@Value
public class PostalAddress {

  @JsonProperty("AddressLine")
  List<String> addressLine;
  @JsonProperty("StreetName")
  String streetName;
  @JsonProperty("TownName")
  String townName;
  @JsonProperty("CountrySubDivision")
  List<String> countrySubDivision;
  @JsonProperty("Country")
  String country;
  @JsonProperty("PostCode")
  String postCode;
}
