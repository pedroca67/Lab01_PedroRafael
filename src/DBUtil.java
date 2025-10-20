import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static Properties props = new Properties();

    // Bloco estático para carregar o .env
    static {
        try (FileInputStream fis = new FileInputStream(".env")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo .env: " + e.getMessage());
            System.exit(1);
        }
    }

    // Método principal para obter a conexão
    public static Connection getConnection() throws SQLException {
        String host = props.getProperty("DB_HOST", "localhost");
        String port = props.getProperty("DB_PORT", "3306");
        String dbName = props.getProperty("DB_NAME");
        String user = props.getProperty("DB_USER");
        String pass = props.getProperty("DB_PASSWORD");
        String isoLevel = props.getProperty("DB_ISOLATION_LEVEL", "READ_COMMITTED");

        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

        Connection conn = DriverManager.getConnection(url, user, pass);

        // Desliga o AutoCommit para controlar transações manualmente
        conn.setAutoCommit(false);

        // Define o Nível de Isolamento
        int isolationLevel = mapIsolationLevel(isoLevel);
        conn.setTransactionIsolation(isolationLevel);

        System.out.println("[DBUtil] Conexão estabelecida. Nível: " + isoLevel);
        return conn;
    }

    // Mapeia o nome do .env para os valores inteiros do JDBC
    private static int mapIsolationLevel(String levelName) {
        switch (levelName.toUpperCase()) {
            case "READ_UNCOMMITTED":
                return Connection.TRANSACTION_READ_UNCOMMITTED;
            case "READ_COMMITTED":
                return Connection.TRANSACTION_READ_COMMITTED;
            case "REPEATABLE_READ":
                return Connection.TRANSACTION_REPEATABLE_READ;
            case "SERIALIZABLE":
                return Connection.TRANSACTION_SERIALIZABLE;
            default:
                return Connection.TRANSACTION_READ_COMMITTED;
        }
    }
}