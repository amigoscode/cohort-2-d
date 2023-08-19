package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecifications {

    public Specification<Book> hasTitleLike(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + query + "%");
    }

    public Specification<Book> hasAuthorNameLike(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Book, Author> join = root.join("authors");
            Predicate firstNamePredicate = criteriaBuilder.like(join.get("firstName"), "%" + query + "%");
            Predicate lastNamePredicate = criteriaBuilder.like(join.get("lastName"), "%" + query + "%");
            return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
        };
    }
}
