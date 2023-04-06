package validation;

import java.util.regex.Pattern;

public class AccountValidation
{
    public static boolean validateName(String name)
    {
        return name.length() >= 5 && Pattern.matches("^[A-Za-z ]+$", name);
    }

    public static boolean validateAge(String age)
    {
        return Pattern.matches("^\\d{2}$", age) && Integer.parseInt(age) > 11;
    }

    public static boolean validateGender(String genderChoice)
    {
        return genderChoice.equals("1") || genderChoice.equals("2");
    }

    public static boolean validateGenderString(String genderString)
    {
        return genderString.equalsIgnoreCase("female") || genderString.equalsIgnoreCase("male");
    }

    public static boolean validateEmail(String email)
    {
        return !Pattern.matches("^[a-zA-Z.]+[0-9]*[a-zA-Z]*@[A-Za-z]+(\\.[A-Za-z]+)+$", email);
    }

    public static boolean validatePin(String pin)
    {
        return !Pattern.matches("^\\d{4}$", pin);
    }

    public static boolean validateSessionId(String sessionId)
    {
        return sessionId != null && sessionId.length() > 0;
    }
}
