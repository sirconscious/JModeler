import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        db.connect();
        // Instead of using Stack:
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("name");
        fields.add("email");

        List<String> value1 = new ArrayList<>();
        value1.add("8");
        value1.add("Khalid");
        value1.add("Khalid@Khalid.com");
          List<String> value2 = new ArrayList<>();
        value2.add("9");
        value2.add("Mehdi");
        value2.add("mehdi@mehdi.com");

        List<List<String>> vals=new ArrayList<>();
        vals.add(value1 ) ;
        vals.add( value2);
        try {
//        new QueryBuilder().insertOne("users",fields,values);
//            new QueryBuilder().insertMany("users" , fields,vals);
//            new QueryBuilder().delete("users", "id", "=", "1");
            List<String> fieldsToUpdate = List.of("name", "email");
            List<String> newValues = List.of("mehdi_bk", "mehdi@example.com");

            new QueryBuilder().update("users", "id", "=", "2", fieldsToUpdate, newValues);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(db.getColumnNames("users"));
            System.out.println(new  QueryBuilder().readAll("users"));
            System.out.println(new QueryBuilder().readOne("users"));
        }catch (SQLException e){

        }
    }

}