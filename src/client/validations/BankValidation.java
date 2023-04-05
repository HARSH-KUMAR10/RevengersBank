package client.validations;

import java.util.regex.Pattern;

public class BankValidation
{
    public static boolean validateAmount(String amount){
        return Pattern.matches("[0-9]+\\.\\d{2}",amount);
    }

    public static boolean validateAccountNumber(String accountNumber){
        return Pattern.matches("^\\d{4}$",accountNumber);
    }

}
