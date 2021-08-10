package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.Test;
import org.openapitools.model.AmountDto;
import org.openapitools.model.ConfirmOperationReqDto;
import org.openapitools.model.SuccessOperationDto;
import org.openapitools.model.TransferReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.netology.moneytransferservice.domain.Amount;
import ru.netology.moneytransferservice.domain.Card;
import ru.netology.moneytransferservice.domain.Operation;
import ru.netology.moneytransferservice.domain.OperationContent;
import ru.netology.moneytransferservice.repository.CardRepository;
import ru.netology.moneytransferservice.repository.OperationRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransferServiceTest {
    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private ConfirmOperationService confirmOperationService;

    @Autowired
    private TransferService transferService;

    @Test
    void transferPost_thenThrowExpByCardFromId() {
        TransferReqDto input = new TransferReqDto();
        input.setCardFromNumber("test");

        when(cardRepository.getById(input.getCardFromNumber())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transferService.transferPost(input));
    }

    @Test
    void transferPost_thenThrowExpByCardFromCVV() {
        TransferReqDto input = new TransferReqDto();
        input.setCardFromNumber("test");
        input.setCardFromCVV("111");

        Card card = Card.builder()
                .id(input.getCardFromNumber())
                .CVV("222")
                .build();

        when(cardRepository.getById(input.getCardFromNumber())).thenReturn(Optional.of(card));

        assertThrows(IllegalArgumentException.class, () -> transferService.transferPost(input));
    }

    @Test
    void transferPost_thenThrowExpByCardFromValidTill() {
        TransferReqDto input = new TransferReqDto();
        input.setCardFromNumber("test");
        input.setCardFromCVV("111");
        input.setCardFromValidTill("111");

        Card card = Card.builder()
                .id(input.getCardFromNumber())
                .CVV(input.getCardFromCVV())
                .till("222")
                .build();

        when(cardRepository.getById(input.getCardFromNumber())).thenReturn(Optional.of(card));

        assertThrows(IllegalArgumentException.class, () -> transferService.transferPost(input));
    }

    @Test
    void transferPost_thenThrowExpByCardToNumber() {
        TransferReqDto input = new TransferReqDto();
        input.setCardFromNumber("test");
        input.setCardFromCVV("111");
        input.setCardFromValidTill("111");
        input.setCardToNumber("test2");

        Card cardFrom = Card.builder()
                .id(input.getCardFromNumber())
                .CVV(input.getCardFromCVV())
                .till(input.getCardFromValidTill())
                .build();

        Card cardTo = Card.builder()
                .id(input.getCardToNumber())
                .CVV(input.getCardFromCVV())
                .till(input.getCardFromValidTill())
                .build();

        when(cardRepository.getById(cardFrom.getId())).thenReturn(Optional.of(cardTo));
        when(cardRepository.getById(cardTo.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transferService.transferPost(input));
    }

    @Test
    void transferPost_thenReturnGood() {
        AmountDto amountDto = new AmountDto();
        amountDto.setCurrency("RUR");
        amountDto.setValue(100);

        TransferReqDto input = new TransferReqDto();
        input.setCardFromNumber("test");
        input.setCardFromCVV("111");
        input.setCardFromValidTill("111");
        input.setCardToNumber("test2");
        input.setAmount(amountDto);

        Card cardFrom = Card.builder()
                .id(input.getCardFromNumber())
                .CVV(input.getCardFromCVV())
                .till(input.getCardFromValidTill())
                .amount(new Amount(1000, "RUR"))
                .build();

        Card cardTo = Card.builder()
                .id(input.getCardToNumber())
                .CVV(input.getCardFromCVV())
                .till(input.getCardFromValidTill())
                .amount(new Amount(1000, "RUR"))
                .build();


        final OperationContent operationContent = OperationContent.builder()
                .amount(new Amount(amountDto.getValue(), amountDto.getCurrency()))
                .cardFromId(cardFrom.getId())
                .cardToId(cardTo.getId())
                .build();
        Operation operation = Operation.builder()
                .id("testOp")
                .code("testOpCode")
                .build();

        when(cardRepository.getById(cardFrom.getId())).thenReturn(Optional.of(cardFrom));
        when(cardRepository.getById(cardTo.getId())).thenReturn(Optional.of(cardTo));

        when(confirmOperationService.addOperation(operationContent)).thenReturn(operation);

        SuccessOperationDto successOperationDto = transferService.transferPost(input);
        assertNotNull(successOperationDto);
        assertEquals(operation.getId(),successOperationDto.getOperationId());
    }
}