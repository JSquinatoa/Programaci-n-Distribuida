package com.programacion.distribuida.authors.repo;


import com.programacion.distribuida.authors.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class AuthorRepository implements PanacheRepositoryBase<Author, Integer> {

    public List<Author> findbyBook(String isbn){
        return this.find(
                "Select o.author From BookAuthor o where o.id.bookIsbn = ?1", isbn
        ).list();
    }

}
