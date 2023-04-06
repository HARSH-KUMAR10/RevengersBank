package bankserver.controller;

import bankserver.account.Accounts;
import bankserver.account.Logins;
import model.Account;
import model.Utilities;
import validation.AccountValidation;

public class ServerAccountController
{

    private static final String DEFAULT_SESSION_ID = "-1";

    private static final String DEFAULT_LOGGED_ID = "-2";

    // Account::Create -> /10.20.40.194:89765==Account::Create->name;age;gender;email;pin
    public static String createAccount(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 5 && AccountValidation.validateName(values[0])
                && AccountValidation.validateAge(values[1]) && AccountValidation.validateGenderString(values[2])
                && !AccountValidation.validateEmail(values[3]) && !AccountValidation.validatePin(values[4]))
            {
                return Accounts.setAccount(values[0], Integer.parseInt(values[1]),
                        values[2], values[3], values[4], 0.0);
            }
            else
            {
                return Utilities.WRONG_INPUT;
            }
        }
        catch (Exception exception)
        {
            return Utilities.SERVER_ERROR;
        }
    }

    // Account::Read -> /10.20.40.194:89765==Account::Read->email;pin
    public static String loginAccount(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 2
                && !AccountValidation.validateEmail(values[0])
                && !AccountValidation.validatePin(values[1]))
            {
                Account account = Accounts.getAccount(values[0], values[1]);
                if (account != null)
                {
                    if (!Logins.checkLogin(values[0]))
                    {
                        Logins.addLogin(values[0]);
                        return account.getSessionId();
                    }
                    else
                    {
                        return DEFAULT_LOGGED_ID;
                    }
                }
                else
                {
                    return DEFAULT_SESSION_ID;
                }
            }
            else
            {
                return DEFAULT_SESSION_ID;
            }
        }
        catch (Exception exception)
        {
            return DEFAULT_SESSION_ID;
        }
    }

    public static String logoutAccount(String valuesString)
    {
        try{
            System.out.println(valuesString);
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 2
                && !AccountValidation.validateEmail(values[0])
                && AccountValidation.validateSessionId(values[1]))
            {
                if(Logins.checkLogin(values[0])){
                    Logins.removeLogin(values[0]);
                    return Utilities.LOGOUT_SUCCESS;
                }else{
                    return Utilities.LOGOUT_SUCCESS;
                }
            }else{
                return Utilities.WRONG_INPUT+", please restart session";
            }
        }catch (Exception exception){
            return "Server error: unable to logout, please restart session.";
        }
    }
}
