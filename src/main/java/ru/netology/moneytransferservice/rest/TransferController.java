package ru.netology.moneytransferservice.rest;

import org.openapitools.api.TransferApi;
import org.openapitools.model.InlineObject;
import org.openapitools.model.InlineResponse200;
import org.springframework.http.ResponseEntity;

public class TransferController implements TransferApi {
  @Override
  public ResponseEntity<InlineResponse200> transferPost(InlineObject inlineObject) {
    return TransferApi.super.transferPost(inlineObject);
  }
}
