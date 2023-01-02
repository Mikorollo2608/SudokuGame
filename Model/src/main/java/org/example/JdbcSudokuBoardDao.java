package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.example.exceptions.DaoException;
import org.example.exceptions.JdbcDaoRecordExitsException;
import org.example.exceptions.JdbcNameException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionsMessages");

    private static final Logger logger = Logger.getLogger(JdbcSudokuBoardDao.class.getName());
    private Connection conn;

    private String name = null;

    public JdbcSudokuBoardDao() throws DaoException {
        String dbUrl = "jdbc:derby:Database;create=true";
        try {
            conn = DriverManager.getConnection(dbUrl);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(resourceBundle.getString("JdbcDaoExceptionConnecting"));
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE boards( "
                    + "boardid INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "CONSTRAINT UNQIUE_name UNIQUE (name), "
                    + "CONSTRAINT PK_boards PRIMARY KEY (boardid))");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                logger.info(resourceBundle.getString("JdbcDaoMessageTableExists"));
            } else {
                logger.info(e.getSQLState());
                throw new DaoException(resourceBundle.getString("JdbcDaoExceptionCreating")
                        + " " + e.getSQLState());
            }
        }
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE fields( "
                    + "boardid INT NOT NULL, "
                    + "x INT NOT NULL, "
                    + "y INT NOT NULL, "
                    + "val INT NOT NULL, "
                    + "CONSTRAINT FK_fields_boards FOREIGN KEY (boardid) "
                    + "REFERENCES boards(boardid))");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                logger.info(resourceBundle.getString("JdbcDaoMessageTableExists"));
            } else {
                logger.info(e.getSQLState());
                throw new DaoException(resourceBundle.getString("JdbcDaoExceptionCreating")
                        + " " + e.getSQLState());
            }
        }
        logger.info(resourceBundle.getString("JdbcDaoMessageDatabaseEstablished"));
    }

    @Override
    public SudokuBoard read() throws DaoException {
        if (name == null) {
            throw new JdbcNameException(resourceBundle.getString("JdbcDaoExceptionNameNotSet"));
        }

        try (Statement stmt = conn.createStatement();
            ResultSet fieldsRs = stmt.executeQuery(
                    String.format("SELECT x,y,val FROM fields f JOIN boards b "
                            + "ON f.boardid = b.boardid WHERE name = '%s'",name))) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
            while (fieldsRs.next()) {
                sudokuBoard.set(fieldsRs.getInt("y"),
                        fieldsRs.getInt("x"), fieldsRs.getInt("val"));
            }
            return sudokuBoard;
        } catch (SQLException e) {
            logger.info(e.getSQLState());
            throw new DaoException(resourceBundle.getString("JdbcDaoExceptionRead"));
        }
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        if (name == null) {
            throw new JdbcNameException(resourceBundle.getString("JdbcDaoExceptionNameNotSet"));
        }
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(String.format("INSERT INTO boards(name) VALUES ('%s')",name),
                    Statement.RETURN_GENERATED_KEYS);
            int id = -1;
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
                for (int row = 0; row < 9; row++) {
                    for (int column = 0; column < 9; column++) {
                        stmt.execute(String.format("INSERT INTO fields(boardid,x,y,val) "
                                + "VALUES (%d,%d,%d,%d)",id,column,row,obj.get(row,column)));
                    }
                }
                conn.commit();
            } catch (SQLException e)  {
                throw new DaoException(resourceBundle.getString("JdbcExceptionRetrievingKey"));
            }
        } catch (SQLException e) {
            //23505 record with the same unqique value exists
            if (e.getSQLState().equals("23505")) {
                throw new JdbcDaoRecordExitsException(
                        resourceBundle.getString("JdbcDaoRecordExitsException"));
            }
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DaoException(resourceBundle.getString("JdbcDaoExceptionRollback"));
            }
        }
    }

    public ArrayList<String> getNames() throws DaoException {
        ArrayList<String> names = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM boards")) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new DaoException(resourceBundle.getString("JdbcDaoExceptionGetNames"));
        }
        return names;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void close() throws DaoException {
        try {
            conn.commit();
            conn.close();
            DriverManager.getConnection("jdbc:derby:Database;shutdown=true");
        } catch (SQLException e) {
            //08006 is code for successful shutdown
            if (!e.getSQLState().equals("08006")) {
                throw new DaoException(resourceBundle.getString("JdbcDaoExceptionClosing"));
            }
        }
        logger.info(resourceBundle.getString("JdbcDaoMessageDatabaseClosed"));
    }
}
