package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LivreRepository extends JpaRepository<Livre, String> {

    @Query("SELECT l FROM Livre l LEFT JOIN FETCH l.auteurs WHERE l.isbn = :isbn")
    Optional<Livre> findByIsbnWithAuteurs(String isbn);
}
