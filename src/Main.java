import java.sql.SQLException;
import java.util.Stack;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        db.connect();
        Stack<String> cols = new Stack<>();
        cols.push("id");
        cols.push("name");
        cols.push("email");
        Stack<String> vals = new Stack<>();
        vals.push("6") ;
        vals.push("mehdi");
        vals.push("mehdi@mehdi.com");
        try {
        new QueryBuilder().insertOne("users",cols,vals);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(db.getColumnNames("users"));

        }catch (SQLException e){

        }
    }

}