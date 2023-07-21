package com.amigoscode.cohort2d.onlinebookstore.service;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.DuplicateResourceException;
import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Component
public class EntityPersistenceService {

    // Generic method to handle Category & Author entities
    // Get existing entity (id present) or create new
    public <Dto extends EntityIdentifiers, Entity> Set<Entity> getOrCreateEntities(
            Set<Dto> dtos,
            JpaRepository<Entity, Long> repository,
            Function<Dto, Entity> dtoToModelMapper) {

        Set<Entity> entities = new HashSet<>();

        for (Dto dto : dtos) {

            if (dto.getId() != null) {

                // if id present find in repo and add to hashset
                entities.add(repository.findById(dto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(dto.getEntityName()+" with id [%s] not found.".formatted(dto.getId()))));
            } else {
                // convert dto to entity and save to repository
                Entity entity = dtoToModelMapper.apply(dto);

                try{
                    entities.add(repository.save(entity));
                } catch (DataIntegrityViolationException e) {
                    throw new DuplicateResourceException(
                            dto.getEntityName() + " with name [%s] already exists.".formatted(dto.getName()));
                }

            }
        }
        return entities;
    }
}
