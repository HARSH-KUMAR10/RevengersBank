package client.controller;

import model.Utilities;
import validation.AccountValidation;
import model.SocketControllers;
import model.UserSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientAccountController
{
    public static void signUp(SocketControllers socketControllers)
    {
        try
        {
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            // Taking user name input

            System.out.print("Enter name: ");

            String name = userInputReader.readLine();

            while (!AccountValidation.validateName(name))
            {
                System.out.print("Wrong input, please enter name again: ");

                name = userInputReader.readLine();
            }

            // Taking user age input

            System.out.print("Enter age: ");

            String age = userInputReader.readLine();

            while (!AccountValidation.validateAge(age))
            {
                System.out.print("Wrong input, please enter age(12-99) again: ");

                age = userInputReader.readLine();
            }

            // Taking user gender input

            System.out.print("1. Female\n2. Male\nEnter choice: ");

            String genderChoice = userInputReader.readLine();

            while (!AccountValidation.validateGender(genderChoice))
            {
                System.out.print("Wrong input, please enter gender(1. Female, 2. Male) again: ");

                genderChoice = userInputReader.readLine();
            }

            // Taking user email input

            System.out.print("Enter email: ");

            String email = userInputReader.readLine();

            while (AccountValidation.validateEmail(email))
            {
                System.out.print("Wrong input, please enter email again: ");

                email = userInputReader.readLine();
            }

            // Taking user pin input

            System.out.print("Enter PIN(4 digit): ");

            String pin = userInputReader.readLine();

            while (AccountValidation.validatePin(pin))
            {
                System.out.print("Wrong input, please enter PIN(4 digit) again: ");

                pin = userInputReader.readLine();
            }

            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + "==Account::Create->" + name + ";" + age + ";"
                             + (genderChoice.equals("1") ? "Female" : "Male") + ";" + email + ";" + pin);

            String response = socketControllers.reader.readLine();

            System.out.println(Utilities.OUTPUT_DIVIDER);

            System.out.println(response);

            System.out.println(Utilities.OUTPUT_DIVIDER);

        }
        catch (Exception exception)
        {
            System.out.println("Error occurred: please restart.");
        }
    }

    public static void login(SocketControllers socketControllers)
    {
        try
        {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter email: ");

            String email = bufferedReader.readLine();

            while (AccountValidation.validateEmail(email))
            {

                System.out.print("Wrong input, please enter email again: ");

                email = bufferedReader.readLine();

            }

            System.out.print("Enter pin: ");

            String pin = bufferedReader.readLine();

            while (AccountValidation.validatePin(pin))
            {

                System.out.print("Wrong input, please enter pin again: ");

                pin = bufferedReader.readLine();

            }

            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + Utilities.DOUBLE_EQUAL_DELIMITER + Utilities.API_ACTION_ACCOUNT
                             + Utilities.DOUBLE_COLON_DELIMITER + Utilities.READ
                             + Utilities.ARROW_DELIMITER + email
                             + Utilities.SEMI_COLON_DELEMITER + pin);

            String response = socketControllers.reader.readLine();

            System.out.println(Utilities.OUTPUT_DIVIDER);

            if (!response.equalsIgnoreCase("-1") && !response.equalsIgnoreCase("-2"))
            {

                UserSession userSession = new UserSession(email, response);

                System.out.println("Login Successful");

                boolean menuFlag = true;

                while (menuFlag)
                {

                    System.out.print("1. Deposit\n2. Withdrawal\n3. Account Details\n4. Fund Transfer\n0. Logout\nEnter your choice: ");

                    switch (bufferedReader.readLine())
                    {
                        case "1" -> ClientBankController.deposit(userSession, socketControllers);

                        case "2" -> ClientBankController.withdrawal(userSession, socketControllers);

                        case "3" -> ClientBankController.getAccountDetail(userSession, socketControllers);

                        case "4" -> ClientBankController.fundTransfer(userSession, socketControllers);

                        case "0" ->
                        {
                            if (logout(socketControllers, userSession))
                            {
                                userSession = null;
                                menuFlag = false;
                                System.out.println("logging out ...\nClearing session ...");
                            }
                            else
                            {
                                System.out.println("Unable to logout.");
                            }
                        }
                        default -> System.out.println("Wrong input");
                    }
                }
                System.out.println(Utilities.OUTPUT_DIVIDER);

            }
            else
            {
                if (response.equalsIgnoreCase("-1"))
                {
                    System.out.println("Login failed, check credentials");
                }
                else if (response.equalsIgnoreCase("-2"))
                {
                    System.out.println("Already logged in");
                }
                else
                {
                    System.out.println("Login failed");
                }
            }

            System.out.println(Utilities.OUTPUT_DIVIDER);

        }
        catch (Exception exception)
        {
            System.out.println("Error occurred: please restart");
        }
    }


    // Account::Logout -> /10.20.40.194:89765==Account::Logout->email;sessionId
    public static boolean logout(SocketControllers socketControllers, UserSession userSession)
    {
        try
        {
            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + Utilities.DOUBLE_EQUAL_DELIMITER + Utilities.API_ACTION_ACCOUNT
                             + Utilities.DOUBLE_COLON_DELIMITER + Utilities.LOGOUT
                             + Utilities.ARROW_DELIMITER + userSession.getEmail()
                             + Utilities.SEMI_COLON_DELEMITER + userSession.getSessionId());

            String response = socketControllers.reader.readLine();
            if(response.equalsIgnoreCase(Utilities.LOGOUT_SUCCESS)){
                return true;
            }else{
                System.out.println(response);
                return false;
            }
        }
        catch (Exception exception)
        {
            System.out.println("Error occurred: please restart");
            return false;
        }
    }

}
