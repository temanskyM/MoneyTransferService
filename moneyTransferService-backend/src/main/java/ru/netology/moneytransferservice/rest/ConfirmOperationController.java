package ru.netology.moneytransferservice.rest;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.ConfirmOperationApi;
import org.openapitools.model.ConfirmOperationReqDto;
import org.openapitools.model.OperationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.netology.moneytransferservice.service.ConfirmOperationService;

@Controller
@RequiredArgsConstructor
public class ConfirmOperationController implements ConfirmOperationApi {
  private final ConfirmOperationService confirmOperationService;

  @Override
  public ResponseEntity<OperationDto> confirmOperationPost(
      ConfirmOperationReqDto confirmOperationReqDto) {
    return ConfirmOperationApi.super.confirmOperationPost(confirmOperationReqDto);
  }
}
