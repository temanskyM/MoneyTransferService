package ru.netology.moneytransferservice.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.domain.Amount;
import ru.netology.moneytransferservice.domain.Card;

@Repository
public class CardRepository {
  private final static ConcurrentHashMap<String, Card> cardMap = new ConcurrentHashMap<>();

  static {
    final Card card = Card.builder()
        .id("1111111111111111")
        .amount(new Amount(1000, "RUR"))
        .CVV("111")
        .till("11/21")
        .build();
    cardMap.put(card.getId(), card);
    final Card card2 = Card.builder()
        .id("2222222222222222")
        .amount(new Amount(0, "RUR"))
        .CVV("111")
        .till("11/21")
        .build();
    cardMap.put(card2.getId(), card2);
  }

  public Optional<Card> getById(String id) {
    return Optional.ofNullable(cardMap.get(id));
  }

  public void update(Card card) {
    cardMap.put(card.getId(), card);
  }
}
