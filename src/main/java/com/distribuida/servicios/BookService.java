package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.json.JsonObject;

import java.util.List;

public interface BookService {
    JsonObject findById(Integer id);
    List<Book> findAll();
    Book create(Book book);
    JsonObject update(Integer id,Book book);
    JsonObject delete(Integer id);

}
