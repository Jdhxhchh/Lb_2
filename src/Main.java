import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // Створіть об'єкти для книг і DVD
        Book book1 = new Book("Book 1", "B1-001", "Author A");
        DVD dvd1 = new DVD("Movie 1", "D1-001", 120);

        // Створіть об'єкти для читачів
        Patron patron1 = new Patron("John", "P1-001");
        Patron patron2 = new Patron("Alice", "P1-002");

        // Створіть бібліотеку
        Library library = new Library();

        // Додайте предмети до бібліотеки
        library.add(book1);
        library.add(dvd1);

        // Зареєструйте читачів
        library.registerPatron(patron1);
        library.registerPatron(patron2);

        // Видайте предмети читачам
        library.lendItem(patron1, book1);
        library.lendItem(patron2, dvd1);

        // Покажіть список доступних предметів
        System.out.println("Available items:");
        library.listAvailable();

        // Покажіть список взятих предметів та їхніх читачів
        System.out.println("Borrowed items:");
        library.listBorrowed();

        // Поверніть предмети в бібліотеку
        library.returnItem(patron1, book1);
        library.returnItem(patron2, dvd1);

        // Покажіть знову список доступних предметів
        System.out.println("Available items after returns:");
        library.listAvailable();
    }
}

abstract class Item {
    private String title;
    private String uniqueID;
    private boolean isBorrowed;

    public Item(String title, String uniqueID) {
        this.title = title;
        this.uniqueID = uniqueID;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowItem() {
        isBorrowed = true;
    }

    public void returnItem() {
        isBorrowed = false;
    }
}

class Book extends Item {
    private String author;

    public Book(String title, String uniqueID, String author) {
        super(title, uniqueID);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}

class DVD extends Item {
    private int duration;

    public DVD(String title, String uniqueID, int duration) {
        super(title, uniqueID);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}

class Patron {
    private String name;
    private String ID;
    private List<Item> borrowedItems;

    public Patron(String name, String ID) {
        this.name = name;
        this.ID = ID;
        this.borrowedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public void borrow(Item item) {
        borrowedItems.add(item);
        item.borrowItem();
    }

    public void returnItem(Item item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}

interface IManageable {
    void add(Item item);
    void remove(Item item);
    void listAvailable();
    void listBorrowed();
}

class Library implements IManageable {
    private List<Item> items;
    private List<Patron> patrons;

    public Library() {
        items = new ArrayList<>();
        patrons = new ArrayList<>();
    }

    public void registerPatron(Patron patron) {
        patrons.add(patron);
    }

    public void lendItem(Patron patron, Item item) {
        if (!item.isBorrowed() && patrons.contains(patron)) {
            patron.borrow(item);
        }
    }

    public void returnItem(Patron patron, Item item) {
        if (patrons.contains(patron) && patron.getBorrowedItems().contains(item)) {
            patron.returnItem(item);
        }
    }

    @Override
    public void add(Item item) {
        items.add(item);
    }

    @Override
    public void remove(Item item) {
        items.remove(item);
    }

    @Override
    public void listAvailable() {
        for (Item item : items) {
            if (!item.isBorrowed()) {
                System.out.println("Available: " + item.getTitle());
            }
        }
    }

    @Override
    public void listBorrowed() {
        for (Patron patron : patrons) {
            for (Item item : patron.getBorrowedItems()) {
                System.out.println(patron.getName() + " has borrowed: " + item.getTitle());
            }
        }
    }
}
