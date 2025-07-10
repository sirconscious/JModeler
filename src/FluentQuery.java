import java.sql.SQLException;
import java.util.List;

public class FluentQuery extends QueryBuilder {

    private String tableName;
    private static final String tableNameNotProvided = "No table name is provided";

    // Execution type enum
    private enum QueryType {
        INSERT_ONE, INSERT_MANY, READ_ALL, READ_ONE, DELETE, UPDATE
    }

    private QueryType currentQueryType;

    // Parameters
    private List<String> fields;
    private List<String> values;
    private List<List<String>> multipleValues;
    private String whereCol;
    private String operator;
    private String whereValue;
    private List<String> updateFields;
    private List<String> updateValues;

    // Results
    private List<String> lastRow;
    private List<List<String>> lastRows;

    // Set table
    public FluentQuery table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public FluentQuery insertOne(List<String> fields, List<String> values) {
        this.fields = fields;
        this.values = values;
        this.currentQueryType = QueryType.INSERT_ONE;
        return this;
    }

    public FluentQuery insertMany(List<String> fields, List<List<String>> values) {
        this.fields = fields;
        this.multipleValues = values;
        this.currentQueryType = QueryType.INSERT_MANY;
        return this;
    }

    public FluentQuery readAll() {
        this.currentQueryType = QueryType.READ_ALL;
        return this;
    }

    public FluentQuery readOne() {
        this.currentQueryType = QueryType.READ_ONE;
        return this;
    }

    public FluentQuery delete(String col, String operator, String value) {
        this.whereCol = col;
        this.operator = operator;
        this.whereValue = value;
        this.currentQueryType = QueryType.DELETE;
        return this;
    }

    public FluentQuery update(String whereCol, String operator, String whereValue, List<String> updateFields, List<String> updateValues) {
        this.whereCol = whereCol;
        this.operator = operator;
        this.whereValue = whereValue;
        this.updateFields = updateFields;
        this.updateValues = updateValues;
        this.currentQueryType = QueryType.UPDATE;
        return this;
    }

    // Execute the stored operation
    public FluentQuery execute() throws SQLException {
        if (this.tableName == null) {
            System.err.println(tableNameNotProvided);
            return this;
        }

        switch (currentQueryType) {
            case INSERT_ONE -> super.insertOne(tableName, fields, values);
            case INSERT_MANY -> super.insertMany(tableName, fields, multipleValues);
            case READ_ALL -> lastRows = super.readAll(tableName);
            case READ_ONE -> lastRow = super.readOne(tableName);
            case DELETE -> super.delete(tableName, whereCol, operator, whereValue);
            case UPDATE -> super.update(tableName, whereCol, operator, whereValue, updateFields, updateValues);
            default -> System.err.println("No query type set.");
        }

        return this;
    }

    // Result getters
    public List<String> getResult() {
        return lastRow;
    }

    public List<List<String>> getResults() {
        return lastRows;
    }
}
