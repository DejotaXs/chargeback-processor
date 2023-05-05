package com.chargeback.processor.controller;

import com.chargeback.processor.dto.ChargebackResponseDto;
import com.chargeback.processor.exception.types.DatabaseErrorException;
import com.chargeback.processor.exception.types.FileErrorException;
import com.chargeback.processor.exception.types.InternalServerErrorException;
import com.chargeback.processor.exception.types.KeyParameterErrorException;
import com.chargeback.processor.service.ChargebackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chargebacks")
public class ChargebackController {

    private final ChargebackService chargebackService;

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.CREATED)
    public ChargebackResponseDto handleFileUpload(
            @RequestParam("file") final MultipartFile multipartFile,
            @RequestParam final Optional<Integer> dateRowNumber,
            @RequestParam final Optional<String> dateFormat,
            @RequestParam final Optional<Integer> amountRowNumber) {
        try {
            return chargebackService.processFile(multipartFile, dateRowNumber, dateFormat, amountRowNumber);
        } catch (IOException ex) {
            final String error = "Error at trying to handle with files";
            log.error(error);
            throw new FileErrorException(error);
        } catch (SQLException ex) {
            final String error = "Error at trying to handle with database";
            log.error(error);
            throw new DatabaseErrorException(error);
        } catch (IndexOutOfBoundsException ex) {
            final String error = "key/query parameter not sent";
            log.error(error);
            throw new KeyParameterErrorException(error);
        } catch (Exception ex) {
            final String error = "Unexpected Error occurred";
            log.error(error);
            throw new InternalServerErrorException(error);
        }
    }
}
