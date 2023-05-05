package com.chargeback.processor.controller;

import com.chargeback.processor.dto.TransactionResponseDto;
import com.chargeback.processor.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponseDto> findAll() {
        return transactionService.findAll();
    }
}
