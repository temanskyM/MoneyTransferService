package ru.netology.moneytransferservice.repository;

import lombok.experimental.UtilityClass;
import ru.netology.moneytransferservice.domain.Amount;
import ru.netology.moneytransferservice.domain.Card;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CardProvider {
    public List<Card> generate() {
        List<Card> list = new ArrayList<>();

        final Card card = Card.builder()
                .id("1111111111111111")
                .amount(new Amount(1000, "RUR"))
                .CVV("111")
                .till("11/21")
                .build();
        list.add(card);
        final Card card2 = Card.builder()
                .id("2222222222222222")
                .amount(new Amount(0, "RUR"))
                .CVV("111")
                .till("11/21")
                .build();
        list.add(card2);

        return list;
    }
}
