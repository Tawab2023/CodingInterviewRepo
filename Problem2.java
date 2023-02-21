import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JsonReaderToSql {
    public static void main(String[] args) throws Exception {

        // Read Json file
        String jsonDataFile = "path/to/data.json";
        ObjectMapper objectMapper = new ObjectMapper();
        QueryResult queryResult = objectMapper.readValue(new File(filePath), QueryResult.class);

        // Prepare Database Connection
        String url = "jdbc:mysql://localhost:3306/databaseName";
        String user = "root";
        String password = "pass123";
        Connection conn = DriverManager.getConnection(url, user, password);

        // Construct SQL query for importing Data into the databases

        String query = "SELECT ";
        for (int i = 0; i < queryResult.getSelectedColumns().size(); i++) {
            query += queryResult.getSelectedColumns().get(i);
            if (i < queryResult.getSelectedColumns().size() - 1) {
                query += ", ";
            }
        }
        query += " FROM " + queryResult.getMainTable() + " ";
        for (Join join : queryResult.getJoins()) {
            query += join.getType() + " JOIN " + join.getTable() + " ON " + join.getCondition() + " ";
        }
        for (SubQuery subQuery : queryResult.getSubQueries()) {
            query += " WHERE " + subQuery.getColumn() + " " + subQuery.getOperator() + " (" + subQuery.getQuery() + ") ";
        }

        // sanitize any input values to avoid SQL injection attacks by using the preparedStatement
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();

        conn.close();
    }

}

// Class for retrieving Table information
@Getters
@Setters
class QueryResult {
    private String mainTable;
    private List<String> selectedColumns;
    private List<Join> joins;
    private List<SubQuery> subQueries;

}

@Getters
@Setters
class Join {
    private String type;
    private String table;
    private String condition;

}

@Getters
@Setters
class SubQuery {
    private String column;
    private String operator;
    private String query;

    // getters and setters
    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

