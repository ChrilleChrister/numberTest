import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialSecurityNumber implements ValidityCheck {

    private static final Pattern SWEDISH_NUMBER_PATTERN = Pattern.compile("^(\\d{6}[+-]?\\d{4}|\\d{8}[+-]?\\d{4}|\\d{10}|\\d{12})$");
    private static final String VALIDITY_CHECK_NAME = "Socialsecuritynumber";
    private static final int LOWEST_POSSIBLE_CENTURY = 18;
    
    private final SwedishNumberValidator swedishNumberValidator;

    public SocialSecurityNumber(SwedishNumberValidator swedishNumberValidator) {
        this.swedishNumberValidator = swedishNumberValidator;
    }

    @Override
    public boolean isValid(final String ssn){

        if (ValidationUtils.isNullOrEmpty(ssn)) {
            return false;
        }

        return isValidSSN(ssn);
    }


    private boolean isValidSSN(String ssn) {
        Matcher matcher = SWEDISH_NUMBER_PATTERN.matcher(ssn);
        if (!matcher.matches()) {
            return false;
        }

        boolean isOverHundredYearsOld = ssn.contains("+");

        String cleanSsnString = ssn.replace("-", "").replace("+", "");

        if (cleanSsnString.length() == 12 && Integer.parseInt(cleanSsnString.substring(0,2)) < LOWEST_POSSIBLE_CENTURY) {
            return false;
        }

        String datePart = cleanSsnString.length() == 12 ? cleanSsnString.substring(0, 8) : cleanSsnString.substring(0, 6);

        if (!swedishNumberValidator.isValidDateForSsn(datePart, isOverHundredYearsOld)) {
            return false;
        }

        String ssnTenDigits = cleanSsnString.length() == 12 ? cleanSsnString.substring(2) : cleanSsnString;

        int controlNumber = swedishNumberValidator.getControlNumber(ssnTenDigits);

        int lastDigitSsn = Integer.parseInt(cleanSsnString.substring(cleanSsnString.length() - 1));

        return controlNumber == lastDigitSsn;
    }

    @Override
    public String toString() {
        return VALIDITY_CHECK_NAME;
    }
}
