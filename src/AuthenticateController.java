import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AuthenticateController implements Initializable {

    @FXML
    private ImageView imageViewer;

    @FXML
    private JFXButton loginButton;

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXCheckBox showPasswordCheckBox;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private Label incorrectLabel;

    @FXML
    private TextField userNameTextFeild;

    @FXML
    private TextField passField;

    private EmployeeQueries eq = new EmployeeQueries();
    int confirm;
    final ObservableList<Employee> data = FXCollections.observableArrayList();

    @FXML
    void loadRegistration(ActionEvent event) throws IOException {
        BorderPane registration = FXMLLoader.load(getClass().getResource("Scene/Register.fxml"));
        anchor.getChildren().setAll(registration);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        imageViewer.setImage(new Image("C:\\Users\\Eba\\Pictures\\User.png"));
        data.setAll(eq.getAllEmployee());
        passField.setVisible(false);
        loginButton.setOnAction(e -> {
            confirm = eq.authenticate(userNameTextFeild.getText(), passwordTextField.getText());
            if (confirm == 1) {

                try {

                    BorderPane root = FXMLLoader.load(getClass().getResource("Scene/Searching.fxml"));
                    Stage stage = (Stage) userNameTextFeild.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            } else {
                userNameTextFeild.setStyle("-fx-border-color:RED");
                passwordTextField.setStyle("-fx-border-color:RED");
                passField.setStyle("-fx-border-color:RED");
                incorrectLabel.setText("Incorrect Username or Password");
            }

        });
        showPasswordCheckBox.selectedProperty().addListener((ov, old_Value, new_Value) -> {
            if (new_Value) {
                passField.setVisible(true);
                passField.setText(passwordTextField.getText());

            } else {
                passField.setVisible(false);
                passwordTextField.setText(passField.getText());
            }
        });

    }

}
