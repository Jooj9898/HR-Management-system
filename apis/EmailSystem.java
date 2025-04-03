package ca.apis;

public class EmailSystem {
    public static void send(String address, String text) {
        System.out.println("\n\n---------- SENDING EMAIL ----------");
        System.out.println("To: " + address + "\n");
        System.out.println("Subject: Notification\n");
        System.out.println("Body: " + text + "\n");
        System.out.println("---------------- SENT ---------------");
    }
}
