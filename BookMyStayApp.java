import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(String guestName, String roomType, String reservationId) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
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
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) throws InvalidBookingException {
        if (availability < 0) {
            throw new InvalidBookingException("Availability cannot be negative for room type: " + roomType);
        }
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type requested: " + roomType);
        }
        return inventory.get(roomType);
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        int available = getAvailability(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
        inventory.put(roomType, available - 1);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v9.1");

        RoomInventory inventory = new RoomInventory();
        try {
            inventory.addRoomType("Single Room", 2);
            inventory.addRoomType("Double Room", 1);
            inventory.addRoomType("Suite Room", 0);

            Reservation r1 = new Reservation("Alice", "Single Room", "RES-001");
            Reservation r2 = new Reservation("Bob", "Suite Room", "RES-002");
            Reservation r3 = new Reservation("", "Double Room", "RES-003"); // invalid guest name

            inventory.allocateRoom(r1.getRoomType());
            System.out.println("Reservation confirmed for " + r1.getGuestName());

            inventory.allocateRoom(r2.getRoomType()); // should fail (no availability)
            System.out.println("Reservation confirmed for " + r2.getGuestName());

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}