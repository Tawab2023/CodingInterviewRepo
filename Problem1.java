import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class JsonStringReader {
    public static void main(String[] args) throws Exception {
        
        // Path of the Json file
        String jsonFile = "/path/to/the/data.json";
        
        // Reading the Json File
        ObjectMapper mapper = new ObjectMapper();
        Columns columns = mapper.readValue(new File(jsonFile), Columns.class);
        
        // Iterating through the list of column
        for (Column column : columns.getColumns()) {
            // Logging the Value retrieved from the Json file
            log.info(column.getOperator() + " " + column.getFieldName() + " " + column.getFieldValue());

            // Construct the SQL query based on the column data
            String query = "INSERT INTO column_table (operator, field_name, field_value) VALUES (" +
                                "'" + column.getOperator() + "', " +
                                      column.getFieldName() + ", " +
                                "'" + column.getFieldValue() + "')";
            
            System.out.println(query); // output the SQL query

        }
    }
}

// Columns class which contains a list of column

class Columns {
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}

// Column Class

class Column {

    // Attributes
    private String operator;
    private String fieldName;
    private String fieldValue;

    // Getters and Setters
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
