import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ValidityCheckValidationRunner {

    ValidityCheck validityCheck;
    List<String> numbersToValidateList;
    List<String> notValidCheckList = new ArrayList<>();


    public ValidityCheckValidationRunner(ValidityCheck validityCheck, List<String> numbersToValidateList) {
        this.validityCheck = validityCheck;
        this.numbersToValidateList = numbersToValidateList;
    }

    public void runDataValidation() {
        if (!numbersToValidateList.isEmpty()) {
            numbersToValidateList.forEach(this::runValidationCheck);
        } else {
            System.out.println("List of Data to validate is empty");
        }
        saveNotValidChecksToLogFile(notValidCheckList);
    }

    public void runValidationCheck(String numberString) {
        boolean isValid = validityCheck.isValid(numberString);
        if (isValid) {
            System.out.println("VALID for " + validityCheck + ": " +  numberString);
        } else {
            System.out.println("NOT VALID for " + validityCheck + ": " + numberString);
            notValidCheckList.add(java.time.LocalDateTime.now() + " NOT VALID for  " + validityCheck + numberString + "\n");
        }

    }


    public void saveNotValidChecksToLogFile(List<String> list) {
        String data = list.toString();
        try {
            FileWriter fileWriter = new FileWriter(validityCheck + "Logfile.txt");
            fileWriter.write(data);
            fileWriter.close();
            System.out.println("Failed validations saved to logfile");
        } catch (IOException e) {
            System.out.println("Error occurred. Couldn't save information to logfile");
        }
    }

}