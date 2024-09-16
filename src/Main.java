import contactBook.Contact;
import contactBook.ContactBook;

import java.util.Scanner;

public class Main {
    //Constants representing every command of the app
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

    // Constants that define the all the possible output messages
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

    /**
     * The main method of our application, allows the user to interact with
     * the program by line commands.
     * @param args - possible outside arguments given to the program
     */
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

    /**
     * Gets the line command from the user and convert it to uppercase to
     * later possibly match one of the possible commands of the app.
     * @param in - the Scanner object used get the user input.
     * @return the user input in uppercase.
     */
    private static String getCommand(Scanner in) {
        String input;

        input = in.nextLine().toUpperCase();
        return input;
    }

    /**
     * Asks for all the information necessary to create a contact and tries to
     * add it to the cBook. If this person already exists in the cBook, the
     * contact won't be added.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
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

    /**
     * Deletes the contact with the name given from the cBook, if the contact
     * exists in the cBook.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
    private static void deleteContact(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.deleteContact(name);
            System.out.println(CONTACT_REMOVED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    /**
     * Gets the phone number of the contact with the given name. The contact
     * must exist in the cBook.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
    private static void getPhone(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getPhone(name));
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    /**
     * Gets the email of the contact with the given name. The contact
     * must exist in the cBook.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
    private static void getEmail(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getEmail(name));
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    /**
     * Updates the phone number of the contact with the given name. The contact
     * must exist in the cBook.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
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

    /**
     * Updates the email of the person with the given name. The contact
     * must exist in the cBook.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
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

    /**
     * List all the contact from the cBook from the first contact added
     * to the most recent one added.
     * @param cBook - the ContactBook object instance used by the app.
     */
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

    /**
     * Prints the name of the contact with the given phone number.
     * In case there are many with the same phone number, the oldest contact
     * is the one printed.
     * @param in - the Scanner object used get the user input.
     * @param cBook - the ContactBook object instance used by the app.
     */
    private static void getName(Scanner in, ContactBook cBook) {
    	int number;
    	number = in.nextInt(); in.nextLine();
    	if (cBook.hasContact(number)) {
    		System.out.println(cBook.getName(number));
    	}
    	else System.out.println(PHONE_NUMBER_DOESNT_EXIST);
    }

    /**
     * Print if there are contacts with the same phone number in the cBook.
     * @param cBook - the ContactBook object instance used by the app.
     */
    private static void checkEqualPhones(ContactBook cBook) {
        if(!cBook.hasEqualPhones())
            System.out.println(ALL_DIFFERENT_PHONE_NUMBERS);
        else
            System.out.println(EQUAL_PHONE_NUMBERS);
    }
}
