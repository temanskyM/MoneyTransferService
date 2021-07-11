package ru.netology.moneytransferservice.rest;

import org.openapitools.api.ConfirmOperationApi;
import org.openapitools.model.InlineObject1;
import org.openapitools.model.InlineResponse200;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ConfirmOperationController implements ConfirmOperationApi {
  @Override
  public ResponseEntity<InlineResponse200> confirmOperationPost(InlineObject1 inlineObject1) {
    return ConfirmOperationApi.super.confirmOperationPost(inlineObject1);
  }
}
