package ru.netology.moneytransferservice.domain;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openapitools.model.AmountDto;

@Data
@AllArgsConstructor
public class Amount {
  private AtomicInteger value;
  private String currency;

  public void increment(AmountDto amountDto) {
    if (!currency.equals(amountDto.getCurrency())) {
      throw new IllegalArgumentException("The currency type does not match");
    }
    value.addAndGet(amountDto.getValue());
  }

  public void decrement(AmountDto amountDto) {
    if (!currency.equals(amountDto.getCurrency())) {
      throw new IllegalArgumentException("The currency type does not match");
    }
    if (value.intValue() < amountDto.getValue()) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    int decrementedValue = value.intValue() - amountDto.getValue();
    value.getAndSet(decrementedValue);
  }
}
