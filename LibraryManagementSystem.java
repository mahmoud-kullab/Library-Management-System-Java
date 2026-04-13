import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

// 1. Book Class: Represents an individual book object
class Book {
    private int id;
    private String title;
    private String status;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
        this.status = "available";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("   %-5d | %-25s | %-10s", id, title, status);
    }
}

// 2. Library Class: Manages the collection of books
class Library {
    private ArrayList<Book> bookList = new ArrayList<>();
    private int idCounter = 1;

    public void addBook(String title) {
        bookList.add(new Book(idCounter++, title));
        System.out.println("\n[Success] Book added: " + title);
    }

    public void showAllBooks() {
        System.out.println("\n=========================================================");
        System.out.println("   ID    | Book Title                | Status");
        System.out.println("=========================================================");

        if (bookList.isEmpty()) {
            System.out.println("   The library is currently empty.");
        } else {
            for (Book book : bookList) {
                System.out.println(book.toString());
            }
        }
        System.out.println("=========================================================");
    }

    public void searchBook(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("\n[Match Found]");
                System.out.println("ID: " + book.getId() + " | Title: " + book.getTitle() + " | Status: " + book.getStatus());
                return;
            }
        }
        System.out.println("\n[Error] '" + title + "' not found.");
    }

    public void borrowBook(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.getStatus().equals("available")) {
                    book.setStatus("on loan");
                    System.out.println("\n[Success] You have borrowed: " + title);
                } else {
                    System.out.println("\n[Denied] '" + title + "' is already on loan.");
                }
                return;
            }
        }
        System.out.println("\n[Error] Book not found.");
    }

    public void returnBook(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.getStatus().equals("on loan")) {
                    book.setStatus("available");
                    System.out.println("\n[Success] '" + title + "' has been returned.");
                } else {
                    System.out.println("\n[Info] This book was already available.");
                }
                return;
            }
        }
        System.out.println("\n[Error] Invalid book name.");
    }

    public void deleteBook(String title) {
        boolean removed = bookList.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        if (removed) {
            System.out.println("\n[Success] Book deleted from system.");
            reindexBooks();
        } else {
            System.out.println("\n[Error] Could not find '" + title + "' to delete.");
        }
    }

    private void reindexBooks() {
        idCounter = 1;
        for (Book book : bookList) {
            book.setId(idCounter++);
        }
    }
}

// 3. Main Application Class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Library myLibrary = new Library();

        // Sample Data
        myLibrary.addBook("Java Programming");
        myLibrary.addBook("Data Structures");
        myLibrary.addBook("Web Development");
        myLibrary.addBook("Cybersecurity Essentials");

        int choice = 0;
        while (choice != 7) {
            System.out.println("\n>>> Library Management System <<<");
            System.out.println("1. Display All Books");
            System.out.println("2. Search for a Book");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Add New Book");
            System.out.println("6. Remove a Book");
            System.out.println("7. Exit System");
            System.out.print("Select Option: ");

            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\n[Invalid Input] Please enter a number between 1-7.");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1: myLibrary.showAllBooks(); break;
                case 2:
                    System.out.print("Enter book title to search: ");
                    myLibrary.searchBook(input.nextLine());
                    break;
                case 3:
                    System.out.print("Enter title to borrow: ");
                    myLibrary.borrowBook(input.nextLine());
                    break;
                case 4:
                    System.out.print("Enter title to return: ");
                    myLibrary.returnBook(input.nextLine());
                    break;
                case 5:
                    System.out.print("Enter new book title: ");
                    myLibrary.addBook(input.nextLine());
                    break;
                case 6:
                    System.out.print("Enter title to delete: ");
                    myLibrary.deleteBook(input.nextLine());
                    break;
                case 7:
                    System.out.println("\nSystem closed. Goodbye!");
                    break;
                default:
                    System.out.println("\n[Error] Please choose a valid option.");
            }
        }
        input.close();
    }
}
