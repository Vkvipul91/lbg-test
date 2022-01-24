package com.lbg.lloydsAtm.model.response;
import java.util.Date;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class Meta {

  Date LastUpdated;
  int TotalResults;
  String Agreement;
  String License;
  String TermsOfUse;

}
