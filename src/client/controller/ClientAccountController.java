package client.controller;

import client.validations.AccountValidation;
import models.SocketControllers;
import models.UserSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientAccountController
{
    public static boolean signUp(SocketControllers socketControllers)
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

            while (!AccountValidation.validateEmail(email))
            {
                System.out.print("Wrong input, please enter email again: ");

                email = userInputReader.readLine();
            }

            // Taking user pin input

            System.out.print("Enter PIN(4 digit): ");

            String pin = userInputReader.readLine();

            while (!AccountValidation.validatePin(pin))
            {
                System.out.println("Wrong input, please enter PIN(4 digit) again: ");

                pin = userInputReader.readLine();
            }

            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + "==Account::Create->" + name + ";" + age + ";"
                             + (genderChoice.equals("1") ? "Female" : "Male") + ";" + email + ";" + pin);

            String response = socketControllers.reader.readLine();

            System.out.println("----------------------------------------------");

            System.out.println(response);

            System.out.println("----------------------------------------------");


            return true;

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    public static void login(SocketControllers socketControllers)
    {
        try
        {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter email: ");

            String email = bufferedReader.readLine();

            while (!AccountValidation.validateEmail(email))
            {

                System.out.print("Wrong input, please enter email again: ");

                email = bufferedReader.readLine();

            }

            System.out.print("Enter pin: ");

            String pin = bufferedReader.readLine();

            while (!AccountValidation.validatePin(pin))
            {

                System.out.print("Wrong input, please enter pin again: ");

                pin = bufferedReader.readLine();

            }

            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + "==Account::Read->" + email + ";" + pin);

            String response = socketControllers.reader.readLine();

            System.out.println("----------------------------------------------");

            if(!response.equalsIgnoreCase("-1")){

                UserSession userSession = new UserSession(email,Integer.parseInt(response));

                System.out.println("Login Successful");

                boolean menuFlag = true;

                while(menuFlag)
                {

                    System.out.print("1. Deposit\n2. Withdrawal\n3. Account Details\n4. Fund Transfer\n0. Logout\nEnter your choice: ");

                    switch (bufferedReader.readLine())
                    {
                        case "1" -> ClientBankController.deposit(userSession,socketControllers);

                        case "2" -> ClientBankController.withdrawal(userSession,socketControllers);

                        case "3" -> ClientBankController.getAccountDetail(userSession,socketControllers);

                        case "0" -> {
                            System.out.println("logging out ...\nClearing session ...");
                            userSession = null;
                            menuFlag = false;
                        }
                        default -> System.out.println("Wrong input");
                    }
                }

            }else{
                System.out.println("Login Failed");
            }


            System.out.println("----------------------------------------------");

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
