package bankserver.account;

import java.util.ArrayList;

public class Logins
{
    private static final ArrayList<String> loggedInAccounts = new ArrayList<>();

    public static boolean addLogin(String email)
    {
        try
        {
            return loggedInAccounts.add(email);
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public static boolean removeLogin(String email)
    {
        try
        {
            return loggedInAccounts.remove(email);
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public static boolean checkLogin(String email)
    {
        try
        {
            return loggedInAccounts.contains(email);
        }
        catch (Exception exception)
        {
            return false;
        }
    }

}
