package client.validations;

import java.util.regex.Pattern;

public class BankValidation
{
    public static boolean validateAmount(String amount){
        return Pattern.matches("[0-9]+\\.[0-9][0-9]",amount);
    }
}
