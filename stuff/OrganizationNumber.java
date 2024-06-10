import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganizationNumber implements ValidityCheck{
    private static final Pattern SWEDISH_NUMBER_PATTERN = Pattern.compile("^(\\d{6}[+-]?\\d{4}|\\d{8}[+-]?\\d{4}|\\d{10}|\\d{12})$");

    private static final int ORGANIZATION_NUMBER_AT_LEAST_VALUE = 20;
    private static final String VALIDITY_CHECK_NAME = "Organization number";
    private static final String ORG_NUMBER_START_VALUE = "16";


    @Override
    public boolean isValid(final String orgNumber) {
        if (ValidationUtils.isNullOrEmpty(orgNumber)) {
            return false;
        }
        return isValidOrganizationNumber(orgNumber);
    }

    private boolean isValidOrganizationNumber(final String orgNumber) {
        Matcher matcher = SWEDISH_NUMBER_PATTERN.matcher(orgNumber);
        if (!matcher.matches()) {
            return false;
        }

        if (orgNumber.length() >= 12 && !orgNumber.startsWith(ORG_NUMBER_START_VALUE)) {
            return false;
        }

        int middleNumberValue = orgNumber.length() >= 12 ? Integer.parseInt(orgNumber.substring(4,6)) : Integer.parseInt(orgNumber.substring(2,4));

        return middleNumberValue >= ORGANIZATION_NUMBER_AT_LEAST_VALUE;
    }

    @Override
    public String toString() {
        return VALIDITY_CHECK_NAME;
    }
}
