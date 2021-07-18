package ru.netology.moneytransferservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationContent {
  private final String cardFromId;
  private final String cardToId;
  private final Amount amount;
}
