import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchingController implements Initializable {
    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    @FXML
    private JFXRadioButton femaleRadioButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TableColumn<Employee, String> firtColumn;

    @FXML
    private TableColumn<Employee, String> lastColumn;

    @FXML
    private TextField lastNameTesxtField;

    @FXML
    private TextField emailTextField;

    @FXML
    private JFXRadioButton maleRadioButton;

    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField professionTextField;

    @FXML
    private ImageView searchImageViewer;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label selectedRowLabel;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private BorderPane baseBorder;

    @FXML
    private ImageView backToLogin;

    @FXML
    private JFXButton backButton;

    @FXML
    void back(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Scene/Authenticate.fxml"));
        baseBorder.getChildren().setAll(pane);
    }

    ToggleGroup group = new ToggleGroup();

    final ObservableList<Employee> data = FXCollections.observableArrayList();

    private EmployeeQueries eq = new EmployeeQueries();

    String deletedRow;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        backToLogin.setImage(new Image("C:\\Users\\Eba\\Pictures\\back.png"));
        searchImageViewer.setImage(new Image("C:\\Users\\Eba\\Pictures\\search.png"));
        Tooltip dir = new Tooltip("Click to logout from searching Page");
        backButton.setTooltip(dir);
        maleRadioButton.setToggleGroup(group);
        femaleRadioButton.setToggleGroup(group);

        data.setAll(eq.getAllEmployee());

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {

            @Override
            public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
                if (newValue != null) {
                    selectedRowLabel.setText((newValue.getFirstName() + "    " + newValue.getLastName() + "     "
                            + "   " + newValue.getGender() + "    " + newValue.getProfession()));
                    deletedRow = newValue.getFirstName();

                } else {
                    selectedRowLabel.setText("No rows has been selected");
                }

            }

        });
        firtColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("FirstName"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("LastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("PhoneNumber"));
        tableView.setItems(data);

        addButton.setOnAction(e -> {
            //
            if (firstNameTextField.getText() == "" || phoneNumberTextField.getText() == ""
                    || lastNameTesxtField.getText() == "" || professionTextField.getText() == ""
                    || emailTextField.getText() == ""
                    || emailTextField.getText() == "" || group.getSelectedToggle() == null)

            {
                Alert alert = new Alert(AlertType.WARNING, "Field is Empty");
                alert.show();
            } else {

                int response = eq.addEmployee(
                        firstNameTextField.getText(),
                        lastNameTesxtField.getText(),
                        emailTextField.getText(),
                        professionTextField.getText(),
                        ((Labeled) group.getSelectedToggle()).getText(),
                        phoneNumberTextField.getText());

                if (response == 1) {
                    data.setAll(eq.getAllEmployee());
                }
                firstNameTextField.clear();
                lastNameTesxtField.clear();
                emailTextField.clear();
                phoneNumberTextField.clear();
                professionTextField.clear();

            }
        });

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Label confirm = new Label("Are you sure you want to delete these student infromation!");
        JFXButton btnYes = new JFXButton("YES");
        JFXButton btnNo = new JFXButton("NO");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnYes, btnNo);
        hBox.setSpacing(55);
        HBox.setMargin(btnYes, new Insets(0, 0, 0, 100));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(confirm, hBox);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        btnYes.setOnAction(e -> {
            int response = eq.deleteEmployee(deletedRow);
            if (response == 1) {
                data.setAll(eq.getAllEmployee());
                window.close();
            }
        });
        btnNo.setOnAction(e -> {
            window.close();
        });
        Scene scene = new Scene(vBox, 350, 100);
        window.setTitle("Delete Information");
        window.setScene(scene);

        deleteButton.setOnAction(e -> {
            if (!selectedRowLabel.getText().equals("No rows has been selected")) {
                window.show();
            }
        });

        // Searching Algorithm

        FilteredList<Employee> filteredEmployee = new FilteredList<>(data, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmployee.setPredicate(Employee -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Employee.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Employee.getLastName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Employee.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Employee.getPhoneNumber().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Employee> sortedStudent = new SortedList<>(filteredEmployee);
        sortedStudent.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedStudent);

    }

}
