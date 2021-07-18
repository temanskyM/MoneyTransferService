package ru.netology.moneytransferservice.domain;

import lombok.Builder;
import lombok.Data;
import org.openapitools.model.AmountDto;

@Data
@Builder
public class Card {
  private final String id;
  private final String till;
  private final String CVV;
  private Amount amount;

  public void increment(AmountDto amountDto) {
    synchronized (this.id) {
      this.amount.increment(amountDto);
    }
  }

  public void decrement(AmountDto amountDto) {
    synchronized (this.id) {
      this.amount.decrement(amountDto);
    }
  }
}
