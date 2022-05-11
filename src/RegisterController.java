import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RegisterController implements Initializable {
    @FXML
    private Label label;

    @FXML
    private JFXButton registerButton;

    @FXML
    private PasswordField registerConfirmPassword;

    @FXML
    private TextField registerEmail;

    @FXML
    private JFXRadioButton registerFemale;

    @FXML
    private TextField registerFirstName;
    @FXML
    private TextField registerPhone;
    @FXML
    private TextField registerLastName;

    @FXML
    private JFXRadioButton registerMale;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerProfession;

    @FXML
    private BorderPane baseBorder;

    @FXML
    private JFXButton cancelButton;

    ToggleGroup group = new ToggleGroup();
    private EmployeeQueries eq = new EmployeeQueries();
    final ObservableList<Employee> data = FXCollections.observableArrayList();
    AnchorPane pane;

    @FXML
    void cancelRegistration(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("Scene/Authenticate.fxml"));
        baseBorder.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        registerMale.setToggleGroup(group);
        registerFemale.setToggleGroup(group);

        registerButton.setOnAction(e -> {
            //
            if (registerFirstName.getText() == "" || registerPhone.getText() == ""
                    || registerLastName.getText() == "" || registerProfession.getText() == ""
                    || registerEmail.getText() == ""
                    || registerEmail.getText() == "" || group.getSelectedToggle() == null
                    || registerPassword.getText() == "" || registerConfirmPassword.getText() == ""
                    || !registerConfirmPassword.getText().equals(registerPassword.getText()))

            {
                label.setText("Correct The Field above");
                Alert alert = new Alert(AlertType.WARNING, "Wrong Input");
                alert.show();
            } else {

                int response = eq.registerEmployee(
                        registerFirstName.getText(),
                        registerLastName.getText(),
                        registerEmail.getText(),
                        registerProfession.getText(),
                        ((Labeled) group.getSelectedToggle()).getText(),
                        registerPhone.getText(),
                        registerPassword.getText());

                if (response == 1) {
                    data.setAll(eq.getAllEmployee());
                }
                registerFirstName.clear();
                registerLastName.clear();
                registerEmail.clear();
                registerPhone.clear();
                registerProfession.clear();
                registerPassword.clear();
                registerConfirmPassword.clear();

                Alert alert = new Alert(AlertType.INFORMATION, "YOU HAVA SUCCESSFULLY REGISTERED");
                alert.show();
                try {
                    pane = FXMLLoader.load(getClass().getResource("Scene/Authenticate.fxml"));
                    baseBorder.getChildren().setAll(pane);
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }

        });
    }

}
