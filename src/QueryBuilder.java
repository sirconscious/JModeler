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
        public  void insertOne(String tableName , List<String> fields  ,List<String> values)throws SQLException {
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
        //insert Many
        public void insertMany(String tableName , List<String> fields , List<List<String>> values ) throws SQLException {
                for (List<String> value : values){
                    insertOne(tableName , fields , value);
                }
        }
        //read all
        public List<List<String>> readAll(String tableName) throws SQLException {
            List<List<String>> output = new ArrayList<>();

            if (!tableExists(tableName)) {
                System.err.println("Table does not exist: " + tableName);
                return output; // return empty list instead of null
            }

            String sql = "SELECT * FROM " + tableName;

            try (PreparedStatement stmt = connect().prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                List<String> cols = getColumnNames(tableName);

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    for (String col : cols) {
                        row.add(rs.getString(col));
                    }
                    output.add(row);
                }
            }

            return output;
        }

    }
