package ru.nsu.brusn.lab1.model.dto.request;

import lombok.Getter;

@Getter
public class CrackHashWorkerRequest {
    private String requestId;
    private String data;
}
