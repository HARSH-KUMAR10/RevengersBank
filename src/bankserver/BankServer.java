package bankserver;

import bankserver.controller.ServerAccountController;
import bankserver.controller.ServerBankController;
import models.SocketControllers;
import models.Utilities;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class BankServer implements Server
{
    static int clientCount = 0;

    static ServerSocket bankServer;


    static ConcurrentHashMap<String, SocketControllers> clients = new ConcurrentHashMap<>();

    public BankServer()
    {
        createServer();
        startConnections();
    }

    @Override
    public void createServer()
    {
        try
        {
            bankServer = new ServerSocket(Utilities.PORT);

            System.out.println("Server started at " + Utilities.IP + ":" + Utilities.PORT);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void startConnections()
    {
        try
        {
            new Thread(() -> {
                while (true)
                {
                    try
                    {
                        Socket socket = bankServer.accept();

                        System.out.println("New connection established, " + socket.getRemoteSocketAddress());

                        SocketControllers socketControllers = new SocketControllers(socket);

                        clients.put(socket.getRemoteSocketAddress().toString(), socketControllers);

                        clientCount++;

                        startReadingClient(socketControllers);

                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }, "read-connection-thread").start();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void startReadingClient(SocketControllers socketControllers)
    {
        try
        {
            new Thread(() -> {
                try
                {
                    while (true)
                    {
                        String request = socketControllers.reader.readLine();
                        if (request != null && request.contains("==") && request.contains("->") && request.contains("::"))
                        {
                            String[] requestSplit = request.split("==");
                            String remoteSocketAddress = requestSplit[0];
                            String[] apiContext = requestSplit[1].split("->");
                            String[] api = apiContext[0].split("::");
                            System.out.println(remoteSocketAddress + " -> " + Arrays.toString(api));
                            if (api[0].equalsIgnoreCase("Account"))
                            {
                                if (api[1].equalsIgnoreCase("Create"))
                                {
                                    if (ServerAccountController.createAccount(remoteSocketAddress, apiContext[1]))
                                    {
                                        socketControllers.writer.println("Account created successfully");
                                    }
                                    else
                                    {
                                        socketControllers.writer.println("Unable to create new account.");
                                    }
                                }
                                else if (api[1].equalsIgnoreCase("Read"))
                                {
                                    socketControllers.writer.println(ServerAccountController.loginAccount(remoteSocketAddress, apiContext[1]));
                                }
                            }
                            else if (api[0].equalsIgnoreCase("Bank"))
                            {
                                if (api[1].equalsIgnoreCase("Deposit"))
                                {
                                    socketControllers.writer
                                            .println(ServerBankController.deposit(remoteSocketAddress, apiContext[1]));
                                }
                                else if (api[1].equalsIgnoreCase("Withdrawal"))
                                {
                                    socketControllers.writer
                                            .println(ServerBankController.withdrawal(remoteSocketAddress, apiContext[1]));
                                }
                                else if (api[1].equalsIgnoreCase("Details"))
                                {
                                    socketControllers.writer
                                            .println(ServerBankController.details(remoteSocketAddress, apiContext[1]));
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Unknown request:" + request + "\nClosing connection.");
                            socketControllers.writer.println("Unable to parse request");
                            break;
                        }
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }, "server-reading-client-" + clientCount).start();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new BankServer();
    }
}
