import contactBook.Contact;
import contactBook.ContactBook;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    //Constantes que definem os comandos
    public static final String ADD_CONTACT    = "AC";
    public static final String REMOVE_CONTACT = "RC";
    public static final String GET_PHONE      = "GP";
    public static final String GET_EMAIL      = "GE";
    public static final String SET_PHONE      = "SP";
    public static final String SET_EMAIL      = "SE";
    public static final String LIST_CONTACTS  = "LC";
    public static final String GET_NAME = "GN";
    public static final String EQUAL_PHONES = "EP";
    public static final String QUIT           = "Q";

    //Constantes que definem as mensagens para o utilizador
    public static final String CONTACT_EXISTS = "contactBook.Contact already exists.";
    public static final String NAME_NOT_EXIST = "contactBook.Contact does not exist.";
    public static final String CONTACT_ADDED = "contactBook.Contact added.";
    public static final String CONTACT_REMOVED = "contactBook.Contact removed.";
    public static final String CONTACT_UPDATED = "contactBook.Contact updated.";
    public static final String BOOK_EMPTY = "contactBook.Contact book empty.";
    public static final String PHONE_NUMBER_DOESNT_EXIST = "Phone number does not exist.";
    public static final String EQUAL_PHONE_NUMBERS = "There are contacts that share phone numbers.";
    public static final String ALL_DIFFERENT_PHONE_NUMBERS = "All contacts have different phone numbers.";
    public static final String QUIT_MSG = "Goodbye!";
    public static final String COMMAND_ERROR = "Unknown command.";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ContactBook cBook = new ContactBook();
        String comm = getCommand(in);

        while (!comm.equals(QUIT)){
            switch (comm) {
                case ADD_CONTACT 	-> addContact(in,cBook);
                case REMOVE_CONTACT -> deleteContact(in,cBook);
                case GET_PHONE		-> getPhone(in,cBook);
                case GET_EMAIL 		-> getEmail(in,cBook);
                case SET_PHONE		-> setPhone(in,cBook);
                case SET_EMAIL		-> setEmail(in,cBook);
                case LIST_CONTACTS	-> listAllContacts(cBook);
                case GET_NAME 		-> getName(in, cBook);
                case EQUAL_PHONES -> checkEqualPhones(cBook);
                default ->	System.out.println(COMMAND_ERROR);
            }
            System.out.println();
            comm = getCommand(in);
        }
        System.out.println(QUIT_MSG);
        System.out.println();
        in.close();
    }

    private static String getCommand(Scanner in) {
        String input;

        input = in.nextLine().toUpperCase();
        return input;
    }

    private static void addContact(Scanner in, ContactBook cBook) {
        String name, email;
        int phone;

        name = in.nextLine();
        phone = in.nextInt(); in.nextLine();
        email = in.nextLine();
        if (!cBook.hasContact(name)) {
            cBook.addContact(name, phone, email);
            System.out.println(CONTACT_ADDED);
        }
        else System.out.println(CONTACT_EXISTS);
    }

    private static void deleteContact(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.deleteContact(name);
            System.out.println(CONTACT_REMOVED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void getPhone(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getPhone(name));
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void getEmail(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getEmail(name));
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void setPhone(Scanner in, ContactBook cBook) {
        String name;
        int phone;
        name = in.nextLine();
        phone = in.nextInt(); in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.setPhone(name,phone);
            System.out.println(CONTACT_UPDATED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void setEmail(Scanner in, ContactBook cBook) {
        String name;
        String email;
        name = in.nextLine();
        email = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.setEmail(name,email);
            System.out.println(CONTACT_UPDATED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void listAllContacts(ContactBook cBook) {
        if (cBook.getNumberOfContacts() != 0) {
            cBook.initializeIterator();
            while( cBook.hasNext() ) {
                Contact c = cBook.next();
                System.out.println(c.getName() + "; " + c.getEmail() + "; " + c.getPhone());
            }
        }
        else System.out.println(BOOK_EMPTY);
    }
    
    private static void getName(Scanner in, ContactBook cBook) {
    	int number;
    	number = in.nextInt(); in.nextLine();
    	if (cBook.hasContact(number)) {
    		System.out.println(cBook.getName(number));
    	}
    	else System.out.println(PHONE_NUMBER_DOESNT_EXIST);
    }

    //TODO Testar método!
    /**
     * Iterates all the contacts of the cBook and uses a hashMap to check in constant time if the current phone number
     * beeing iterated is already on the hashMap, in that case a previously iterated contact have the same phone number,
     * which means there are at least two contacts with the same phone number (no need to keep iterating).
     * Otherwise, if we had to iterate through every phone number then all phone numbers are different.
     * @param cBook - the ContackBook object.
     */
    private static void checkEqualPhones(ContactBook cBook) {
        //Key: phoneNumber  Value: Number of times the phone number is found in the ContackBook
        Map<Integer, Integer> phoneNumberFrequency = new HashMap<>();

        boolean found = false;
        int currentPhoneNumber;

        cBook.initializeIterator();
        while(cBook.hasNext() && !found){
            currentPhoneNumber = cBook.next().getPhone();
            if(!phoneNumberFrequency.containsKey(currentPhoneNumber))
                phoneNumberFrequency.put(currentPhoneNumber, 1);
            else
                found = true;
        }

        if(!cBook.hasNext() && !found)
            System.out.println(ALL_DIFFERENT_PHONE_NUMBERS);
        else
            System.out.println(EQUAL_PHONE_NUMBERS);
    }


}
