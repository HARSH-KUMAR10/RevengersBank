package bankserver.controller;

import bankserver.accounts.Accounts;
import models.Account;

public class ServerAccountController
{
    public static boolean createAccount(String remoteSocketAddress, String valuesString)
    {
        try
        {
            String[] values = valuesString.split(";");
            if (values.length == 5)
            {
                return Accounts.setAccount(values[0], Integer.parseInt(values[1]), values[2], values[3], values[4], 0.0);
            }else{
                return false;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    public static String loginAccount(String remoteSocketAddress, String valuesString)
    {
        try
        {
            String[] values = valuesString.split(";");

            if (values.length == 2)
            {
                Account account = Accounts.getAccount(values[0],values[1]);
                return account!=null?account.getSessionId():"-1";
            }
            else
            {
                return "-1";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return "-1";
        }
    }
}
