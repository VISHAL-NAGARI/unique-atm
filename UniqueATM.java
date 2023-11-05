import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn: $" + amount);
            return true;
        }
        return false;
    }

    public boolean transfer(User recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
            return true;
        }
        return false;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }
}

public class UniqueATM{
    private ArrayList<User> users;
    private User currentUser;
    private Scanner scanner;

    public UniqueATM() {
        this.users = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.next();
            System.out.print("Enter PIN: ");
            String pin = scanner.next();

            User user = authenticateUser(userId, pin);

            if (user == null) {
                System.out.println("Invalid User ID or PIN. Please try again.");
            } else {
                currentUser = user;
                showMenu();
            }
        }
    }

    private User authenticateUser(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }

    private void showMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Quit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: $" + currentUser.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    System.out.println("Deposited: $" + depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawalAmount = scanner.nextDouble();
                    if (currentUser.withdraw(withdrawalAmount)) {
                        System.out.println("Withdrawn: $" + withdrawalAmount);
                    } else {
                        System.out.println("Insufficient funds or invalid amount.");
                    }
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientUserId = scanner.next();
                    User recipient = authenticateUser(recipientUserId, "");
                    if (recipient != null) {
                        System.out.print("Enter transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        if (currentUser.transfer(recipient, transferAmount)) {
                            System.out.println("Transferred: $" + transferAmount + " to " + recipient.getUserId());
                        } else {
                            System.out.println("Insufficient funds or invalid amount.");
                        }
                    } else {
                        System.out.println("Recipient not found. Please try again.");
                    }
                    break;
                case 5:
                    ArrayList<String> transactionHistory = currentUser.getTransactionHistory();
                    System.out.println("Transaction History:");
                    for (String transaction : transactionHistory) {
                        System.out.println(transaction);
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        UniqueATM atm = new UniqueATM();

        // Simulate some user accounts
        User user1 = new User("12345", "5005");
        User user2 = new User("95959", "9945");
        atm.users.add(user1);
        atm.users.add(user2);

        atm.run();
    }
}
