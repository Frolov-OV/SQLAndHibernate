
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main {

    public static void main(String[] args) {

        String url = "JDBC:mysql://localhost:3306/mysite";

        try {
            Connection connection = DriverManager.getConnection(url, "root","********");
            Statement statement = connection.createStatement();
            //statement.execute("SELECT pl.course_name name, pl.subscription_date FROM PurchaseList pl WHERE pl.course_name = \"Веб-разработчик c 0 до PRO\" ORDER BY pl.subscription_date;");
            ResultSet resultSet = statement.executeQuery("SELECT pl.course_name Course_Name, " +
                            "(COUNT(pl.course_name) / TIMESTAMPDIFF(MONTH, MIN(subscription_date), MAX(subscription_date))) AVG_value " +
                            "FROM PurchaseList pl GROUP BY pl.course_name ORDER BY pl.subscription_date;");
            while (resultSet.next()){
                String resSet = resultSet.getString("Course_Name");
                String resSet2 = resultSet.getString("AVG_value");
                System.out.println(resSet + " - " + resSet2 + " per month");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
