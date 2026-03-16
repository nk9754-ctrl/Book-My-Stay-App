import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public synchronized void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public synchronized int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public synchronized boolean allocateRoom(String roomType, String guestName) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            System.out.println("Reservation confirmed for " + guestName +
                               " | Room Type: " + roomType +
                               " | Remaining: " + (available - 1));
            return true;
        } else {
            System.out.println("Reservation failed for " + guestName +
                               " | Room Type: " + roomType +
                               " (No availability)");
            return false;
        }
    }
}

class BookingTask implements Runnable {
    private RoomInventory inventory;
    private String guestName;
    private String roomType;

    public BookingTask(RoomInventory inventory, String guestName, String roomType) {
        this.inventory = inventory;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        inventory.allocateRoom(roomType, guestName);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v11.1");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        Thread t1 = new Thread(new BookingTask(inventory, "Alice", "Single Room"));
        Thread t2 = new Thread(new BookingTask(inventory, "Bob", "Single Room"));
        Thread t3 = new Thread(new BookingTask(inventory, "Charlie", "Single Room"));
        Thread t4 = new Thread(new BookingTask(inventory, "Diana", "Double Room"));
        Thread t5 = new Thread(new BookingTask(inventory, "Eve", "Double Room"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("\nFinal Inventory State:");
        System.out.println("Single Room Availability: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room Availability: " + inventory.getAvailability("Double Room"));
    }
}