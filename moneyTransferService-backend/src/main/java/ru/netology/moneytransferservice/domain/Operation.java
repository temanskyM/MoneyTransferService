package ru.netology.moneytransferservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Operation {
  private String id;
  private OperationStatus status;
  private String code;
  private OperationContent content;
}
