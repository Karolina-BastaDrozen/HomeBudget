import data.Transaction;
import data.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    private static final String URL = "jdbc:mysql://localhost:3306/budget?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";

    private Connection connection;

    public TransactionDao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, "root", "root");
        } catch (SQLException e) {
            System.err.println("Unable to make a connection");
        } catch (ClassNotFoundException e) {
            System.err.println("Controller not found");
        }
    }

    public void save(Transaction transaction){
        String sql = "INSERT INTO transactions(transaction_type, transaction_description, amount, transaction_date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, transaction.getType());
            statement.setString(2, transaction.getDescription());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setString(4, transaction.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Saving record failed");
            e.printStackTrace();
        }
    }

    public List<Transaction> read(Type type){
        String sql = "SELECT * FROM transactions WHERE transaction_type = ?";
        List<Transaction> transactions = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type.name());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getLong("id"));
                transaction.setType(resultSet.getString("transaction_type"));
                transaction.setDescription(resultSet.getString("transaction_description"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setDate(resultSet.getString("transaction_date"));
                transactions.add(transaction);
            }
            return transactions;
        }catch (SQLException e) {
            System.err.println("Reading record failed");
        }
        return null;
    }

    public void update(Transaction transaction){
        String sql = "UPDATE transactions SET transaction_type = ?, transaction_description = ?, amount = ?, transaction_date = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, transaction.getType());
            statement.setString(2, transaction.getDescription());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setString(4, transaction.getDate());
            statement.setLong(5, transaction.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Updating data failed");
        }
    }

    public void delete(long id){
        String sql = "DELETE FROM transactions WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Deleting record failed");
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Closing connection failed");
        }
    }
}