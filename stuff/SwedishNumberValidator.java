import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SwedishNumberValidator {


    private static final DateTimeFormatter DATE_FORMAT_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_FORMAT_YYMMDD = DateTimeFormatter.ofPattern("yyMMdd");

    public boolean isValidDateForSsn(final String dateString, final boolean isOverHundredYearsOld) {
        try {
            LocalDate parsedDate;
            int currentYear = LocalDate.now().getYear() % 100;
            int fullYear;

            if (dateString.length() == 8) {
                parsedDate = LocalDate.parse(dateString, DATE_FORMAT_YYYYMMDD);
                if (isOverHundredYearsOld && !parsedDate.isBefore(LocalDate.now().minusYears(100))) {
                    return false;
                }
            } else if (dateString.length() == 6) {
                parsedDate = LocalDate.parse(dateString, DATE_FORMAT_YYMMDD);
                int parsedYear = parsedDate.getYear() % 100;

                if (parsedYear <= currentYear) {
                    fullYear = 2000 + parsedYear;
                } else {
                    fullYear = 1900 + parsedYear;
                }

                parsedDate = LocalDate.of(fullYear, parsedDate.getMonth(), parsedDate.getDayOfMonth());
            } else {
                return false;
            }

            if (parsedDate.isAfter(LocalDate.now())) {
                return false;
            }

            if (!isLeapYear(parsedDate.getYear()) && dateString.endsWith("0229")) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    public int getControlNumber(String numberString) {

        int total = 0;
        for (int i = 0; i < numberString.length() - 1; i++) {
            int digit = Character.getNumericValue(numberString.charAt(i));
            if (i % 2 == 0) digit *= 2;
            if (digit > 9) digit -= 9;
            total += digit;
        }

        return (10 - (total % 10)) % 10;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
