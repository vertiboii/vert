import basic.Hotel;
import basic.Reservation;
import basic.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

/**
 * The Controller class handles the interaction between the Model and the View.
 * It responds to user actions by updating the model and/or the view accordingly.
 */
public class Controller {

    private static final DecimalFormat decfor = new DecimalFormat("0.00");


    /**
     * Constructs a Controller instance and sets up action listeners for the view's components.
     *
     * @param model the Model instance managing the application's data
     * @param view the View instance managing the user interface
     */
    public Controller(Model model, View view) {

        view.main.getRootPane().setDefaultButton(view.main.createHotelButton);

        view.main.createHotelButton.addActionListener(_ -> {
            view.createHotel.getRootPane().setDefaultButton(view.createHotel.okButton);
            view.createHotel.text.setText("");
            view.createHotel.warning.setText("");
            view.moveToFront(view.createHotel);

            view.returnButton.setVisible(true);

            view.main.bookButton.setEnabled(false);
            view.main.deleteButton.setEnabled(false);
        });

        view.view.roomButton.addActionListener(_ -> {
            view.moveToFront(view.rooms);

            view.rooms.roomList.removeAll();
            view.rooms.textBoxList.clear();

            model.getCurrentHotel().sortRooms();

            for (Room r : model.getCurrentHotel().getRooms()) {
                view.rooms.createTextBox(r.getName());
            }

        });

        view.view.reserveButton.addActionListener(_ -> {

            view.moveToFront(view.reserve);

            view.reserve.reserveList.removeAll();
            view.reserve.textBoxList.clear();

            for (Reservation r : model.getCurrentHotel().getReservations()) {
                view.reserve.createTextBox(r.getGuestName());
            }

        });

        view.createHotel.okButton.addActionListener(_ -> {

            if (!view.createHotel.text.getText().isEmpty() && model.getHrs().getHotel(view.createHotel.text.getText()) == null) {
                model.getHrs().createHotel(view.createHotel.text.getText());
                view.main.createTextBox(view.createHotel.text.getText());
                view.goBack();

                view.main.getRootPane().setDefaultButton(view.main.createHotelButton);
                view.returnButton.setVisible(false);
            }
            else if (model.getHrs().getHotel(view.createHotel.text.getText()) != null)
            {
                view.createHotel.warning.setText(view.createHotel.text.getText() + " already exists!");
            }
            else
            {
                view.goBack();
                view.main.getRootPane().setDefaultButton(view.main.createHotelButton);
                view.returnButton.setVisible(false);
            }

        });

        view.manage.changeHotelButton.addActionListener(_ -> {
            view.moveToFront(view.change);
            view.change.text.setText("");
            view.change.warning.setText("");
        });

        view.change.okButton.addActionListener(_ -> {
            if (!view.change.text.getText().isEmpty() && model.getHrs().getHotel(view.change.text.getText()) == null) {
                model.getHrs().changeHotel(model.getCurrentHotel(), view.change.text.getText());
                view.goHome(view.main);


                view.main.hotelList.removeAll();
                view.main.textBoxList.clear();

                for (Hotel h : model.getHrs().getHotels()) {
                    view.main.createTextBox(h.getName());
                }
            }
            else if (model.getHrs().getHotel(view.change.text.getText()) != null)
            {
                view.change.warning.setText(view.change.text.getText() + " already exists!");
            }
            else
            {
                view.goBack();
            }
        });

        //action listeners

        view.viewHotel.viewButton.addActionListener(_ -> view.moveToFront(view.view));

        view.view.dateButton.addActionListener(_ -> view.moveToFront(view.checkDateInfo));

        view.main.bookButton.addActionListener(_ -> {

            for (JTextPane textPane : view.main.textBoxList) {
                textPane.setBackground(Color.white);
                textPane.setForeground(Color.black);
            }

            view.main.bookButton.setEnabled(false);
            view.main.deleteButton.setEnabled(false);

            for (JTextPane textPane : view.book.dayList) {
                textPane.setBackground(Color.white);
                textPane.setForeground(Color.black);
            }

            model.setCheckIn(null);
            model.setCheckOut(null);
            model.setClickCount(0);

            for (JTextPane textPane : view.book.roomList) {
                textPane.setBackground(Color.white);
                textPane.setForeground(Color.black);
            }
            model.setRoomType(0);
            view.book.warning.setText("");

            view.moveToFront(view.book);
            view.returnButton.setVisible(true);
            view.book.hotelName.setText("Booking a Reservation at " + model.getCurrentHotelPane().getText());
            view.book.guestName.setText("");
        });

        view.manage.updateBaseButton.addActionListener(_ ->
        {
            view.moveToFront(view.base);
            view.base.text.setText("");
            view.base.warning.setText("");
        });

        view.base.okButton.addActionListener(_ ->
        {
            if (model.getCurrentHotel().updateBasePrice(Double.parseDouble(view.base.text.getText()))==1) {
                view.base.warning.setText("must be >= P 100.00!");
            }
            else if (model.getCurrentHotel().updateBasePrice(Double.parseDouble(view.base.text.getText()))==2)
            {
                view.base.warning.setText("reservations are made!");
            }
            else
            {
                view.goBack();
            }
        });

        view.viewHotel.manageButton.addActionListener(_ -> view.moveToFront(view.manage));

        view.book.nextButton.addActionListener(_ -> {

            if (model.getHrs().checkBooking(model.getCurrentHotel(), view.book.guestName.getText(), Integer.parseInt(model.getCheckIn().getText()), Integer.parseInt(model.getCheckOut().getText()), model.getRoomType())==0) {
                view.book.warning.setText("");

                view.confirm.warning.setText("");
                view.confirm.text.setText("");
                view.confirm.text.setEnabled(true);
                view.confirm.okButton.setEnabled(true);

                view.moveToFront(view.confirm);
                view.confirm.guestName.setText("Guest Name: " + view.book.guestName.getText());
                view.confirm.daysBook.setText("Days Reserved: " + model.getCheckIn().getText() + " to " + model.getCheckOut().getText());

                if (model.getRoomType() == 1) {
                    view.confirm.roomType.setText("Room Type: Standard");
                } else if (model.getRoomType() == 2) {
                    view.confirm.roomType.setText("Room Type: Deluxe");
                } else if (model.getRoomType() == 3) {
                    view.confirm.roomType.setText("Room Type: Executive");
                }
            }
            else if (model.getHrs().checkBooking(model.getCurrentHotel(), view.book.guestName.getText(), Integer.parseInt(model.getCheckIn().getText()), Integer.parseInt(model.getCheckOut().getText()), model.getRoomType())==1)
            {
                view.book.warning.setText("guest has already booked!");
            }
            else if (model.getHrs().checkBooking(model.getCurrentHotel(), view.book.guestName.getText(), Integer.parseInt(model.getCheckIn().getText()), Integer.parseInt(model.getCheckOut().getText()), model.getRoomType())==2)
            {
                view.book.warning.setText("no available rooms!");
            }

        });

        view.confirm.okButton.addActionListener(_ -> {

            if (model.getHrs().checkVoucher(view.confirm.text.getText(), Integer.parseInt(model.getCheckIn().getText()), Integer.parseInt(model.getCheckOut().getText())))
            {
                view.confirm.warning.setText("<html><font color='green'>coupon successfully applied!</font></html>");
                view.confirm.text.setEnabled(false);
                view.confirm.okButton.setEnabled(false);

            }
            else
            {
                view.confirm.warning.setText("<html><font color='red'>does not exist or not applicable.</font></html>");
            }

        });

        view.returnButton.addActionListener(_ -> {
            view.goBack();

            if (view.layer.highestLayer() == 1)
            {
                for (Component comp : view.main.hotelList.getComponents())
                {
                    comp.setBackground(Color.white);
                    comp.setForeground(Color.black);
                }
                view.returnButton.setVisible(false);
                view.homeButton.setVisible(false);
            }

        });

        view.homeButton.addActionListener(_ -> view.goHome(view.main));

        view.view.infoButton.addActionListener(_ -> {

            view.moveToFront(view.info);
            view.info.hotelName.setText("Hotel Name: " + model.getCurrentHotel().getName());
            view.info.totalRooms.setText("Total Rooms: " + model.getCurrentHotel().getTotalRooms());
            view.info.estimateEarn.setText("Estimated Earnings: " + decfor.format(model.getCurrentHotel().calculateEarnings()));
        });

        view.main.deleteButton.addActionListener(_ -> {

            model.getHrs().removeHotel(model.getCurrentHotel());
            view.main.textBoxList.remove(model.getCurrentHotelPane());
            view.main.hotelList.remove(model.getCurrentHotelPane());


            view.main.hotelList.setVisible(false);
            view.main.hotelList.setVisible(true);

            view.main.bookButton.setEnabled(false);
            view.main.deleteButton.setEnabled(false);

        });

        view.rooms.stdButton.addActionListener(_ -> {
            model.getCurrentHotel().addRoom("A" + (model.getCurrentHotel().countStandard()+1), 1);


            view.rooms.roomList.removeAll();
            view.rooms.textBoxList.clear();

            model.getCurrentHotel().sortRooms();

            for (Room r : model.getCurrentHotel().getRooms()) {
                view.rooms.createTextBox(r.getName());
            }
        });

        view. rooms.dlxButton.addActionListener(_ -> {
            model.getCurrentHotel().addRoom("B" + (model.getCurrentHotel().countDeluxe()+1), 2);


            view.rooms.roomList.removeAll();
            view.rooms.textBoxList.clear();

            model.getCurrentHotel().sortRooms();

            for (Room r : model.getCurrentHotel().getRooms()) {
                view.rooms.createTextBox(r.getName());
            }
        });

        view.rooms.excButton.addActionListener(_ -> {


            model.getCurrentHotel().addRoom("C" + (model.getCurrentHotel().nextRoom(3)), 3);


            view.rooms.roomList.removeAll();
            view.rooms.textBoxList.clear();

            model.getCurrentHotel().sortRooms();

            for (Room r : model.getCurrentHotel().getRooms()) {
                view.rooms.createTextBox(r.getName());
            }
        });

        view.rooms.deleteButton.addActionListener(_ -> {

            model.getCurrentHotel().removeRoom(model.getCurrentRoom());

            view.rooms.roomList.removeAll();
            view.rooms.textBoxList.clear();

            model.getCurrentHotel().sortRooms();

            for (Room r : model.getCurrentHotel().getRooms()) {
                view.rooms.createTextBox(r.getName());
            }

            view.rooms.deleteButton.setEnabled(false);

            view.rooms.roomList.setVisible(false);
            view.rooms.roomList.setVisible(true);
        });

        view.reserve.deleteButton.addActionListener(_ -> {

            model.getCurrentHotel().removeReservation(model.getCurrentReservation());

            view.reserve.reserveList.removeAll();
            view.reserve.textBoxList.clear();

            for (Reservation r : model.getCurrentHotel().getReservations()) {
                view.reserve.createTextBox(r.getGuestName());
            }

            view.reserve.deleteButton.setEnabled(false);

            view.reserve.reserveList.setVisible(false);
            view.reserve.reserveList.setVisible(true);
        });




        view.confirm.reserveButton.addActionListener(_ -> {
            Reservation newReservation = model.getHrs().simulateBooking(model.getCurrentHotel(), view.book.guestName.getText(), Integer.parseInt(model.getCheckIn().getText()), Integer.parseInt(model.getCheckOut().getText()), model.getRoomType());
            if (!view.confirm.text.isEnabled()) {
                newReservation.setVoucher(view.confirm.text.getText());
            }

            view.goBack();
            view.goBack();

            view.main.bookButton.setEnabled(false);
            view.returnButton.setVisible(false);
            view.homeButton.setVisible(false);
        });


        for (int i=0; i<view.checkDateInfo.dayList.size(); i++) {

            JButton tempButton = view.checkDateInfo.dayList.get(i);

            tempButton.addActionListener(_ -> {

                int temp = Integer.parseInt(tempButton.getText());

                view.moveToFront(view.date);
                view.date.day.setText("Day " + temp);
                view.date.totalRoomsAvail.setText("Available Rooms: " + model.getCurrentHotel().getAvailableRooms(temp));
                view.date.totalRoomsBooked.setText("Booked Rooms: " + model.getCurrentHotel().getBookedRooms(temp));
            });
        }

        for (int i=0; i<view.selectDay.dayList.size(); i++) {

            JButton tempButton = view.selectDay.dayList.get(i);

            tempButton.addActionListener(_ -> {

                model.setCurrentDay(Integer.parseInt(tempButton.getText()));
                view.moveToFront(view.datePercent);
                view.datePercent.text.setText("");
                view.datePercent.warning.setText("");

            });
        }

        view.manage.datePercentButton.addActionListener(_ -> view.moveToFront(view.selectDay));

        view.datePercent.okButton.addActionListener(_ -> {
            if ((Double.parseDouble(view.datePercent.text.getText())>=50.0 && (Double.parseDouble(view.datePercent.text.getText())<=150.0)))
            {
                model.getCurrentHotel().setAllReservationPercentage(model.getCurrentDay(), Double.parseDouble(view.datePercent.text.getText())/100);
                view.goBack();
            }
            else
            {
                view.datePercent.warning.setText("50% to 150% only!");
            }
        });



        Toolkit.getDefaultToolkit().addAWTEventListener(event -> { // universal actionlistener when reserve panel is visible
            if (view.reserve.isVisible()) {
                if (event instanceof MouseEvent evt) {
                    if (evt.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.reserve.textBoxList.contains(textBox)) {
                                if (evt.getClickCount() == 1) {
                                    for (JTextPane textPane : view.reserve.textBoxList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                    model.setCurrentReservationPane(textBox);
                                    model.setCurrentReservation(model.getCurrentHotel().getReservationByGuestName(model.getCurrentReservationPane().getText()));
                                    model.getCurrentReservationPane().setBackground(new Color(31, 117, 254));
                                    model.getCurrentReservationPane().setForeground(Color.white);

                                    view.reserve.deleteButton.setEnabled(true);

                                } else if (evt.getClickCount() == 2) {

                                    view.moveToFront(view.viewReserve);
                                    for (JTextPane textPane : view.reserve.textBoxList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                    view.reserve.deleteButton.setEnabled(false);

                                    view.viewReserve.guestName.setText("Guest Name: " + model.getCurrentReservation().getGuestName());
                                    view.viewReserve.roomName.setText("Room Name: " + model.getCurrentReservation().getRoom().getName());
                                    view.viewReserve.daysReserved.setText("Stay: " + model.getCurrentReservation().getCheckIn() + " to " + model.getCurrentReservation().getCheckOut());
                                    view.viewReserve.voucherApplied.setText("Coupon Applied: " + model.getCurrentReservation().getVoucher());
                                    view.viewReserve.totalPrice.setText("Total Price: P " + decfor.format(model.getCurrentReservation().getTotalPrice()));

                                    view.viewReserve.costBreakdown.removeAll();

                                    for (int i = model.getCurrentReservation().getCheckIn(); i< model.getCurrentReservation().getCheckOut(); i++)
                                    {
                                        JLabel temp = new JLabel(i + " to " + (i+1) + " -> " + model.getCurrentReservation().getDatePercentage(i)*100 +"%");
                                        temp.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
                                        temp.setPreferredSize(new Dimension(150,30));
                                        view.viewReserve.costBreakdown.add(temp);
                                    }

                                }
                            }
                        } else if (!evt.getSource().equals(view.reserve.deleteButton)) {
                            for (JTextPane textPane : view.reserve.textBoxList) {
                                textPane.setBackground(Color.white);
                                textPane.setForeground(Color.black);
                            }

                            view.reserve.deleteButton.setEnabled(false);
                        }
                    } else {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.reserve.textBoxList.contains(textBox)) {
                                for (JTextPane textPane : view.reserve.textBoxList) {
                                    if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                }
                                if (!textBox.getBackground().equals(new Color(31, 117, 254))) {
                                    textBox.setBackground(Color.lightGray);
                                }
                            }
                        }
                        else {
                            for (JTextPane textPane : view.reserve.textBoxList) {
                                if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }
                            }
                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> { // universal actionlistener when rooms panel is visible
            if (view.rooms.isVisible()) {
                if (event instanceof MouseEvent evt) {
                    if (evt.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.rooms.textBoxList.contains(textBox)) {
                                if (evt.getClickCount() == 1) {
                                    for (JTextPane textPane : view.rooms.textBoxList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                    model.setCurrentRoomPane(textBox);
                                    model.setCurrentRoom(model.getCurrentHotel().getRoomByName(model.getCurrentRoomPane().getText()));
                                    model.getCurrentRoomPane().setBackground(new Color(31, 117, 254));
                                    model.getCurrentRoomPane().setForeground(Color.white);

                                    view.rooms.deleteButton.setEnabled(false);
                                    if (model.getCurrentHotel().getTotalRooms() != 1 && model.getCurrentRoom().checkAvailableInterval(1, 31)) {
                                        view.rooms.deleteButton.setEnabled(true);
                                    }

                                } else if (evt.getClickCount() == 2) {

                                    view.moveToFront(view.viewRoom);
                                    for (JTextPane textPane : view.rooms.textBoxList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                    view.rooms.deleteButton.setEnabled(false);

                                    view.viewRoom.roomName.setText("Room Name: " + model.getCurrentRoom().getName());
                                    view.viewRoom.roomType.setText("Room Type: " + model.getCurrentRoom().getRoomTypeString());
                                    view.viewRoom.costPerNight.setText("Cost Per Night: P" + decfor.format(model.getCurrentRoom().getFinalPricePerNight()));

                                    for (int i=1; i<=31; i++)
                                    {
                                        view.viewRoom.dayList.get(i-1).setBackground(Color.white);
                                    }

                                    for (int i=1; i<=31; i++)
                                    {
                                        if (!model.getCurrentRoom().checkAvailable(i))
                                            view.viewRoom.dayList.get(i-1).setBackground(Color.lightGray);
                                    }

                                }
                            }
                        } else if (!evt.getSource().equals(view.rooms.deleteButton)) {
                            for (JTextPane textPane : view.rooms.textBoxList) {
                                textPane.setBackground(Color.white);
                                textPane.setForeground(Color.black);
                            }

                            view.rooms.deleteButton.setEnabled(false);
                        }
                    } else {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.rooms.textBoxList.contains(textBox)) {
                                for (JTextPane textPane : view.rooms.textBoxList) {
                                    if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                }
                                if (!textBox.getBackground().equals(new Color(31, 117, 254))) {
                                    textBox.setBackground(Color.lightGray);
                                }
                            }
                        }
                        else {
                            for (JTextPane textPane : view.rooms.textBoxList) {
                                if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }
                            }
                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);


        Toolkit.getDefaultToolkit().addAWTEventListener(event -> { // universal actionlistener when main panel is visible
            if (view.main.isVisible()) {
                if (event instanceof MouseEvent evt) {
                    if (evt.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.main.textBoxList.contains(textBox)) {
                                if (evt.getClickCount() == 1) {
                                    for (JTextPane textPane : view.main.textBoxList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                    model.setCurrentHotelPane(textBox);
                                    model.setCurrentHotel(model.getHrs().getHotel(textBox.getText()));
                                    model.getCurrentHotelPane().setBackground(new Color(31, 117, 254));
                                    model.getCurrentHotelPane().setForeground(Color.white);
                                    view.main.bookButton.setEnabled(true);
                                    view.main.deleteButton.setEnabled(true);

                                } else if (evt.getClickCount() == 2) {

                                    view.moveToFront(view.viewHotel);
                                    for (JTextPane textPane : view.main.textBoxList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                    view.main.bookButton.setEnabled(false);
                                    view.main.deleteButton.setEnabled(false);
                                    view.returnButton.setVisible(true);
                                    view.homeButton.setVisible(true);
                                }
                            }
                        } else if (!evt.getSource().equals(view.main.deleteButton) && !evt.getSource().equals(view.main.bookButton)) {
                            for (JTextPane textPane : view.main.textBoxList) {
                                textPane.setBackground(Color.white);
                                textPane.setForeground(Color.black);
                            }

                            view.main.bookButton.setEnabled(false);
                            view.main.deleteButton.setEnabled(false);
                        }
                    } else {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.main.textBoxList.contains(textBox)) {
                                for (JTextPane textPane : view.main.textBoxList) {
                                    if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                }
                                if (!textBox.getBackground().equals(new Color(31, 117, 254))) {
                                    textBox.setBackground(Color.lightGray);
                                }
                            }
                        }
                        else {
                            for (JTextPane textPane : view.main.textBoxList) {
                                if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }
                            }
                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);


        Toolkit.getDefaultToolkit().addAWTEventListener(event -> { // universal actionlistener when book panel is visible
            if (view.book.isVisible()) {
                if (event instanceof MouseEvent evt) {
                    if (evt.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (view.book.dayList.contains(textBox)) {
                                if (model.getClickCount() == 0) {

                                    if (Integer.parseInt(textBox.getText()) != 31) {
                                        for (JTextPane textPane : view.book.dayList) {
                                            textPane.setBackground(Color.white);
                                            textPane.setForeground(Color.black);
                                        }

                                        model.setCheckIn(textBox);
                                        model.getCheckIn().setBackground(new Color(31, 117, 254));
                                        model.getCheckIn().setForeground(Color.white);
                                        model.setClickCount(1);
                                    }

                                } else if (model.getClickCount() == 1) {
                                    if (Integer.parseInt(model.getCheckIn().getText()) < Integer.parseInt(textBox.getText())) {
                                        model.setCheckOut(textBox);
                                        for (int i = Integer.parseInt(model.getCheckIn().getText()) - 1; i < Integer.parseInt(model.getCheckOut().getText()); i++) {
                                            view.book.dayList.get(i).setBackground(new Color(31, 117, 254));
                                            view.book.dayList.get(i).setForeground(Color.white);
                                        }
                                    } else {
                                        for (JTextPane textPane : view.book.dayList) {
                                            textPane.setBackground(Color.white);
                                            textPane.setForeground(Color.black);
                                        }
                                        model.setCheckIn(null);
                                        model.setCheckOut(null);

                                    }

                                    model.setClickCount(0);
                                }
                            } else if (view.book.roomList.contains(textBox)) {
                                if (textBox.getText().equals("STANDARD")) {
                                    model.setRoomType(1);
                                } else if (textBox.getText().equals("DELUXE")) {
                                    model.setRoomType(2);
                                } else if (textBox.getText().equals("EXECUTIVE")) {
                                    model.setRoomType(3);
                                }

                                for (JTextPane textPane : view.book.roomList) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }

                                textBox.setBackground(new Color(31, 117, 254));
                                textBox.setForeground(Color.white);
                            }
                        } else if (!(evt.getSource() instanceof JTextField) && !(evt.getSource().equals(view.book.nextButton)) && view.book.isVisible()) {

                            if (model.getTextClick() == 0) {
                                for (JTextPane textPane : view.book.dayList) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }

                                model.setCheckIn(null);
                                model.setCheckOut(null);
                                model.setClickCount(0);
                            } else {
                                model.setTextClick(0);
                            }
                        } else if (evt.getSource() instanceof JTextField) {
                            model.setTextClick(1);
                        }
                    } else {

                        view.book.nextButton.setEnabled(!view.book.guestName.getText().isEmpty() && model.getCheckIn() != null && model.getCheckOut() != null && model.getRoomType() != 0);

                        if (evt.getSource() instanceof JTextPane textBox) {
                            if (model.getClickCount() == 0) {
                                for (JTextPane textPane : view.book.dayList) {
                                    if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }
                                }
                                if (!textBox.getBackground().equals(new Color(31, 117, 254))) {
                                    textBox.setBackground(Color.lightGray);
                                }
                            } else if (model.getClickCount() == 1) {
                                if (!view.book.roomList.contains(textBox)) {
                                    for (JTextPane textPane : view.book.dayList) {
                                        textPane.setBackground(Color.white);
                                        textPane.setForeground(Color.black);
                                    }

                                    for (int i = Integer.parseInt(model.getCheckIn().getText()) - 1; i < Integer.parseInt(textBox.getText()); i++) {
                                        view.book.dayList.get(i).setBackground(new Color(31, 117, 254));
                                        view.book.dayList.get(i).setForeground(Color.white);
                                    }
                                }
                            }
                        } else {
                            for (JTextPane textPane : view.book.dayList) {
                                if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }
                            }

                            for (JTextPane textPane : view.book.roomList) {
                                if (!textPane.getBackground().equals(new Color(31, 117, 254))) {
                                    textPane.setBackground(Color.white);
                                    textPane.setForeground(Color.black);
                                }
                            }

                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }
}