package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecifications {

    public Specification<Book> hasTitleLike(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            String lowercaseQuery = "%" + query.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), lowercaseQuery);
        };
    }

    public Specification<Book> hasAuthorNameLike(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Book, Author> join = root.join("authors");
            String lowerCaseQuery = query.toLowerCase();

            Predicate firstNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(join.get("firstName")), "%" + lowerCaseQuery + "%");
            Predicate lastNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(join.get("lastName")), "%" + lowerCaseQuery + "%");

            return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
        };
    }
}
