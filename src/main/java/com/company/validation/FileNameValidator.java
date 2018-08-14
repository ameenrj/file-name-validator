package com.company.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileNameValidator {

    private static final String FILE_VALIDATOR_DELIMITER = "[_.]";
    private static final String PREFIX = "Test";
    private static final String PORTFOLIO_CODE_FORMAT = "^[A|B|C]$";
    private static final String VALUATION_DATE_FORMAT = "ddMMyyyy";
    private static final String SEQUENCE_NUMBER_FORMAT = "^\\d\\d$";
    private static final String FILE_EXTENSION_FORMAT = "^csv$";

    public void validateCsvFileName(String fileName) {
        ValidationResult validationResult = checkValidCsvFileName(fileName);
        if (validationResult.isValid()) {
            System.out.println("File '" + fileName + "' passed validation.");
        } else {
            System.out.print("File '" + fileName + "' failed validation. ");
            if (shouldPrintGenericMessage(validationResult)) {
                System.out.println(ValidationError.GENERIC.getMessage());
            } else {
                System.out.println(getErrorMessage(validationResult.getErrors().get(0)));
            }
        }
    }

    ValidationResult checkValidCsvFileName(String fileName) {
        String[] fileParts = fileName.split(FILE_VALIDATOR_DELIMITER);
        ValidationResult validationResult = new ValidationResult();

        if (fileParts.length != 5) {
            validationResult.addError();
            return validationResult;
        }

        checkPrefixValid(fileParts[0], validationResult);
        checkPortfolioCodeValid(fileParts[1], validationResult);
        checkValuationDateValid(fileParts[2], validationResult);
        checkSequenceNumberValid(fileParts[3], validationResult);
        checkFileExtensionValid(fileParts[4], validationResult);

        return validationResult;
    }

    void checkPrefixValid(String prefix, ValidationResult validationResult) {
        if (!prefix.equals(PREFIX)) {
            validationResult.addError(ValidationError.PREFIX, prefix);
        }
    }

    void checkPortfolioCodeValid(String sequenceNumber, ValidationResult validationResult) {
        if (!sequenceNumber.matches(PORTFOLIO_CODE_FORMAT)) {
            validationResult.addError(ValidationError.PORTFOLIO_CODE, sequenceNumber);
        }
    }

    void checkValuationDateValid(String date, ValidationResult validationResult) {
        try {
            DateTimeFormatter.ofPattern(VALUATION_DATE_FORMAT).parse(date);
        } catch (DateTimeParseException e) {
            validationResult.addError(ValidationError.VALUATION_DATE, date);
        }
    }

    void checkSequenceNumberValid(String sequenceNumber, ValidationResult validationResult) {
        if (!sequenceNumber.matches(SEQUENCE_NUMBER_FORMAT)) {
            validationResult.addError(ValidationError.SEQUENCE_NUMBER, sequenceNumber);
        }
    }

    void checkFileExtensionValid(String fileExtension, ValidationResult validationResult) {
        if (!fileExtension.matches(FILE_EXTENSION_FORMAT)) {
            validationResult.addError(ValidationError.FILE_EXTENSION, fileExtension);
        }
    }

    boolean shouldPrintGenericMessage(ValidationResult validationResult) {
        return validationResult.getErrors().size() > 1 ||
                validationResult.getErrors().stream().anyMatch(ve -> ve.equals(ValidationError.GENERIC));
    }

    String getErrorMessage(ValidationError validationError) {
        return validationError.getMessage() + " found " + "'" + validationError.getFound() + "'.";
    }
}
