package com.company.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class FileNameValidatorTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private final FileNameValidator fileNameValidator = new FileNameValidator();

    @Test
    public void givenValidFileName_validationIsSuccessful() {
        String fileName = "Test_A_07121987_45.csv";
        fileNameValidator.validateCsvFileName(fileName);

        assertThat(systemOutRule.getLog()).isEqualTo("File '" + fileName + "' passed validation.\r\n");
    }

    @Test
    public void givenInvalidFileName_validationIsUnsuccessful_withSpecificErrorMessage() {
        // A total of one error gives a specific error message
        String fileName = "Test_D_07121987_45.csv";
        ValidationError validationError = ValidationError.PORTFOLIO_CODE;
        validationError.setFound("D");
        fileNameValidator.validateCsvFileName(fileName);

        assertThat(systemOutRule.getLog()).isEqualTo("File '" + fileName + "' failed validation. " +
                fileNameValidator.getErrorMessage(validationError) + "\r\n");
    }

    @Test
    public void givenInvalidFileName_validationIsUnsuccessful_withGenericErrorMessage() {
        // A total of one error gives a specific error message
        String fileName = "Test_D_07121987_4!.csv";
        fileNameValidator.validateCsvFileName(fileName);

        assertThat(systemOutRule.getLog()).isEqualTo("File '" + fileName + "' failed validation. " +
                ValidationError.GENERIC.getMessage() + "\r\n");
    }

    @Test
    public void givenValidPrefix_validationResultIsValid() {
        ValidationResult validationResult = new ValidationResult();
        String prefix = "Test";

        fileNameValidator.checkPrefixValid(prefix, validationResult);
        assertThat(validationResult.isValid()).isTrue();
    }

    @Test
    public void givenInvalidPrefix_validationResultIsInvalid() {
        ValidationResult validationResult = new ValidationResult();
        String prefix = "Testt";

        fileNameValidator.checkPrefixValid(prefix, validationResult);
        assertThat(validationResult.isValid()).isFalse();
    }

    @Test
    public void givenValidPortfolioCode_validationResultIsValid() {
        ValidationResult validationResult = new ValidationResult();
        String portfolioCode = "C";

        fileNameValidator.checkPortfolioCodeValid(portfolioCode, validationResult);
        assertThat(validationResult.isValid()).isTrue();
    }

    @Test
    public void givenInvalidPortfolioCode_validationResultIsInvalid() {
        ValidationResult validationResult = new ValidationResult();
        // Any portfolio code between D and Z
        char thread = (char) (ThreadLocalRandom.current().nextInt(3, 26) + 'a');
        String portfolioCode = String.valueOf(thread).toUpperCase();

        fileNameValidator.checkPortfolioCodeValid(portfolioCode, validationResult);
        assertThat(validationResult.isValid()).isFalse();
    }

    @Test
    public void givenValidValuationDate_validationResultIsValid() {
        ValidationResult validationResult = new ValidationResult();
        String valuationDate = "02122012";

        fileNameValidator.checkValuationDateValid(valuationDate, validationResult);
        assertThat(validationResult.isValid()).isTrue();
    }

    @Test
    public void givenInvalidValuationDate_validationResultIsInvalid() {
        ValidationResult validationResult = new ValidationResult();
        String valuationDate = "02132012";

        fileNameValidator.checkValuationDateValid(valuationDate, validationResult);
        assertThat(validationResult.isValid()).isFalse();

        validationResult = new ValidationResult();
        valuationDate = "32122012";

        fileNameValidator.checkValuationDateValid(valuationDate, validationResult);
        assertThat(validationResult.isValid()).isFalse();

        validationResult = new ValidationResult();
        valuationDate = "021299";

        fileNameValidator.checkValuationDateValid(valuationDate, validationResult);
        assertThat(validationResult.isValid()).isFalse();
    }

    @Test
    public void givenValidSequenceNumber_validationResultIsValid() {
        ValidationResult validationResult = new ValidationResult();
        String sequenceNumber = "45";

        fileNameValidator.checkSequenceNumberValid(sequenceNumber, validationResult);
        assertThat(validationResult.isValid()).isTrue();
    }

    @Test
    public void givenInvalidSequenceNumber_validationResultIsInvalid() {
        ValidationResult validationResult = new ValidationResult();
        String sequenceNumber = "453";

        fileNameValidator.checkSequenceNumberValid(sequenceNumber, validationResult);
        assertThat(validationResult.isValid()).isFalse();

        validationResult = new ValidationResult();
        sequenceNumber = "4?";

        fileNameValidator.checkSequenceNumberValid(sequenceNumber, validationResult);
        assertThat(validationResult.isValid()).isFalse();
    }

    @Test
    public void givenValidFileExtension_validationResultIsValid() {
        ValidationResult validationResult = new ValidationResult();
        String fileExtension = "csv";

        fileNameValidator.checkFileExtensionValid(fileExtension, validationResult);
        assertThat(validationResult.isValid()).isTrue();
    }

    @Test
    public void givenInvalidFileExtension_validationResultIsInvalid() {
        ValidationResult validationResult = new ValidationResult();
        String fileExtension = "txt";

        fileNameValidator.checkFileExtensionValid(fileExtension, validationResult);
        assertThat(validationResult.isValid()).isFalse();
    }

    @Test
    public void givenValidationResultWithMultipleErrors_genericMessageShouldBePrinted() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addError(ValidationError.PORTFOLIO_CODE, "blah");
        validationResult.addError(ValidationError.FILE_EXTENSION, "blah");

        boolean shouldPrintGenericMessage = fileNameValidator.shouldPrintGenericMessage(validationResult);
        assertThat(shouldPrintGenericMessage).isTrue();
    }

    @Test
    public void givenValidationResultWithGenericMessage_genericMessageShouldBePrinted() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addError();

        boolean shouldPrintGenericMessage = fileNameValidator.shouldPrintGenericMessage(validationResult);
        assertThat(shouldPrintGenericMessage).isTrue();
    }

    @Test
    public void givenValidationResultWithOneNonGenericError_genericMessageShouldBeNotPrinted() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addError(ValidationError.PREFIX, "blah");

        boolean shouldPrintGenericMessage = fileNameValidator.shouldPrintGenericMessage(validationResult);
        assertThat(shouldPrintGenericMessage).isFalse();
    }

}
