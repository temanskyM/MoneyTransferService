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
        CardProvider.generate().forEach(item -> cardMap.put(item.getId(), item));
    }

    public Optional<Card> getById(String id) {
        return Optional.ofNullable(cardMap.get(id));
    }

    public void upsert(Card card) {
        cardMap.put(card.getId(), card);
    }
}
