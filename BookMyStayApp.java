import java.util.HashMap;

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newAvailability) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newAvailability);
        }
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " | Available: " + inventory.get(roomType));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v3.1");

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        inventory.displayInventory();

        inventory.updateAvailability("Double Room", 2);

        System.out.println("\nAfter Update:");
        inventory.displayInventory();
    }
}