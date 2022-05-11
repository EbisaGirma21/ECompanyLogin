import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeQueries {
    private static final String url = "jdbc:mysql://localhost:3306/ECompany";
    private static final String user = "root";
    private static final String password = "Eg1325#@";

    private Connection connection;
    private CallableStatement selectAll;
    private CallableStatement registerNewEmployee;
    private CallableStatement deleteEmployee;
    private CallableStatement addEmployee;
    ResultSet resultSet;

    EmployeeQueries() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            selectAll = connection.prepareCall("CALL get_all_employee();");
            deleteEmployee = connection.prepareCall("CALL delete_employee(?);");
            registerNewEmployee = connection.prepareCall("CALL register_employee(?,?,?,?,?,?,?);");
            addEmployee = connection.prepareCall("CALL add_employee(?,?,?,?,?,?);");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployee() {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            List<Employee> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)));
            }
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int addEmployee(String FirstName, String LastName, String Email, String Profession, String Gender,
            String PhoneNumber) {
        try {
            addEmployee.setString(1, FirstName);
            addEmployee.setString(2, LastName);
            addEmployee.setString(3, Email);
            addEmployee.setString(4, Profession);
            addEmployee.setString(5, Gender);
            addEmployee.setString(6, PhoneNumber);

            return addEmployee.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int registerEmployee(String FirstName, String LastName, String Email, String Profession, String Gender,
            String PhoneNumber,
            String Password) {
        try {
            registerNewEmployee.setString(1, FirstName);
            registerNewEmployee.setString(2, LastName);
            registerNewEmployee.setString(3, Email);
            registerNewEmployee.setString(4, Profession);
            registerNewEmployee.setString(5, Gender);
            registerNewEmployee.setString(6, PhoneNumber);
            registerNewEmployee.setString(7, Password);

            return registerNewEmployee.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int authenticate(String email, String password) {
        try (ResultSet resultSet = selectAll.executeQuery()) {
            int returned = 0;
            while (resultSet.next()) {
                if (resultSet.getString(3).equals(email) && resultSet.getString(7).equals(password)) {
                    returned = 1;
                    break;
                } else {
                    returned = 0;
                }

            }
            return returned;
        } catch (Exception e) {
            return 0;
        }
    }

    public int deleteEmployee(String fname) {
        try {
            deleteEmployee.setString(1, fname);

            return deleteEmployee.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
