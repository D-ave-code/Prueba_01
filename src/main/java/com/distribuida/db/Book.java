package com.distribuida.db;

import lombok.Data;

@Data
public class Book {

    private Integer id;
    private String isbn;
    private String title;
    private String author;
    private Double price ;
}
