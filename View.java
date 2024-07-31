import javax.swing.*;
import java.awt.*;
import panels.*;

/**
 * The View class represents the graphical user interface of the hotel reservation system.
 * It extends JFrame and manages the various panels and buttons used in the application.
 */
public class View extends JFrame {

    // Buttons for navigation
    public JButton returnButton;
    public JButton homeButton;

    // Layered panes for managing different views
    public JLayeredPane layer;
    public JLayeredPane layer2;

    // Panels for different functionalities
    public mainPanel main;
    public createHotelPanel createHotel;
    public viewHotelPanel viewHotel;
    public viewPanel view;
    public infoPanel info;
    public checkDatePanel checkDateInfo;
    public datePanel date;
    public bookPanel book;
    public confirmPanel confirm;
    public roomPanel rooms;
    public viewRoomPanel viewRoom;
    public reservePanel reserve;
    public viewReservationPanel viewReserve;
    public managePanel manage;
    public changeHotelPanel change;
    public basePricePanel base;
    public checkDatePanel selectDay;
    public datePercentPanel datePercent;

    /**
     * Constructs the View object and initializes the GUI components.
     */
    public View() {
        // Initialize layered panes
        layer = new JLayeredPane();
        layer2 = new JLayeredPane();

        // Initialize panels
        main = new mainPanel();
        createHotel = new createHotelPanel();
        viewHotel = new viewHotelPanel();
        view = new viewPanel();
        info = new infoPanel();
        checkDateInfo = new checkDatePanel();
        date = new datePanel();
        book = new bookPanel();
        confirm = new confirmPanel();
        rooms = new roomPanel();
        viewRoom = new viewRoomPanel();
        reserve = new reservePanel();
        viewReserve = new viewReservationPanel();
        manage = new managePanel();
        change = new changeHotelPanel();
        base = new basePricePanel();
        selectDay = new checkDatePanel();
        datePercent = new datePercentPanel();

        // Set JFrame properties
        this.setTitle("Hotel Reservation System");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);

        // Configure the layer pane
        layer.setBounds(0, 0, 400, 400);

        // Add panels to the layer pane
        layer.add(main, Integer.valueOf(1));
        main.setBounds(0, 0, 400, 400);

        layer.add(createHotel, Integer.valueOf(0));
        createHotel.setBounds(0, 0, 400, 400);

        layer.add(viewHotel, Integer.valueOf(0));
        viewHotel.setBounds(0, 0, 400, 400);

        layer.add(view, Integer.valueOf(0));
        view.setBounds(0, 0, 400, 400);

        layer.add(info, Integer.valueOf(0));
        info.setBounds(0, 0, 400, 400);

        layer.add(checkDateInfo, Integer.valueOf(0));
        checkDateInfo.setBounds(0, 0, 400, 400);

        layer.add(date, Integer.valueOf(0));
        date.setBounds(0, 0, 400, 400);

        layer.add(book, Integer.valueOf(0));
        book.setBounds(0, 0, 400, 400);

        layer.add(confirm, Integer.valueOf(0));
        confirm.setBounds(0, 0, 400, 400);

        layer.add(rooms, Integer.valueOf(0));
        rooms.setBounds(0, 0, 400, 400);

        layer.add(viewRoom, Integer.valueOf(0));
        viewRoom.setBounds(0, 0, 400, 400);

        layer.add(reserve, Integer.valueOf(0));
        reserve.setBounds(0, 0, 400, 400);

        layer.add(viewReserve, Integer.valueOf(0));
        viewReserve.setBounds(0, 0, 400, 400);

        layer.add(manage, Integer.valueOf(0));
        manage.setBounds(0, 0, 400, 400);

        layer.add(change, Integer.valueOf(0));
        change.setBounds(0, 0, 400, 400);

        layer.add(base, Integer.valueOf(0));
        base.setBounds(0, 0, 400, 400);

        layer.add(selectDay, Integer.valueOf(0));
        selectDay.setBounds(0, 0, 400, 400);

        layer.add(datePercent, Integer.valueOf(0));
        datePercent.setBounds(0, 0, 400, 400);

        // Initialize and configure navigation buttons
        returnButton = new JButton("<");
        returnButton.setBounds(320, 330, 50, 20);
        returnButton.setForeground(Color.red);
        returnButton.setFocusable(false);
        returnButton.setBackground(Color.WHITE);
        returnButton.setVisible(false);

        homeButton = new JButton("<<");
        homeButton.setBounds(320, 300, 50, 20);
        homeButton.setForeground(Color.red);
        homeButton.setFocusable(false);
        homeButton.setBackground(Color.WHITE);
        homeButton.setVisible(false);

        // Configure the second layered pane and add components
        layer2.setBounds(0, 0, 400, 400);
        layer2.add(layer, Integer.valueOf(0));
        layer2.add(returnButton, Integer.valueOf(1));
        layer2.add(homeButton, Integer.valueOf(1));

        // Add the layered pane to the JFrame
        this.add(layer2);
        this.setVisible(true);
    }

    /**
     * Moves all components in the highest layer of the layered pane to the bottom.
     * Only the components in the highest layer remain visible.
     */
    void goBack() {
        for (Component comp : layer.getComponentsInLayer(layer.highestLayer())) {
            layer.setLayer(comp, 0);  // Move component to bottom layer
        }

        for (Component c : layer.getComponents()) {
            c.setVisible(layer.getLayer(c) == layer.highestLayer());  // Only show components in the highest layer
        }
    }

    /**
     * Hides all components in the layered pane and shows the specified panel.
     * The navigation buttons are hidden.
     *
     * @param main the panel to show
     */
    void goHome(mainPanel main) {
        for (Component c : layer.getComponents()) {
            layer.setLayer(c, 0);  // Move all components to bottom layer
            c.setVisible(false);  // Hide all components
        }

        moveToFront(main);  // Show the specified panel
        returnButton.setVisible(false);  // Hide the return button
        homeButton.setVisible(false);  // Hide the home button
    }

    /**
     * Moves the specified component to the front and makes it visible.
     * Hides all other components in the layered pane.
     *
     * @param comp the component to move to the front
     */
    void moveToFront(Component comp) {
        for (Component c : layer.getComponents()) {
            c.setVisible(false);  // Hide all components
        }

        comp.setVisible(true);  // Show the specified component
        layer.setLayer(comp, layer.highestLayer() + 1);  // Move the component to the top layer
    }
}
