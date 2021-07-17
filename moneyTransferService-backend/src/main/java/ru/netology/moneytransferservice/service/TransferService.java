package ru.netology.moneytransferservice.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AmountDto;
import org.openapitools.model.SuccessOperationDto;
import org.openapitools.model.TransferReqDto;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.domain.Card;
import ru.netology.moneytransferservice.repository.CardRepository;

@Service
@RequiredArgsConstructor
public class TransferService {
  private final CardRepository cardRepository;

  public SuccessOperationDto transferPost(TransferReqDto transferReqDto) {
    final String cardFromId = transferReqDto.getCardFromNumber();
    final Card cardFrom = cardRepository.getById(cardFromId)
        .orElseThrow(() -> new IllegalArgumentException("Incorrect cardFromId"));

    final String cardFromCVV = transferReqDto.getCardFromCVV();
    if (!cardFromCVV.equals(cardFrom.getCVV())) {
      throw new IllegalArgumentException("Incorrect cardToId");
    }

    final String cardFromValidTill = transferReqDto.getCardFromValidTill();
    if (!cardFromValidTill.equals(cardFrom.getTill())) {
      throw new IllegalArgumentException("Incorrect ValidTill");
    }

    final String cardToId = transferReqDto.getCardToNumber();
    final Card cardTo = cardRepository.getById(cardToId)
        .orElseThrow(() -> new IllegalArgumentException("Incorrect cardToId"));

    final AmountDto amountFromDto = transferReqDto.getAmount();
    cardFrom.decrement(amountFromDto);
    cardTo.increment(amountFromDto);

    SuccessOperationDto output = new SuccessOperationDto();
    output.setOperationId(UUID.randomUUID().toString());
    return output;
  }
}
