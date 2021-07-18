package ru.netology.moneytransferservice.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AmountDto;
import org.openapitools.model.SuccessOperationDto;
import org.openapitools.model.TransferReqDto;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.domain.Amount;
import ru.netology.moneytransferservice.domain.Card;
import ru.netology.moneytransferservice.domain.Operation;
import ru.netology.moneytransferservice.domain.OperationContent;
import ru.netology.moneytransferservice.repository.CardRepository;

@Service
@RequiredArgsConstructor
public class TransferService {
  private final CardRepository cardRepository;
  private final ConfirmOperationService confirmOperationService;

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

    final OperationContent operationContent = OperationContent.builder()
        .amount(new Amount(amountFromDto.getValue(), amountFromDto.getCurrency()))
        .cardFromId(cardFromId)
        .cardToId(cardToId)
        .build();
    final Operation operation = confirmOperationService.addOperation(operationContent);

    SuccessOperationDto output = new SuccessOperationDto();
    output.setOperationId(operation.getId());
    return output;
  }
}
