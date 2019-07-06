package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDao extends AbstractDao {

    private static final String LOAD_ALL_QUERY = "SELECT * FROM books";
    private static final String LOAD_ALL_BY_ID_QUERY = "SELECT * FROM books WHERE id=?";
    private static final String CREATE_QUERY = "INSERT INTO books(isbn, title, author, publisher, type) VALUES(?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE books SET isbn=?, title=?, author=?, publisher=?, type=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id=?";


    @Override
    protected Model newFromResultSet(ResultSet rs) throws SQLException {
        return new Book(
                rs.getLong("id"),
                rs.getString("isbn"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getString("type"));
    }

    @Override
    protected String getLoadAllQuery() {
        return LOAD_ALL_QUERY;
    }

    @Override
    protected String getLoadAllByIdQuery() {
        return LOAD_ALL_BY_ID_QUERY;
    }

    @Override
    protected PreparedStatement saveNewStatement(Connection conn, Model book) throws SQLException {
        String[] generatedColumns = {"id"};

        PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, generatedColumns);
        ps.setString(1, ((Book) book).getIsbn());
        ps.setString(2, ((Book) book).getTitle());
        ps.setString(3, ((Book) book).getAuthor());
        ps.setString(4, ((Book) book).getPublisher());
        ps.setString(5, ((Book) book).getType());

        return ps;
    }

    @Override
    protected PreparedStatement updateExistingStatement(Connection conn, Model book) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY);
        ps.setString(1, ((Book) book).getIsbn());
        ps.setString(2, ((Book) book).getTitle());
        ps.setString(3, ((Book) book).getAuthor());
        ps.setString(4, ((Book) book).getPublisher());
        ps.setString(5, ((Book) book).getType());
        ps.setLong(6, ((Book) book).getId());

        return ps;
    }

    @Override
    protected PreparedStatement deleteStatement(Connection conn, Model book) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(DELETE_QUERY);
        ps.setLong(1, book.getId());

        return ps;
    }
}
