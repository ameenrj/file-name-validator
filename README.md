# file-name-validator

# Problem

Given a file location, the program should validate the filename. If the file is invalid, it should print the error 
details along with the status of the file as passed or failed. The file name format should be:

Test\_\<portfoliocode\>\_\<ddmmyyyy\>\_\<2digit-sequencenumber\>.csv

Where:
- Test – hardcoded string prefix
- \<portfoliocode\> - can only be A,B,C
- \<ddmmyyyy\>– is valuation date format dd e.g 07, mm e.g 12, yyyy e.g 1987
- \<2digit-sequencenumber\> - is 2 digit sequence number

- No use of 3rd party libraries (except testing libraries)
- The program needs to accept input file and validate file name

## Assumptions

- When two or more errors occur, a generic format error message is printed (as in spec)
- Sequence number was not tested for or even included in the test cases, assumed unintentional
- Used the exact same outputs as provided by the spec, despite spelling/grammar errors
    - EXCEPT: All failure messages ended with a fullstop (spec varies).

## Installation

First, clone the project:
```
git clone --recursive git@github.com:ameenrj/file-name-validator.git
```
Then cd into the directory of the project:
```
cd file-name-validator
```
Finally, build the project:
```
gradle build
```

## Running the app

Run the application by pointing your IDE to the main class and passing in the filename as a program argument. 
Alternatively, run;
```
java -jar build/libs/file-name-validator-0.0.1-SNAPSHOT.jar example.csv
```
For example, a csv file with a valid format, Test_A_07121987_02.csv gives the following:
```
ameen@DESKTOP-6C487UF MINGW64 ~/projects/file-name-validator
$ java -jar build/libs/file-name-validator-0.0.1-SNAPSHOT.jar Test_A_07121987_02.csv
File 'Test_A_07121987_02.csv' passed validation.
```

## Possible Improvements

- All errors in the given file name can be printed out instead of either one or a generic error