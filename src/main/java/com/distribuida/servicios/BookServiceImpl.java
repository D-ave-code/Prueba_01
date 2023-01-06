package com.distribuida.servicios;

import com.distribuida.config.DbConfig;
import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import org.jdbi.v3.core.Handle;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class BookServiceImpl implements BookService{
    @Inject
    private DbConfig dbConfig;
    public List<Book> findAll() {
        List<Book> books1 = null;
        String sql = "SELECT * FROM books";
        try (Handle handle = dbConfig.test()) {
            books1 = handle.createQuery(sql)
                    .mapToBean(Book.class)
                    .list();
        }
        return books1;
    }

    public JsonObject findById(Integer id) {
        Book bookd = null;
        JsonObject json;
        String sql = "SELECT * FROM books where id = " + id;
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        List<Book> books1=findAll();
        List<Book> books = null;
        List<Book> books2 = null;
        try (Handle handle = dbConfig.test()) {
            books =  handle.createQuery(sql)
                    .mapToBean(Book.class)
                    .list();
        }

        if(books.size() > 0){
            arrayBuilder.add(Json.createObjectBuilder().add("id", books.get(0).getId()).add("isbn", books.get(0).getIsbn())
                    .add("title", books.get(0).getTitle()).add("author", books.get(0).getAuthor())
                    .add("price", books.get(0).getPrice()).build());
            bookd = books.get(0);
            json = Json.createObjectBuilder()
                    .add("existe", "SI")
                    .add("data", arrayBuilder.build())

                    .build();
        }else {
            json = Json.createObjectBuilder()
                    .add("existe", "NO")
                    .build();
        }

        return json;

    }

    public Book create(Book book) {

        String sql = "INSERT INTO \"books\" ( \"isbn\",\"title\",\"author\",\"price\") VALUES (:isbn,:title,:author, :price)" ;
        try (Handle handle = dbConfig.test()) {
            handle.createUpdate(sql)
                    .bindBean(book)
                    .execute();
        }
    return book;
    }

    public JsonObject update(Integer id, Book book) {
        JsonObject json;
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int count = 0;
        JsonObject b = findById(id);
        System.out.println();
        if(b.get("existe").toString().replaceAll("\"","").equals("SI")){
            JsonArray ar = (JsonArray) b.get("data");
            JsonObject c = (JsonObject) ar.get(0);
            int d = Integer.parseInt(c.get("id").toString());
            if (d == id ) {
                try (Handle handle = dbConfig.test()) {
                    count = handle.createUpdate("UPDATE books set isbn = :isbn  , title = :title, author= :author, price = :price WHERE id = " + book.getId())

                            .bind("isbn", book.getIsbn())
                            .bind("title", book.getTitle())
                            .bind("author", book.getAuthor())
                            .bind("price", book.getPrice())
                            .execute();
                }
                if (count > 0) {

                    arrayBuilder.add(Json.createObjectBuilder().add("id", book.getId()).add("isbn", book.getIsbn())
                            .add("title", book.getTitle()).add("author", book.getAuthor())
                            .add("price", book.getPrice()).build());
                    json = Json.createObjectBuilder()
                            .add("Editado con exito", arrayBuilder.build())
                            .build();
                } else {
                    json = Json.createObjectBuilder()
                            .add("msg", "No existe registros")
                            .build();
                }



            } else {
                json = Json.createObjectBuilder()
                        .add("msg", "No existe registros")
                        .build();
            }
        }else{
            json = Json.createObjectBuilder()
                    .add("msg", "No existe registros")
                    .build();
        }

        return json;
    }

    public JsonObject delete(Integer id) {
        JsonObject json = null;
        String sql = "DELETE FROM books WHERE id = :id" ;
        try (Handle handle = dbConfig.test()) {
           int count = handle.createUpdate(sql)
                    .bind("id", id)
                    .execute();
            if (count>0){
                json = Json.createObjectBuilder()
            .add("msg", "Borrado con exito")
            .build();
            }else {
            json = Json.createObjectBuilder()
            .add("msg", "No existe registros")
            .build();
        }
        }
        return json;
    }
}
