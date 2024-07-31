import basic.*;

import javax.swing.*;

/**
 * The Model class is responsible for managing the state of the hotel reservation system.
 * It maintains references to various components like hotel, room, reservation, and UI elements.
 */
public class Model {

    private final HotelReservationSystem hrs;
    private JTextPane currentHotelPane;
    private Hotel currentHotel;
    private JTextPane currentRoomPane;
    private Room currentRoom;
    private int currentDay;
    private JTextPane currentReservationPane;
    private Reservation currentReservation;
    private JTextPane checkIn;
    private JTextPane checkOut;
    private int roomType;
    private int clickCount;
    private int textClick;

    /**
     * Constructs a new Model instance with an initialized HotelReservationSystem.
     */
    public Model() {
        hrs = new HotelReservationSystem();
        checkIn = null;
        checkOut = null;
        roomType = 0;
        clickCount = 0;
        textClick = 0;
    }

    /**
     * Gets the HotelReservationSystem instance.
     * @return the HotelReservationSystem instance
     */
    public HotelReservationSystem getHrs() {
        return hrs;
    }

    /**
     * Gets the JTextPane that displays the current hotel information.
     * @return the JTextPane for the current hotel
     */
    public JTextPane getCurrentHotelPane() {
        return currentHotelPane;
    }

    /**
     * Sets the JTextPane that displays the current hotel information.
     * @param currentHotelPane the JTextPane to set for the current hotel
     */
    public void setCurrentHotelPane(JTextPane currentHotelPane) {
        this.currentHotelPane = currentHotelPane;
    }

    /**
     * Gets the currently selected Hotel.
     * @return the current Hotel
     */
    public Hotel getCurrentHotel() {
        return currentHotel;
    }

    /**
     * Sets the currently selected Hotel.
     * @param currentHotel the Hotel to set as the current hotel
     */
    public void setCurrentHotel(Hotel currentHotel) {
        this.currentHotel = currentHotel;
    }

    /**
     * Gets the JTextPane that displays the current room information.
     * @return the JTextPane for the current room
     */
    public JTextPane getCurrentRoomPane() {
        return currentRoomPane;
    }

    /**
     * Sets the JTextPane that displays the current room information.
     * @param currentRoomPane the JTextPane to set for the current room
     */
    public void setCurrentRoomPane(JTextPane currentRoomPane) {
        this.currentRoomPane = currentRoomPane;
    }

    /**
     * Gets the currently selected Room.
     * @return the current Room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the currently selected Room.
     * @param currentRoom the Room to set as the current room
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Gets the current day.
     * @return the current day
     */
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * Sets the current day.
     * @param currentDay the day to set
     */
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    /**
     * Gets the JTextPane that displays the current reservation information.
     * @return the JTextPane for the current reservation
     */
    public JTextPane getCurrentReservationPane() {
        return currentReservationPane;
    }

    /**
     * Sets the JTextPane that displays the current reservation information.
     * @param currentReservationPane the JTextPane to set for the current reservation
     */
    public void setCurrentReservationPane(JTextPane currentReservationPane) {
        this.currentReservationPane = currentReservationPane;
    }

    /**
     * Gets the currently selected Reservation.
     * @return the current Reservation
     */
    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    /**
     * Sets the currently selected Reservation.
     * @param currentReservation the Reservation to set as the current reservation
     */
    public void setCurrentReservation(Reservation currentReservation) {
        this.currentReservation = currentReservation;
    }

    /**
     * Gets the JTextPane that displays the check-in information.
     * @return the JTextPane for check-in
     */
    public JTextPane getCheckIn() {
        return checkIn;
    }

    /**
     * Sets the JTextPane that displays the check-in information.
     * @param checkIn the JTextPane to set for check-in
     */
    public void setCheckIn(JTextPane checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Gets the JTextPane that displays the check-out information.
     * @return the JTextPane for check-out
     */
    public JTextPane getCheckOut() {
        return checkOut;
    }

    /**
     * Sets the JTextPane that displays the check-out information.
     * @param checkOut the JTextPane to set for check-out
     */
    public void setCheckOut(JTextPane checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * Gets the type of the room.
     * @return the room type
     */
    public int getRoomType() {
        return roomType;
    }

    /**
     * Sets the type of the room.
     * @param roomType the type of the room to set
     */
    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    /**
     * Gets the count of clicks.
     * @return the click count
     */
    public int getClickCount() {
        return clickCount;
    }

    /**
     * Sets the count of clicks.
     * @param clickCount the number of clicks to set
     */
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * Gets the count of text clicks.
     * @return the text click count
     */
    public int getTextClick() {
        return textClick;
    }

    /**
     * Sets the count of text clicks.
     * @param textClick the number of text clicks to set
     */
    public void setTextClick(int textClick) {
        this.textClick = textClick;
    }
}
