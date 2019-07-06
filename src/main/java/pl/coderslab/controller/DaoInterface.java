package pl.coderslab.controller;

import org.springframework.ui.Model;

import java.util.List;

public interface DaoInterface {
    List<Model> loadAll();
    Model loadById(Long id);
    void save(Model object);
    void delete(Model object);
}
