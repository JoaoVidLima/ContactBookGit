package contactBook;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * It's the system interface that manages all the contacts from the application.
 * @author António Mendes 66891 an.mendes@campus.fct.unl.pt
 * @author Bernardo Gracioso 65284 b.gracioso@campus.fct.unl.pt
 * @author João Lima 65491 jav.lima@campus.fct.unl.pt
 */
public class ContactBook {
    private static final int DEFAULT_SIZE = 100;

    private int counter;
    private Contact[] contacts;
    private int currentContact;

    /**
     * Initializes all the variables. In the beginning there are no contacts (so counter is 0),
     * and we are not iterating yet, so currentContact is -1.
     */
    public ContactBook() {
        counter = 0;
        contacts = new Contact[DEFAULT_SIZE];
        currentContact = -1;
    }

    /**
     * Evaluates if there is a contact with the given name.
     * @pre: name != null
     * @param name - the given contact name.
     * @return true if there is a contact with the given name, false otherwise.
     */
    public boolean hasContact(String name) {
        return searchIndex(name) >= 0;
    }

    /**
     * Evaluates if there is a contact with the given phone number.
     * @param number - the given phone number.
     * @return true if there is a contact with the given phone number, false otherwise.
     */
    public boolean hasContact(int number) {
    	return searchIndex(number) >= 0;
    }

    /**
     * Returns the number of contacts registered at the moment.
     * @return the number of contacts registered at the moment.
     */
    public int getNumberOfContacts() {
        return counter;
    }

    /**
     * Adds a new contact in the system with the given arguments, if none has the given name.
     * @pre name != null && !hasContact(name)
     * @param name - the given contact name.
     * @param phone - the given phone number.
     * @param email - the given contact email.
     */
    public void addContact(String name, int phone, String email) {
        if (counter == contacts.length)
            resize();
        contacts[counter] = new Contact(name, phone, email);
        counter++;
    }

    /**
     * Deletes a contact in the system with the given name (it's identifier), if there is a contact with such a name.
     * @pre name != null && hasContact(name)
     * @param name - the given contact name.
     */
    public void deleteContact(String name) {
        int index = searchIndex(name);
        for(int i=index; i<counter; i++)
            contacts[i] = contacts[i+1];
        counter--;
    }

    /**
     * Returns the phone number of the contact with the given name, if there is a contact with such a name.
     * @pre name != null && hasContact(name)
     * @param name - the given contact name.
     * @return the phone number of the contact with the given name, if there is a contact with such a name.
     */
    public int getPhone(String name) {
        return contacts[searchIndex(name)].getPhone();
    }

    /**
     * Returns the email of the contact with the given name, if there is a contact with such a name.
     * @pre name != null && hasContact(name)
     * @param name - the given contact name.
     * @return the email of the contact with the given name, if there is a contact with such a name.
     */
    public String getEmail(String name) {
        return contacts[searchIndex(name)].getEmail();
    }

    /**
     * Returns the name of the oldest contact with the given phone number,
     * if there is a contact with such a phone number.
     * @pre hasContact(number)
     * @param number - the given phone number.
     * @return the name of the oldest contact with the given phone number,
     * if there is a contact with such a phone number.
     */
    public String getName(int number) {
    	return contacts[searchIndex(number)].getName();
    }

    /**
     * Sets the given phone number to the contact with the given name, if there is a contact with such a name.
     * @pre name != null && hasContact(name)
     * @param name - the given contact name.
     * @param phone - the given phone number.
     */
    public void setPhone(String name, int phone) {
        contacts[searchIndex(name)].setPhone(phone);
    }

    /**
     * Sets the given email to the contact with the given name, if there is a contact with such a name.
     * @pre name != null && hasContact(name)
     * @param name - the given contact name.
     * @param email - the given contact email.
     */
    public void setEmail(String name, String email) {
        contacts[searchIndex(name)].setEmail(email);
    }

    /**
     * Using a predicate to factor the search index (for all criteria wanted).
     * @pre predicate != null, and predicate must evaluate a contact.
     * @param predicate - the predicate used, takes a contact and evaluates as wanted.
     * @return the index of the contact found, or -1 if not found.
     */
    private int searchIndex(Predicate<Contact> predicate) {
        int i = 0;
        int result = -1;
        boolean found = false;
        while (i<counter && !found)
            if (predicate.test(contacts[i]))
                found = true;
            else
                i++;
        if (found) result = i;
        return result;
    }

    /**
     * Returns the index of the contact with the given name, or -1 if not found.
     * @pre name != null
     * @param name - the given contact name.
     * @return the index of the contact with the given name, or -1 if not found.
     */
    private int searchIndex(String name) {
        return searchIndex(contact -> name.equals(contact.getName()));
    }

    /**
     * Returns the index of the oldest contact with the given phone number, or -1 if not found.
     * @param number - the given phone number.
     * @return the index of the oldest contact with the given phone number, or -1 if not found.
     */
    private int searchIndex(int number) {
        return searchIndex(contact -> number == contact.getPhone());
    }

    /**
     * Auxiliary method to help the contacts array grow when needed.
     */
    private void resize() {
        Contact tmp[] = new Contact[2*contacts.length];
        for (int i=0;i<counter; i++)
            tmp[i] = contacts[i];
        contacts = tmp;
    }

    /**
     * Initializes the iterator, such that the first contact to iterate is the oldest one.
     */
    public void initializeIterator() {
        currentContact = 0;
    }

    /**
     * Evaluates if there is at least one more contact to iterate.
     * @return true if there is at least one more contact to iterate, false otherwise.
     */
    public boolean hasNext() {
        return (currentContact >= 0 ) && (currentContact < counter);
    }

    /**
     * Returns the next contact to iterate.
     * @pre hasNext()
     * @return the next Contact object to iterate.
     */
    public Contact next() {
        return contacts[currentContact++];
    }

    /**
     * Iterates all the contacts of this cBook and uses a HashSet to check in constant time if the current phone number
     * being iterated is already on the set. In this case, a previously iterated contact has the same phone number,
     * which means there are at least two contacts with the same phone number (no need to keep iterating),
     * and returns true.
     * Otherwise, if we had to iterate through every phone number, then all phone numbers are different,
     * and returns false.
     */
    public boolean hasEqualPhones() {
        Set<Integer> phoneNumberFrequency = new HashSet<>();
        int currentPhoneNumber;

        initializeIterator();
        while (hasNext()) {
            currentPhoneNumber = next().getPhone();
            if (!phoneNumberFrequency.add(currentPhoneNumber))
                return true;
        }

        return false;
    }
}
