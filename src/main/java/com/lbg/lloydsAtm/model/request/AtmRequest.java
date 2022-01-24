package com.lbg.lloydsAtm.model.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class AtmRequest {

  String url;
  String identification;

}
