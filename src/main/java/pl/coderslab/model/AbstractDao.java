package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao implements DaoInterface {
    protected abstract Model newFromResultSet(ResultSet rs) throws SQLException;

    protected abstract String getLoadAllQuery();

    protected abstract String getLoadAllByIdQuery();

    protected abstract PreparedStatement saveNewStatement(Connection conn, Model item) throws SQLException;

    protected abstract PreparedStatement updateExistingStatement(Connection conn, Model item) throws SQLException;

    protected abstract PreparedStatement deleteStatement(Connection conn, Model item) throws SQLException;

    @Override
    public List<Model> loadAll() {
        List<Model> list = new ArrayList<>();
        try (Connection conn = DBUtill.getConnection();
             ResultSet rs = conn.prepareStatement(getLoadAllQuery()).executeQuery()) {
            while (rs.next())
                list.add(newFromResultSet(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Model loadById(Long id) {
        try (Connection conn = DBUtill.getConnection();
             PreparedStatement ps = conn.prepareStatement(getLoadAllByIdQuery())) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return newFromResultSet(rs);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Model item) {
        try (Connection conn = DBUtill.getConnection()) {

            if (item.getId() == null)
                saveNewToDb(conn, item);
            else
                updateExistingInDb(conn, item);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Model item) {
        try (Connection conn = DBUtill.getConnection()) {

            if (item.getId() != 0L) {
                try (PreparedStatement ps = deleteStatement(conn, item)) {
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                item.setId(0L);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveNewToDb(Connection conn, Model item) throws SQLException {
        try (PreparedStatement ps = saveNewStatement(conn, item)) {
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    item.setId(rs.getLong(1));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateExistingInDb(Connection conn, Model item) throws  SQLException {
        try (PreparedStatement ps = updateExistingStatement(conn, item)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
