import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        SwedishNumberValidator swedishNumberValidator = new SwedishNumberValidator();
        SocialSecurityNumber socialSecurityNumber = new SocialSecurityNumber(swedishNumberValidator);
        CoordinationNumber coordinationNumber = new CoordinationNumber(swedishNumberValidator);
        OrganizationNumber organizationNumber = new OrganizationNumber();

        socialSecurityNumber.isValid("900118+9811");

        List<String> ssnToValidateList = Arrays.asList(
                //valid
                "201701102384",
                "141206-2380",
                "20080903-2386",
                "7101169295",
                "198107249289",
                "19021214-9819",
                "190910199827",
                "192109099180",
                "4607137454",
                "194510168885",
                "900118+9811",
                "189102279800",
                "189912299816",
                "920831-2257",
                //not valid
                null,
                "",
                "-80202-2389",
                "20020202-2389111",
                "-200202022389",
                "200202022389-",
                "19780202+2389",
                "20310411-2380",
                "18310411-2380",
                "201701272394",
                "190302299813"
        );

        List<String> coordinationNumbersToValidateList = Arrays.asList(
                //valid
                "190910799824",
                "201701702381",
                "141266-2387",
                "20080963-2383",
                "7101769292",
                "198107849286",
                "19021274-9816",
                "190910799824",
                "192109699187",
                "4607737451",
                "194510768882",
                "900178+9818",
                "189102879807",
                "189912899813",
                "920891-2254",
                //not valid
                null,
                "",
                "-80262-2389",
                "20020262-2389111",
                "-200202622389",
                "200202622389-",
                "19780262+2389",
                "20310471-2380",
                "18310471-2380",
                "201701872394",
                "190302899813"
        );

        List<String> organizationNumbersToValidateList = Arrays.asList(
                //valid
                "556614-3185",
                "16556601-6399",
                "167744016399",
                "2244881212",
                "262000-1111",
                "857202-7566",
                //not valid
                null,
                "",
                "851902-7566",
                "8519027566",
                "16191901-6399",
                "162019016399",
                "17262000-1111",
                "172620001111"
        );


        ValidityCheckValidationRunner validationRunnerForSsn = new ValidityCheckValidationRunner(socialSecurityNumber, ssnToValidateList);
        ValidityCheckValidationRunner validationRunnerForCoordinationNumber = new ValidityCheckValidationRunner(coordinationNumber, coordinationNumbersToValidateList);
        ValidityCheckValidationRunner validationRunnerForOrganizationNumber = new ValidityCheckValidationRunner(organizationNumber, organizationNumbersToValidateList);
        validationRunnerForSsn.runDataValidation();
        validationRunnerForCoordinationNumber.runDataValidation();
        validationRunnerForOrganizationNumber.runDataValidation();
    }
}
