package ca.apis;

public class Notifications {
    public static String getCurrent(String area) {
        return switch(area) {
            case "technical" -> "Please complete self-paced online training at training.com";
            case "business" -> "Please register for training week in the Maldives";
            case "admin" -> "Please attend training on Friday, 10am in room 404";
            default -> "No notifications available for this area.";
        };
    }
}
