package com.distribuida.rest;

import com.distribuida.config.DbConfig;
import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/books")

public class BookRest {
    @Inject private BookService bookService;
@Inject private DbConfig dbConfig;
    @GET
    @Path("{id}")

    @Produces(MediaType.APPLICATION_JSON)
    public Response findBy(@PathParam("id") Integer id){

        return  Response.ok(bookService.findById(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {

        return Response.ok(bookService.findAll()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Book book) {
        Book newBook = bookService.create(book);
        return Response.ok(newBook).status(Response.Status.CREATED).build();
    }
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, Book book) {
         book.setId(id);
         return Response.ok(bookService.update(id, book)).build();

    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {

        return Response.ok(bookService.delete(id)).build();
    }


}
