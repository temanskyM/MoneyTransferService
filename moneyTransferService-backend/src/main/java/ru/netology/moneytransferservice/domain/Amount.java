package ru.netology.moneytransferservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.openapitools.model.AmountDto;

@Data
@AllArgsConstructor
public class Amount {
  private int value;
  private String currency;

  public void increment(AmountDto amountDto) {
    if (!currency.equals(amountDto.getCurrency())) {
      throw new IllegalArgumentException("The currency type does not match");
    }
    value += amountDto.getValue();
  }

  public void decrement(AmountDto amountDto) {
    if (!currency.equals(amountDto.getCurrency())) {
      throw new IllegalArgumentException("The currency type does not match");
    }
    if (value < amountDto.getValue()) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    value -= amountDto.getValue();
  }
}
