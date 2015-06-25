import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 4/11/2015.
 */
public class ClientTools
{

    private Scanner userInput;

    public ClientTools(Scanner userInput)
    {
        this.userInput = userInput;
    }



    public String getNameFromUser()
    {
        boolean validInput = true;
        String name = "";
        System.out.println("Please enter your name:");
        name = userInput.nextLine().trim();
        do
        {
            if (!validInput)
            {
                System.out.println("Invalid name, only letter and space is allowed, please enter your name again:");
                name = userInput.nextLine().trim();
            }
            char[] chars = name.toCharArray();
            for (char c : chars)
            {
                if (c != ' ' && !Character.isLetter(c))
                {
                    validInput = false;
                    break;
                }
                else
                {
                    validInput = true;
                }
            }

        }while (!validInput);

        return name;

    }


    public String getPhoneFromUser()
    {
        boolean validInput = true;
        String phone = "";
        System.out.println("Please enter your phone:");
        phone = userInput.nextLine().trim();
        do
        {
            if (!validInput)
            {
                System.out.println("Invalid phone, only number is allowed, please enter a valid phone number:");
                phone = userInput.nextLine().trim();
            }
            if (isNumber(phone))
                break;
            else
                validInput = false;

        }while (true);

        return phone;

    }



    public String getEmailFromUser()
    {
        boolean validInput = true;
        String email = "";
        System.out.println("Please enter your email:");
        email = userInput.nextLine().trim();
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        do
        {
            if (!validInput)
            {
                System.out.println("Invalid email, please enter a valid email:");
                email = userInput.nextLine().trim();
            }


            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches())
                validInput = true;
            else
                validInput = false;

        }while (!validInput);

        return email;

    }




    public String getCreditCardNumberFromUser()
    {
        boolean validInput = true;
        String cardNumber = "";
        System.out.println("Please enter your credit card number: (Example: 79927398713)");
        cardNumber = userInput.nextLine().trim();
        do
        {
            if (!validInput)
            {
                System.out.println("Invalid credit number, please enter a valid phone number:");
                cardNumber = userInput.nextLine().trim();
            }
            if (isNumber(cardNumber))
            {
                int[] digits = new int[cardNumber.length()];
                char[] chars = cardNumber.toCharArray();
                for (int i = 0; i < digits.length; i++)
                {
                    digits[i] = Integer.parseInt(chars[i] + "");
                }
                if (luhn(digits))
                    break;
                else
                    validInput = false;
            }
            else
                validInput = false;

        }while (true);
        return cardNumber;

    }


    public String getDateFromUser()
    {
        System.out.println("Enter check-in date: (Example format :2015/07/01)");
        String dateString = userInput.nextLine().trim();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        boolean isValid = true;
        do
        {
            if (!isValid)
            {
                System.out.println("Invalid date, please enter a valid date between 2015/07/01 and 2015/07/30:");
                dateString = userInput.nextLine().trim();
            }
            try
            {
                Date date = dateFormat.parse(dateString);
                Calendar cal = Calendar.getInstance();
                cal.set(2015, 5,30);
                Date minDate = cal.getTime();
                cal.set(2015,6,30);
                Date maxDate = cal.getTime();
                if (date.before(minDate) || date.after(maxDate))
                    isValid = false;
                else
                    isValid = true;
            } catch (Exception e)
            {
               isValid = false;
            }
        }while (!isValid);

        return dateString;
    }



    public String getNumberOfNightsFromUser(String checkInDate)
    {

        System.out.println("Enter number of nights you want to stay:");
        String numberOfNights = userInput.nextLine().trim();
        boolean isValid = true;
        do
        {
            if (!isValid)
            {
                System.out.println("Invalid input, please enter a number larger than 0, "
                        + "and check-out day cannot later than 2015/07/31");
                numberOfNights = userInput.nextLine().trim();
            }

            if (isNumber(numberOfNights) && Integer.parseInt(numberOfNights) != 0 
                    && checkOutTimeNotOutOfRange(checkInDate,numberOfNights))
                isValid = true;
            else
                isValid = false;

        }while (!isValid);

        return numberOfNights;
    }



    public String getNumberOfRoomsFromUser()
    {
        System.out.println("Enter number of rooms you need:");
        String numberOfRooms = userInput.nextLine().trim();
        boolean isValid = true;
        do
        {
            if (!isValid)
            {
                System.out.println("Invalid input, please enter a number larger than 0:");
                numberOfRooms = userInput.nextLine().trim();
            }

            if (isNumber(numberOfRooms) && Integer.parseInt(numberOfRooms) != 0)
                isValid = true;
            else
                isValid = false;

        }while (!isValid);

        return numberOfRooms;
    }


    private boolean isNumber(String string)
    {
        char[] chars = string.toCharArray();
        for (char c : chars)
        {
            if (!Character.isDigit(c))
            {
                return false;
            }

        }
        return true;
    }


    public int getChoiceNumberFromUser(int maxChoice)
    {
        String choice = userInput.nextLine().trim();
        boolean isValid = true;
        do
        {
            if (!isValid)
            {
                System.out.println("Invalid choice, please enter a number between 1 and " + maxChoice + ":");
                choice = userInput.nextLine().trim();
            }

            if (isNumber(choice) && Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= maxChoice)
                isValid = true;
            else
                isValid = false;

        }while (!isValid);

        return Integer.parseInt(choice);

    }


    private boolean checkOutTimeNotOutOfRange(String checkInDate, String numberOfNights)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;

        try
        {
            date = dateFormat.parse(checkInDate);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.set(2015,6,31);
        Date maxDate = cal.getTime();
        long difference = maxDate.getTime() - date.getTime();
        long daysDifference = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);
        if (daysDifference >= Integer.parseInt(numberOfNights))
            return true;
        else
            return false;
    }


    public boolean getYOrNFromUser()
    {
        String answer = userInput.nextLine().trim().toLowerCase();
        boolean isValid = true;

        do
        {
            if (!isValid)
            {
                System.out.println("Invalid input, please enter y or n");
                answer = userInput.nextLine().trim().toLowerCase();
            }
            if (answer.equals("y") || answer.equals("n"))
                isValid = true;
            else
                isValid = false;
        } while (!isValid);

        if (answer.equals("y"))
            return true;
        else
            return false;
    }


    //This method for validating credit card number using Luhn algorithm is from http://de.wikipedia.org/wiki/Luhn-Algorithmus#Java
    private boolean luhn(int[] digits)
    {
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {

            // get digits in reverse order
            int digit = digits[length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }





}
