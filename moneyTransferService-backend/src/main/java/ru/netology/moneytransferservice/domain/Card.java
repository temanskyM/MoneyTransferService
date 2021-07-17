package ru.netology.moneytransferservice.domain;

import lombok.Builder;
import lombok.Data;
import org.openapitools.model.AmountDto;

@Data
@Builder
public class Card {
  private String id;
  private String till;
  private String CVV;
  private Amount amount;

  public void increment(AmountDto amountDto) {
    this.amount.increment(amountDto);
  }

  public void decrement(AmountDto amountDto) {
    this.amount.decrement(amountDto);
  }
}
