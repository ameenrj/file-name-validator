package com.company.validation;

public enum ValidationError {
    PREFIX("Prefix for the file should be 'Test'"),
    PORTFOLIO_CODE("PortfolioCode should be A/B/C"),
    VALUATION_DATE("Valuation Date is not a valid date format 'ddmmyyyy'"),
    SEQUENCE_NUMBER("Sequence number should be two digits"),
    FILE_EXTENSION("Invalid File format.Expected 'csv'"),
    GENERIC("File format should be 'Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv'");

    private String message;
    private String found;

    ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }
}
