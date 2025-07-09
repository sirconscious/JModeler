    import java.io.IOException;
    import java.sql.DatabaseMetaData;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Stack;

    public class QueryBuilder  extends DBConnection{

        //insertOne
        public  void insertOne(String tableName , Stack<String> fields  ,Stack<String> values)throws SQLException {
            if (tableName == null || fields == null || values == null || fields.isEmpty() || values.isEmpty()) {            System.err.println("the table name , fields and values are required to insert");
            } else if (fields.size() != values.size()) {
                System.err.println("the fields and values dont match");
            } else if (!tableExists(tableName)) {
                System.err.println("the table doesnt exist");
            } else {
                StringBuilder cols = new StringBuilder("(");
                for (int i = 0; i < fields.size(); i++) {
                    cols.append(fields.get(i));
                    if (i < fields.size() - 1) {
                        cols.append(", ");
                    } else {
                        cols.append(")");
                    }
                }
                String sql = "INSERT INTO "+tableName+" "+cols+" VALUES "+"("+"?,".repeat(values.size()-1)+"?"+")";
                try (PreparedStatement stmt = connect().prepareStatement(sql)) {
                    for (int i = 0; i < values.size(); i++) {
                        stmt.setString(i + 1, values.get(i));
                    }
                    stmt.executeUpdate();
                }
            }
        }
        //read all
        public void readAll(String tableName) throws SQLException{
            if (tableExists(tableName)){
            String sql = "select * from "+tableName;
            try (PreparedStatement stmt = connect().prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("name") +
                            ", Email: " + rs.getString("email"));
                }
            }
        }else {
                System.err.println("the table doesnt exist");
            }
            }

    }
