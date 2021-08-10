package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openapitools.model.ConfirmOperationReqDto;
import org.openapitools.model.SuccessOperationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.netology.moneytransferservice.domain.Amount;
import ru.netology.moneytransferservice.domain.Operation;
import ru.netology.moneytransferservice.domain.OperationContent;
import ru.netology.moneytransferservice.domain.OperationStatus;
import ru.netology.moneytransferservice.repository.OperationRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ConfirmOperationServiceTest {
    @MockBean
    private OperationRepository operationRepository;

    @Autowired
    private ConfirmOperationService confirmOperationService;

    @Test
    void addOperation() {
        final OperationContent inputContent = OperationContent.builder()
                .amount(new Amount(1, "RUR"))
                .cardFromId("111")
                .cardToId("222")
                .build();
        final Operation input = Operation.builder()
                .id(UUID.randomUUID().toString())
                .code("DEFAULT_CODE")
                .status(OperationStatus.CHECK)
                .content(inputContent)
                .build();

        when(operationRepository.add(inputContent)).thenReturn(input);

        Operation output = confirmOperationService.addOperation(inputContent);
        assertNotNull(output);
        assertEquals(output.getContent(), inputContent);
        assertEquals(output.getId(), input.getId());
        assertEquals(output.getCode(), input.getCode());
        assertEquals(output.getStatus(), input.getStatus());
    }

    @Test
    void confirmOperationPost_thenReturnExpByNotFound() {
        ConfirmOperationReqDto input = new ConfirmOperationReqDto();
        input.setOperationId("test");

        when(operationRepository.getById(input.getOperationId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> confirmOperationService.confirmOperationPost(input));
    }

    @Test
    void confirmOperationPost_thenReturnExpByNotEqual() {
        ConfirmOperationReqDto input = new ConfirmOperationReqDto();
        input.setOperationId("test");
        input.setCode("111");

        Operation operation = Operation.builder()
                .id("test1")
                .code("222")
                .build();

        when(operationRepository.getById(input.getOperationId())).thenReturn(Optional.of(operation));

        assertThrows(IllegalArgumentException.class, () -> confirmOperationService.confirmOperationPost(input));
    }

    @Test
    void confirmOperationPost_thenReturnGood() {
        ConfirmOperationReqDto input = new ConfirmOperationReqDto();
        input.setOperationId("test");
        input.setCode("111");

        Operation operation = Operation.builder()
                .id(input.getOperationId())
                .code(input.getCode())
                .build();

        when(operationRepository.getById(input.getOperationId())).thenReturn(Optional.of(operation));

        SuccessOperationDto output = confirmOperationService.confirmOperationPost(input);
        assertNotNull(output);
        assertEquals(output.getOperationId(), input.getOperationId());
    }

    @Test
    void failedCode() {

    }
}