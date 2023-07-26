package com.amigoscode.cohort2d.onlinebookstore.service;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EntityPersistenceService {

    // Generic method to handle Category & Author entities
    // Get existing entity (id present) or create new
    public <Entity extends EntityIdentifiers> Set<Entity> getOrCreateEntities(
            Set<Entity> entities, // Category or Author entities
            JpaRepository<Entity, Long> repository // corresponding categoryRepository / authorRepository
    ) {
        // Set to hold the categories or authors to returned and set to book
        Set<Entity> persistedEntities = new HashSet<>();

        // iterate through the entities
        for (Entity entity : entities) {
            if (entity.getId() != null) {
                // if id present find in repo and add to hashset
                persistedEntities.add(repository.findById(entity.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(entity.getEntityName() + " with id [%s] not found.".formatted(entity.getId()))));
            } else {
                // save entity to repository
                persistedEntities.add(repository.save(entity));
            }
        }
        return persistedEntities;
    }
}
