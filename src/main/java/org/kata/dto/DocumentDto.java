package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.DocumentType;

import java.util.Date;

@Data
@Builder
@Jacksonized
public class DocumentDto {

    private String icp;

    private DocumentType documentType;

    private String documentNumber;

    private String documentSerial;

    private Date issueDate;

    private Date expirationDate;

    private boolean actual;
    public void maskPersonalData() {
        // Применяем маскировку к полю documentNumber, если оно не пустое и имеет более 4 символов
        if (documentNumber != null && documentNumber.length() > 4) {
            String firstTwoChars = documentNumber.substring(0, 2);
            String lastTwoChars = documentNumber.substring(documentNumber.length() - 2);
            String maskedDocumentNumber = firstTwoChars + "****" + lastTwoChars;
            this.documentNumber = maskedDocumentNumber;
        }
    }
}
