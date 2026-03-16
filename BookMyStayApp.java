import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;
    private String roomId;
    private boolean active;

    public Reservation(String guestName, String roomType, String reservationId, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.active = true;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        this.active = false;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void restoreRoom(String roomType) {
        int available = getAvailability(roomType);
        inventory.put(roomType, available + 1);
    }
}

class CancellationService {
    private RoomInventory inventory;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelReservation(Reservation reservation) {
        if (reservation == null || !reservation.isActive()) {
            System.out.println("Cancellation failed: Reservation invalid or already cancelled.");
            return;
        }
        reservation.cancel();
        rollbackStack.push(reservation.getRoomId());
        inventory.restoreRoom(reservation.getRoomType());
        System.out.println("Reservation cancelled for " + reservation.getGuestName() +
                           " | Room Type: " + reservation.getRoomType() +
                           " | Room ID: " + reservation.getRoomId());
    }

    public void displayRollbackHistory() {
        System.out.println("\nRollback History (LIFO):");
        for (String roomId : rollbackStack) {
            System.out.println("Released Room ID: " + roomId);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v10.1");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("Alice", "Single Room", "RES-001", "SIN-101");
        Reservation r2 = new Reservation("Bob", "Double Room", "RES-002", "DOU-201");

        inventory.allocateRoom(r1.getRoomType());
        inventory.allocateRoom(r2.getRoomType());

        CancellationService cancellationService = new CancellationService(inventory);

        // Cancel Alice's reservation
        cancellationService.cancelReservation(r1);

        // Attempt to cancel Bob's reservation twice
        cancellationService.cancelReservation(r2);
        cancellationService.cancelReservation(r2); // should fail

        cancellationService.displayRollbackHistory();

        System.out.println("\nFinal Inventory State:");
        System.out.println("Single Room Availability: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room Availability: " + inventory.getAvailability("Double Room"));
    }
}