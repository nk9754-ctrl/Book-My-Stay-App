import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(String guestName, String roomType, String reservationId) {
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

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
        System.out.println("Service '" + service.getName() + "' added to Reservation ID: " + reservationId);
    }

    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        if (services.isEmpty()) {
            System.out.println("No add-on services selected for Reservation ID: " + reservationId);
        } else {
            System.out.println("Add-On Services for Reservation ID: " + reservationId);
            double totalCost = 0;
            for (Service service : services) {
                System.out.println("- " + service.getName() + " | Cost: " + service.getCost());
                totalCost += service.getCost();
            }
            System.out.println("Total Additional Cost: " + totalCost);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v7.1");

        Reservation reservation1 = new Reservation("Alice", "Single Room", "RES-001");
        Reservation reservation2 = new Reservation("Bob", "Suite Room", "RES-002");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        serviceManager.addServiceToReservation(reservation1.getReservationId(), new Service("Breakfast", 300.0));
        serviceManager.addServiceToReservation(reservation1.getReservationId(), new Service("Airport Pickup", 800.0));
        serviceManager.addServiceToReservation(reservation2.getReservationId(), new Service("Spa Access", 1200.0));

        System.out.println();
        serviceManager.displayServices(reservation1.getReservationId());
        System.out.println();
        serviceManager.displayServices(reservation2.getReservationId());
    }
}