package com.company;

import com.company.validation.FileNameValidator;

public class Application {

    public static void main(String[] args) {
        if (args.length != 1) {
          System.out.println("ERROR!\n" +
                  "Please enter only one argument;\n" +
                  "A file name in the format 'Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv'\n" +
                  "Note: A 'portfoliocode' can only be 'A', 'B' or 'C' (case sensitive)");
        }
        FileNameValidator fileNameValidator = new FileNameValidator();
        fileNameValidator.validateCsvFileName(args[0]);
    }

}
