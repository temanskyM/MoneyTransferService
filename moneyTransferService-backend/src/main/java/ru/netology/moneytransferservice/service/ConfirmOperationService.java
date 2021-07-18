package ru.netology.moneytransferservice.service;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.ConfirmOperationReqDto;
import org.openapitools.model.SuccessOperationDto;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.domain.Operation;
import ru.netology.moneytransferservice.domain.OperationContent;
import ru.netology.moneytransferservice.domain.OperationStatus;
import ru.netology.moneytransferservice.repository.OperationRepository;

@Service
@RequiredArgsConstructor
public class ConfirmOperationService {
  private final OperationRepository operationRepository;

  public Operation addOperation(OperationContent content) {
    return operationRepository.add(content);
  }

  public SuccessOperationDto confirmOperationPost(ConfirmOperationReqDto confirmOperationReqDto) {
    final Operation operation = operationRepository.getById(confirmOperationReqDto.getOperationId())
        .orElseThrow(() -> new IllegalArgumentException("Incorrect opertionId"));

    if (!confirmOperationReqDto.getCode().equals(operation.getCode())) {
      failedCode(operation);
    }

    SuccessOperationDto output = new SuccessOperationDto();
    output.setOperationId(operation.getId());
    return output;
  }

  public void failedCode(Operation operation) {
    operation.setStatus(OperationStatus.FAILED);
    operationRepository.update(operation);
    throw new IllegalArgumentException("Code not equals");
  }
}
