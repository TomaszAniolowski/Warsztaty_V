package pl.coderslab.model;


import java.util.List;

public interface DaoInterface {
    List<Model> loadAll();
    Model loadById(Long id);
    void save(Model object);
    void delete(Model object);
}
