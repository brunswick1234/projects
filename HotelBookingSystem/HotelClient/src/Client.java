import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by dell on 4/7/2015.
 */
public class Client
{


    private Scanner input = null;
    private PrintWriter output = null;
    private Scanner userInput = null;
    private ClientTools clientTools = null;


    public static void main(String[] args)
    {

        if (args.length != 2)
        {
            System.out.println("Invalid arguments, please enter host and port of broker");
            System.exit(1);
        }
        int port = 0;
        try
        {
            port = Integer.parseInt(args[1]);
        } catch (Exception e)
        {
            System.out.println("Invalid argument, please enter a number for port of host");
            System.exit(1);
        }
        new Client().runClient(args[0], port);

    }


    public void runClient(String host, int port)
    {
        InetAddress address = null;
        try
        {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        Socket socket = null;
        try
        {
            socket = new Socket(address, port);
        } catch (IOException e)
        {
            System.out.println("Cannot connect to broker. Please check your input or try again later");
            System.exit(1);
        }

        try
        {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            userInput = new Scanner(System.in);
            clientTools = new ClientTools(userInput);
            do
            {
                do
                {
                    String cityName = chooseCity();
                    String hotelName = chooseHotel();
                    if (hotelName == null)
                        continue;
                    if (checkVacancy(hotelName, cityName) && processToBook())
                        break;
                } while (true);
                book();
                if (!continueOrQuit())
                    break;

            } while (true);

        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                output.println(ClientBrokerProtocol.QUIT);
                System.out.println("client socket close");
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private HashMap<Integer, String> generateChoiceMenu(String list)
    {
        HashMap<Integer, String> choiceCityMap = new HashMap<>();
        String[] entity = list.split(",");
        int choiceId = 1;
        for (String s : entity)
        {
            System.out.println(choiceId + ". " + s);
            choiceCityMap.put(choiceId, s);
            choiceId++;

        }

        return choiceCityMap;
    }


    private String chooseCity()
    {

        output.println(ClientBrokerProtocol.GET_ALL_CITIES);
        String response = input.nextLine();

        if (response.equals(""))
        {
            System.out.println("Sorry,cannot find any city. Please try again latter");
            output.println(ClientBrokerProtocol.QUIT);
            System.exit(1);
            return null;
        } else
        {
            HashMap<Integer, String> choiceCityMap = generateChoiceMenu(response);
            System.out.println("Choose the city you want to stay:");
            int maxChoice = choiceCityMap.keySet().size();
            int choice = clientTools.getChoiceNumberFromUser(maxChoice);
            String cityChoose = choiceCityMap.get(choice);
            output.println(ClientBrokerProtocol.GET_ALL_HOTELS + "_" + cityChoose);
            return cityChoose;
        }

    }


    private String chooseHotel()
    {

        String response = input.nextLine();
       
        if (response.equals(""))
        {
            System.out.println("Sorry,cannot find any hotel. Please try again latter");
            return null;
        } else
        {
            HashMap<Integer, String> choiceHotelMap = generateChoiceMenu(response);
            System.out.println("Choose the hotel you want to stay:");
            int maxChoice = choiceHotelMap.keySet().size();
            int choice = clientTools.getChoiceNumberFromUser(maxChoice);


            String hotelChoose = choiceHotelMap.get(choice);
            return hotelChoose.split("@")[0].trim();
        }

    }


    private boolean checkVacancy(String hotelName, String cityName)
    {

        String checkInDate = clientTools.getDateFromUser();
        String numberOfNights = clientTools.getNumberOfNightsFromUser(checkInDate);
        String numberOfRooms = clientTools.getNumberOfRoomsFromUser();
        output.println(ClientBrokerProtocol.CHECK_VACANCY + "_" + hotelName + "_" + cityName + "_" + checkInDate + "-" + numberOfNights + "-" + numberOfRooms);
        String response = input.nextLine();
        if (response.equals(ClientBrokerProtocol.ROOM_AVAILABLE))
        {
            return true;
        } else if (response.equals(ClientBrokerProtocol.ROOM_NOT_AVAILABLE))
        {
            System.out.println("Sorry, no room is available. Please try another book.");
            return false;
        } else
        {
            System.out.println("Sorry, cannot connect to hotel, please try again later");
            return false;
        }

    }


    private boolean processToBook()
    {
        System.out.println("Room available! Do you want to continue booking this hotel? y/n");
        return clientTools.getYOrNFromUser();
    }


    private void book()
    {
        String name = clientTools.getNameFromUser();
        String phone = clientTools.getPhoneFromUser();
        String email = clientTools.getEmailFromUser();
        String creditCardNumber = clientTools.getCreditCardNumberFromUser();
        output.println(ClientBrokerProtocol.BOOK + "_" + name + "%" + phone + "%" + email + "%" + creditCardNumber);

        String response = input.nextLine();
        String[] stringSplit = response.split("_");
        String[] strings = stringSplit[1].split("%");
        if (stringSplit[0].equals(ClientBrokerProtocol.BOOK_SUCCESS))
        {
            System.out.println();
            System.out.println("You have successfully booked this hotel, and your booking number is " + strings[0]);
            System.out.println("The following is your booking details");
            System.out.println("Hotel Name: "  + strings[1]);
            System.out.println("Check in date:"  + strings[2]);
            System.out.println("Number of nights: "  + strings[3]);
            System.out.println("Number of rooms: "  + strings[4]);
            System.out.println("Customer Name: "  + strings[5]);
            System.out.println("Customer Phone: " + strings[6]);
            System.out.println("Email: " + strings[7]);
            System.out.println("Credit Card: " + strings[8]);
            System.out.println();
                    
        }   
        else
            System.out.println("Sorry, the booking is not successful. Please try again");


    }


    private boolean continueOrQuit()
    {
        System.out.println("Do you want to book another hotel? y/n");
        boolean result = clientTools.getYOrNFromUser();
        if (!result)
        {

            System.out.println("See you next time");
            output.println(ClientBrokerProtocol.QUIT);
        }
        return result;
    }


}
