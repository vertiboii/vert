/**
 * The Main class is the entry point of the hotel reservation system application.
 * It initializes the model, view, and controller components and sets up the application.
 */
public class Main {

    /**
     * The main method is the entry point of the application.
     * It creates instances of Model, View, and Controller to start the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create an instance of the Model class which handles data and business logic
        Model model = new Model();

        // Create an instance of the View class which manages the user interface
        View view = new View();

        // Create an instance of the Controller class which handles user input and updates the model and view
        Controller controller = new Controller(model, view);

        // The application is now set up with the model, view, and controller
    }
}
