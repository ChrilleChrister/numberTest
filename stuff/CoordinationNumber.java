import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinationNumber implements ValidityCheck {

    private static final Pattern SWEDISH_NUMBER_PATTERN = Pattern.compile("^(\\d{6}[+-]?\\d{4}|\\d{8}[+-]?\\d{4}|\\d{10}|\\d{12})$");
    private static final int COORDINATION_NUMBER_DAY_VALUE = 60;
    private final SwedishNumberValidator swedishNumberValidator;
    private final String VALIDITY_CHECK_NAME = "Coordinationnumber";


    public CoordinationNumber(SwedishNumberValidator swedishNumberValidator) {
        this.swedishNumberValidator = swedishNumberValidator;

    }


    @Override
    public boolean isValid(final String coordinationNumber) {

        if (ValidationUtils.isNullOrEmpty(coordinationNumber)) {
            return false;
        }

        return isValidCoordinationNumber(coordinationNumber);
    }

    public boolean isValidCoordinationNumber(final String coordinationNumber) {
        Matcher matcher = SWEDISH_NUMBER_PATTERN.matcher(coordinationNumber);
        if (!matcher.matches()) {
            return false;
        }

        boolean isOverHundredYearsOld = coordinationNumber.contains("+");

        String cleanCoordinationNumber = coordinationNumber.replace("-", "").replace("+", "");

        if (cleanCoordinationNumber.length() == 12 && Integer.parseInt(cleanCoordinationNumber.substring(0, 2)) < 18) {
            return false;
        }

        String datePart = cleanCoordinationNumber.length() == 12 ? cleanCoordinationNumber.substring(0, 8) : cleanCoordinationNumber.substring(0, 6);

        datePart = subtractCoordinationNumberDayValue(datePart);

        if (!swedishNumberValidator.isValidDateForSsn(datePart, isOverHundredYearsOld)) {
            return false;
        }

        String coordinationNumberTenDigits = cleanCoordinationNumber.length() == 12 ? cleanCoordinationNumber.substring(2) : cleanCoordinationNumber;



        int controlNumber = swedishNumberValidator.getControlNumber(coordinationNumberTenDigits);

        int lastDigitSsn = Integer.parseInt(cleanCoordinationNumber.substring(cleanCoordinationNumber.length() - 1));

        return controlNumber == lastDigitSsn;
    }

    private String subtractCoordinationNumberDayValue(final String datePart) {
        int daysAsInt = datePart.length() == 8 ? Integer.parseInt(datePart.substring(6,8)) : Integer.parseInt(datePart.substring(4,6));
        int addedValueInt = daysAsInt - COORDINATION_NUMBER_DAY_VALUE;
        String formattedNumberIfBelowTen = String.format("%02d", addedValueInt);

        if (datePart.length() == 8) {
            return datePart.substring(0, 6) + formattedNumberIfBelowTen + datePart.substring(8);
        }
        return datePart.substring(0, 4) + formattedNumberIfBelowTen + datePart.substring(6);

    }

    @Override
    public String toString() {
        return VALIDITY_CHECK_NAME;
    }
}
