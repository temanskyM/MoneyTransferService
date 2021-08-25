package ru.netology.moneytransferservice.rest;

import org.junit.jupiter.api.Test;
import org.openapitools.model.AmountDto;
import org.openapitools.model.ConfirmOperationReqDto;
import org.openapitools.model.SuccessOperationDto;
import org.openapitools.model.TransferReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.AbstractContainerTest;
import ru.netology.moneytransferservice.domain.Card;
import ru.netology.moneytransferservice.repository.CardProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfirmOperationControllerTest extends AbstractContainerTest {

    @Test
    void whenConfirmOperationPost_thenSuccess() {
        AmountDto amountDto = new AmountDto();
        amountDto.setCurrency("RUR");
        amountDto.setValue(100);

        List<Card> cardList = CardProvider.generate();
        Card cardFrom = cardList.get(0);
        Card cardTo = cardList.get(1);

        TransferReqDto input = new TransferReqDto();
        input.setCardFromNumber(cardFrom.getId());
        input.setCardFromCVV(cardFrom.getCVV());
        input.setCardFromValidTill(cardFrom.getTill());
        input.setCardToNumber(cardTo.getId());
        input.setAmount(amountDto);

        String transferUrl = "http://localhost:" + devApp.getMappedPort(5500) + "/transfer";
        ResponseEntity<SuccessOperationDto> transferResponseStr = restTemplate.postForEntity(transferUrl, input, SuccessOperationDto.class);
        assertEquals(HttpStatus.OK,transferResponseStr.getStatusCode());
        SuccessOperationDto transferResponse = transferResponseStr.getBody();
        assertNotNull(transferResponse);
        assertNotNull(transferResponse.getOperationId());


        ConfirmOperationReqDto confirmOperationReqDto = new ConfirmOperationReqDto();
        confirmOperationReqDto.setOperationId(transferResponse.getOperationId());
        confirmOperationReqDto.setCode("0000");

        String confirmOperationUrl = "http://localhost:" + devApp.getMappedPort(5500) + "/confirmOperation";
        ResponseEntity<SuccessOperationDto> response = restTemplate.postForEntity(confirmOperationUrl, confirmOperationReqDto, SuccessOperationDto.class);
        assertEquals(HttpStatus.OK,transferResponseStr.getStatusCode());
        SuccessOperationDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getOperationId());
    }
}