package ru.netology.moneytransferservice.repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.domain.Operation;
import ru.netology.moneytransferservice.domain.OperationContent;
import ru.netology.moneytransferservice.domain.OperationStatus;

@Repository
public class OperationRepository {
  private final static String DEFAULT_CODE = "0000";
  private final static ConcurrentHashMap<String, Operation> operationMap =
      new ConcurrentHashMap<>();

  public Optional<Operation> getById(String id) {
    return Optional.ofNullable(operationMap.get(id));
  }

  public Operation add(OperationContent operationContent) {
    final Operation operation = Operation.builder()
        .id(UUID.randomUUID().toString())
        .code(DEFAULT_CODE)
        .status(OperationStatus.CHECK)
        .content(operationContent)
        .build();
    operationMap.put(operation.getId(),operation);
    return operation;
  }

  public void update(Operation operation) {
    operationMap.put(operation.getId(), operation);
  }
}
