
import Repository.AvailabilityRepository;
import Repository.BookingRepository;
import Repository.CustomerRepository;
import Repository.HotelRepository;
import entity.Availability;
import entity.Booking;
import entity.Customer;
import entity.Hotel;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by dell on 4/8/2015.
 */
public class BrokerHandler extends Thread
{

    private final String hotelName;
    private final Socket socket;
    private Scanner input;
    private PrintWriter output;
    private HotelRepository hotelRepository;
    private AvailabilityRepository availabilityRepository;
    private CustomerRepository customerRepository;
    private BookingRepository bookingRepository;
    private int hotelId;
    private Date checkInDate;
    private int numberOfNights;
    private int numberOfRooms;
    private final String puName;

    public BrokerHandler(Socket socket, String hotelName, String puName)
    {
        this.socket = socket;
        this.hotelName = hotelName;
        this.puName = puName;
        hotelRepository = new HotelRepository(puName);
        availabilityRepository = new AvailabilityRepository(puName);
        customerRepository = new CustomerRepository(puName);
        bookingRepository = new BookingRepository(puName);
        try
        {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String messageFromBroker;
        do
        {
            messageFromBroker = input.nextLine();
            String[] message = messageFromBroker.split("_");
            System.out.println(message[0]);

            switch (message[0])
            {
                case BrokerServerProtocol.GET_ALL_CITIES:
                    output.println(getCitiesList());
                    break;
                case BrokerServerProtocol.GET_HOTEL:
                    output.println(getHotel(message[1]));
                    break;
                case BrokerServerProtocol.CHECK_VACANCY:
                    output.println(handleCheckVacancy(message[1], message[2]));
                    break;
                case BrokerServerProtocol.BOOK:
                    book(message[1]);
                    break;
                case BrokerServerProtocol.QUIT:
                    break;
                default:
                    break;

            }
            System.out.println("message received " + messageFromBroker);
        } while (!messageFromBroker.equals(BrokerServerProtocol.QUIT));

        try
        {
            socket.close();
            System.out.println(hotelName + " server socket connection close");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getCitiesList()
    {
        String citiesList = "";
        Set<String> citiesSet = hotelRepository.findAllCities();
        for (String city : citiesSet)
        {
            citiesList = citiesList + city + ",";
        }

        return citiesList;
    }

    private String getHotel(String city)
    {

        Hotel hotel = hotelRepository.findByCity(city);
        if (hotel != null)
        {
            return hotelName + " @ " + hotel.getRate();
        } else
        {
            return BrokerServerProtocol.NO_HOTEL;
        }
    }

    private String handleCheckVacancy(String city, String queryInfo)
    {
        String[] strings = queryInfo.split("-");
        checkInDate = stringConvertToDate(strings[0]);
        Date date = stringConvertToDate(strings[0]);
        numberOfNights = Integer.parseInt(strings[1]);
        numberOfRooms = Integer.parseInt(strings[2]);
        hotelId = getHotelId(city);

        if (checkVancy(hotelId, date, numberOfNights, numberOfRooms))
        {
            return BrokerServerProtocol.ROOM_AVAILABLE;
        } else
        {
            return BrokerServerProtocol.ROOM_NOT_AVAILABLE;
        }

    }

    private int getHotelId(String city)
    {
        Hotel hotel = hotelRepository.findByCity(city);
        return hotel.getHotelId();
    }

    private Date stringConvertToDate(String string)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(string);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;

    }

    private Date increaseDate(Date date, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    private synchronized void book(String customerInfo)
    {
        if (!checkVancy(hotelId, checkInDate, numberOfNights, numberOfRooms))
        {
           
            output.println(BrokerServerProtocol.BOOK_FAIL);
        } else
        {
            String[] strings = customerInfo.split("%");
            System.out.println(customerInfo);
            int customerId = customerRepository.getNewCstomerId();
            Customer customer = new Customer(customerId,strings[0], strings[1], strings[2], strings[3]);
            int bookingId = bookingRepository.getNewBookingId();
            Hotel hotel = hotelRepository.findByHotelId(hotelId);
            Booking booking = new Booking(bookingId, checkInDate, increaseDate(checkInDate, numberOfNights),
                    numberOfRooms, hotel, customer);

            customerRepository.addCustomer(customer);
            changeAvailability();
            bookingRepository.addBooking(booking);
            String checkInDateString = new SimpleDateFormat("yyyy/MM/dd").format(checkInDate);
            output.println(BrokerServerProtocol.BOOK_SUCCESS + "_" + bookingId + "%" + hotelName + "%" + checkInDateString + "%" 
                    + numberOfNights + "%" + numberOfRooms + "%" + strings[0] + "%" + strings[1] + "%" +
                    strings[2] + "%" + strings[3]);
        }

    }

    private synchronized void changeAvailability()
    {
        Date date = checkInDate;
        for (int i = 1; i <= numberOfNights; i++)
        {
            Availability result = availabilityRepository.findByHotelIdAndDate(hotelId, date);
            int roomAvailable = result.getRoomAvailable();
            result.setRoomAvailable(roomAvailable - numberOfRooms);
            availabilityRepository.updateAvailabillity(result);
            date = increaseDate(date, 1);
        }

    }

    private boolean checkVancy(int hotelId, Date date, int numberOfNights, int numberRooms)
    {

        AvailabilityRepository availabilityRepository2 = new AvailabilityRepository(puName);
        for (int i = 1; i <= numberOfNights; i++)
        {
            Availability result = availabilityRepository2.findByHotelIdAndDate(hotelId, date);
            
            if (result.getRoomAvailable() < numberRooms)
            {
                
                return false;
            }
            date = increaseDate(date, 1);

        }
        return true;
    }

}
