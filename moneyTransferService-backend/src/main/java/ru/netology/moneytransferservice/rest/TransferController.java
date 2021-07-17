package ru.netology.moneytransferservice.rest;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.TransferApi;
import org.openapitools.model.OperationDto;
import org.openapitools.model.SuccessOperationDto;
import org.openapitools.model.TransferReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.netology.moneytransferservice.service.TransferService;

@Controller
@RequiredArgsConstructor
public class TransferController implements TransferApi {
  private final TransferService transferService;

  @Override
  public ResponseEntity<SuccessOperationDto> transferPost(TransferReqDto transferReqDto) {
    SuccessOperationDto output = transferService.transferPost(transferReqDto);
    return ResponseEntity.ok(output);
  }
}
