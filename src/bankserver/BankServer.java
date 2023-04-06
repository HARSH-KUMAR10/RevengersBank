package bankserver;

import bankserver.controller.ServerAccountController;
import bankserver.controller.ServerBankController;
import model.SocketControllers;
import model.Utilities;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class BankServer implements Server
{
    static int clientCount = 0;

    static ServerSocket bankServer;

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
            System.out.println("Unable to create server, please try again.");
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

                        clientCount++;

                        startReadingClient(socketControllers);

                    }
                    catch (Exception exception)
                    {
                        System.out.println("Server error: please restart server.");
                        break;
                    }
                }
            }, "read-connection-thread").start();
        }
        catch (Exception exception)
        {
            System.out.println("Server error: please restart server.");
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
                        if (request != null && request.contains(Utilities.DOUBLE_EQUAL_DELIMITER)
                            && request.contains(Utilities.ARROW_DELIMITER) && request.contains(Utilities.DOUBLE_COLON_DELIMITER))
                        {
                            String[] requestSplit = request.split(Utilities.DOUBLE_EQUAL_DELIMITER);

                            String remoteSocketAddress = requestSplit[0];

                            String[] apiContext = requestSplit[1].split(Utilities.ARROW_DELIMITER);

                            String[] api = apiContext[0].split(Utilities.DOUBLE_COLON_DELIMITER);

                            if (requestSplit.length != 2 && apiContext.length != 2 && api.length != 2)
                            {
                                socketControllers.writer.println("Bad Request");
                            }
                            else
                            {
                                System.out.println(remoteSocketAddress + Utilities.ARROW_DELIMITER + Arrays.toString(api));

                                if (api[0].equalsIgnoreCase(Utilities.API_ACTION_ACCOUNT))
                                {
                                    if (api[1].equalsIgnoreCase(Utilities.CREATE))
                                    {
                                        socketControllers.writer
                                                .println(ServerAccountController.createAccount(apiContext[1]));
                                    }
                                    else if (api[1].equalsIgnoreCase(Utilities.READ))
                                    {
                                        socketControllers.writer
                                                .println(ServerAccountController.loginAccount(apiContext[1]));
                                    }
                                    else if (api[1].equalsIgnoreCase(Utilities.LOGOUT))
                                    {
                                        socketControllers.writer
                                                .println(ServerAccountController.logoutAccount(apiContext[1]));
                                    }
                                    else
                                    {
                                        socketControllers.writer
                                                .println("Account route doesn't support this operation");
                                    }
                                }
                                else if (api[0].equalsIgnoreCase(Utilities.API_ACTION_BANK))
                                {
                                    if (api[1].equalsIgnoreCase(Utilities.DEPOSIT))
                                    {
                                        socketControllers.writer
                                                .println(ServerBankController.deposit(apiContext[1]));
                                    }
                                    else if (api[1].equalsIgnoreCase(Utilities.WITHDRAWAL))
                                    {
                                        socketControllers.writer
                                                .println(ServerBankController.withdrawal(apiContext[1]));
                                    }
                                    else if (api[1].equalsIgnoreCase(Utilities.DETAILS))
                                    {
                                        socketControllers.writer
                                                .println(ServerBankController.details(apiContext[1]));
                                    }
                                    else if (api[1].equalsIgnoreCase(Utilities.TRANSFER))
                                    {
                                        socketControllers.writer
                                                .println(ServerBankController.fundTransfer(apiContext[1]));
                                    }
                                    else
                                    {
                                        socketControllers.writer
                                                .println("Bank route doesn't support this operation");
                                    }
                                }
                                else
                                {
                                    socketControllers.writer.println("route not found");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Unknown request:" + request + "\nClosing connection.");

                            socketControllers.writer
                                    .println("Unable to parse request.");

                            break;
                        }
                    }
                }
                catch (Exception exception)
                {
                    System.out.println("Server error: unable to process requests.");
                }
            }, "server-reading-client-" + clientCount).start();
        }
        catch (Exception exception)
        {
            System.out.println("Server error: unable to process requests.");
        }
    }

    public static void main(String[] args)
    {
        System.out.println(Utilities.WELCOME);
        new BankServer();
    }
}
