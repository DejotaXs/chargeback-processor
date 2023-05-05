package com.chargeback.processor.component;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Component
public interface FileProcessor<T> {
    T retrieveStructFromFile(final MultipartFile file,
                             final Optional<Integer> dateRowNumber,
                             final Optional<String> dateFormat,
                             final Optional<Integer> amountRowNumber) throws IOException;
}
