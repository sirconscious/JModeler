    import java.io.IOException;
    import java.sql.DatabaseMetaData;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Stack;

    public class QueryBuilder  extends DBConnection{

        private final ArrayList<String> operators = new ArrayList<>(){{
            add("=");
            add(">");
            add("<");
            add("!=");
        }};
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
        //read one  it will return the first
        public List<String> readOne(String tableName) throws SQLException {
            if (!tableExists(tableName)) {
                System.err.println("Table does not exist: " + tableName);
                return new ArrayList<>();
            }

            List<String> cols = getColumnNames(tableName);
            List<String> output = new ArrayList<>();

            String sql = "SELECT * FROM " + tableName + " LIMIT 1";

            try (PreparedStatement stmt = connect().prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    for (String col : cols) {
                        output.add(rs.getString(col));
                    }
                }
            }

            return output;
        }
        //Delete
        public void delete(String tableName, String col, String operator, String value) throws SQLException {
            if (tableExists(tableName)) {
                List<String> cols = getColumnNames(tableName);
                if (cols.contains(col)) {
                    if (operators.contains(operator)) {
                        String sql = "DELETE FROM " + tableName + " WHERE " + col + " " + operator + " ?";
                        try (PreparedStatement stmt = connect().prepareStatement(sql)) {
                            stmt.setString(1, value);
                            int affected = stmt.executeUpdate();
                            System.out.println("Deleted rows: " + affected);
                        }
                    } else {
                        System.err.println("The operator provided is invalid");
                    }
                } else {
                    System.err.println("The column is invalid");
                }
            }
        }

        //update
        public void update(String tableName, String whereCol, String operator, String whereValue, List<String> updateFields, List<String> updateValues) throws SQLException {
            // Validation
            if (tableName == null || updateFields == null || updateValues == null || updateFields.isEmpty() || updateValues.isEmpty()) {
                System.err.println("Table name, update fields, and values are required.");
                return;
            }

            if (updateFields.size() != updateValues.size()) {
                System.err.println("Fields and values count do not match.");
                return;
            }

            if (!tableExists(tableName)) {
                System.err.println("Table does not exist: " + tableName);
                return;
            }

            List<String> tableCols = getColumnNames(tableName);

            for (String field : updateFields) {
                if (!tableCols.contains(field)) {
                    System.err.println("Invalid column in update fields: " + field);
                    return;
                }
            }

            if (!tableCols.contains(whereCol)) {
                System.err.println("Invalid WHERE column: " + whereCol);
                return;
            }

            if (!operators.contains(operator)) {
                System.err.println("Invalid operator: " + operator);
                return;
            }

            // Build SET clause: field1 = ?, field2 = ?, ...
            StringBuilder setClause = new StringBuilder();
            for (int i = 0; i < updateFields.size(); i++) {
                setClause.append(updateFields.get(i)).append(" = ?");
                if (i < updateFields.size() - 1) {
                    setClause.append(", ");
                }
            }

            // Build SQL statement
            String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereCol + " " + operator + " ?";

            try (PreparedStatement stmt = connect().prepareStatement(sql)) {
                // Set update values
                int paramIndex = 1;
                for (String value : updateValues) {
                    stmt.setString(paramIndex++, value);
                }
                // Set WHERE value
                stmt.setString(paramIndex, whereValue);

                int affectedRows = stmt.executeUpdate();
                System.out.println("Updated rows: " + affectedRows);
            }
        }

    }
