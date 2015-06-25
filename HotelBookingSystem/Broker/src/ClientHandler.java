import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by dell on 4/7/2015.
 */
public class ClientHandler extends Thread
{
    private Socket client;
    private Scanner inputFromClient;
    private PrintWriter outputToClient;
    private Socket clientToServer;
    private Scanner inputFromServer;
    private PrintWriter outputToServer;
    private HashMap<String,String[]> hotelAddress;

    public ClientHandler(Socket client, HashMap<String,String[]> hotelAddress )
    {
        this.client = client;
        this.hotelAddress = hotelAddress;

        try
        {
            inputFromClient = new Scanner(client.getInputStream());
            outputToClient = new PrintWriter(client.getOutputStream(),true);
        } catch (IOException e)
        {
            e.printStackTrace();
        }






    }


    @Override
    public void run()
    {

        String messageFromClient ="";
        do
        {
            messageFromClient = inputFromClient.nextLine();

            System.out.println(messageFromClient);
            String[] message = messageFromClient.split("_");


            switch (message[0])
            {
                case ClientBrokerProtocol.GET_ALL_CITIES:
                    getCitiesList();break;

                case ClientBrokerProtocol.GET_ALL_HOTELS:
                    getAllHotelsInACity(message[1]); break;

                case ClientBrokerProtocol.CHECK_VACANCY:
                    checkVacancy(message[1], message[2], message[3]);break;

                case ClientBrokerProtocol.BOOK:
                    book(message[1]);break;

                case ClientBrokerProtocol.QUIT:
                {
                    if (outputToServer != null)
                    {
                        outputToServer.println(BrokerServerProtocol.QUIT);
                        break;
                    }
                }

                default:break;

            }
        }while (!messageFromClient.equals(ClientBrokerProtocol.QUIT));


        try
        {
            client.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }





    }






    private void getCitiesList()
    {
        String citiesList = "";
        Set<String> citiesSet = new HashSet<>();
        for(String[] address: hotelAddress.values())
        {
            InetAddress host = null;
            try
            {
                host = InetAddress.getByName(address[0]);
            } catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            Socket socket = null;
            try
            {
                socket = new Socket(host, Integer.parseInt(address[1]));
            } catch (IOException e)
            {
                System.out.println("Cannot connect to " + address[0] + " " + address[1]);
                continue;
            }

            try
            {
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
                output.println(BrokerServerProtocol.GET_ALL_CITIES);
                citiesSet.addAll(stringToSet(input.nextLine()));
                output.println(BrokerServerProtocol.QUIT);

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            finally
            {
                try
                {

                    socket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }


        }
        citiesList = setToString(citiesSet);

        outputToClient.println(citiesList);
    }




    private void getAllHotelsInACity(String city)
    {

        String hotelList = "";
        for(String[] address: hotelAddress.values())
        {
            InetAddress host = null;
            try
            {
                host = InetAddress.getByName(address[0]);
            } catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            Socket socket = null;
            try
            {
                socket = new Socket(host, Integer.parseInt(address[1]));
            } catch (IOException e)
            {
                System.out.println("Cannot connect to " + address[0] + " " + address[1]);
                continue;
            }

            try
            {
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
                output.println(BrokerServerProtocol.GET_HOTEL + "_" + city);
                String response = input.nextLine();
                if (!response.equals(BrokerServerProtocol.NO_HOTEL))
                    hotelList = hotelList + response + ",";
                output.println(BrokerServerProtocol.QUIT);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            finally
            {
                try
                {
                    socket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }
       outputToClient.println(hotelList);
    }


    private Set<String> stringToSet(String list)
    {
        String[] strings = list.split(",");
        Set<String> stringSet = new HashSet<>(Arrays.asList(strings));
        return stringSet;
    }


    private String setToString(Set<String> stringSet)
    {
        String result = "";
        for (String s : stringSet)
        {
            result = result + s + ",";
        }
        return result;

    }





    private void checkVacancy(String hotel,String city,String queryInfo)
    {

        String[] hostAndPort = hotelAddress.get(hotel);

        InetAddress address = null;
        try
        {
            address = InetAddress.getByName(hostAndPort[0]);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        try
        {
            clientToServer = new Socket(address, Integer.parseInt(hostAndPort[1]));
        } catch (IOException e)
        {
            outputToClient.println(ClientBrokerProtocol.CANNOT_CONNECT_SERVER);
            return;
        }

        try
        {
            inputFromServer = new Scanner(clientToServer.getInputStream());
            outputToServer = new PrintWriter(clientToServer.getOutputStream(),true);
            outputToServer.println(BrokerServerProtocol.CHECK_VACANCY  + "_" + city + "_" + queryInfo);
            String response = inputFromServer.nextLine();
            if (response.equals(BrokerServerProtocol.ROOM_AVAILABLE))
                outputToClient.println(ClientBrokerProtocol.ROOM_AVAILABLE);
            else
                outputToClient.println(ClientBrokerProtocol.ROOM_NOT_AVAILABLE);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    private void book(String CustomerInfo)
    {
        outputToServer.println(BrokerServerProtocol.BOOK + "_" + CustomerInfo);
        String response = "";
        try
        {
            response = inputFromServer.nextLine();
        }catch (NoSuchElementException e)
        {
            outputToClient.println(ClientBrokerProtocol.BOOK_FAIL);
            return;
        }
        String[] strings = response.split("_");
        if (strings[0].equals(BrokerServerProtocol.BOOK_SUCCESS))
            outputToClient.println(ClientBrokerProtocol.BOOK_SUCCESS + "_" + strings[1]);
        else
            outputToClient.println(ClientBrokerProtocol.BOOK_FAIL);
    }




}
