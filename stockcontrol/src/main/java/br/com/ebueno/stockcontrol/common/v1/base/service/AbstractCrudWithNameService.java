package br.com.ebueno.stockcontrol.common.v1.base.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ebueno.stockcontrol.common.v1.base.repository.BaseWithNameRepository;
import br.com.ebueno.stockcontrol.common.v1.util.MappingService;
import jakarta.persistence.EntityNotFoundException;

/***
 * AbstractCrudWithNameService.java
 * This class extends AbstractCrudService to provide CRUD operations
 * for entities that have a name field.
 * It can be used to manage entities with a name attribute.
 */
public class AbstractCrudWithNameService <E, CreateDTO, UpdateDTO, ResponseDTO, ID> 
    extends AbstractCrudService <E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

    protected BaseWithNameRepository<E, ID> repository;

    /**
     * Constructor for AbstractCrudWithNameService.
     * @param repository the repository for the entity type
     * @param eventPublisher the event publisher for application events
     * @param mappingService the service for mapping between DTOs and entities
     * @param entityClass the class of the entity type
     * @param responseDTOClass the class of the response DTO type
     */
    public AbstractCrudWithNameService(BaseWithNameRepository<E, ID> repository,
                                        ApplicationEventPublisher eventPublisher,
                                        MappingService mappingService,
                                        Class<E> entityClass,
                                        Class<ResponseDTO> responseDTOClass) {
        super(repository, eventPublisher, mappingService, entityClass, responseDTOClass);
    }

    /**
     * Finds entities by name with pagination.
     * @param name the name to search for
     * @param pageable the pagination information
     * @return a page of response DTOs containing the found entities
     */
    public Page<ResponseDTO> findByName(String name, Pageable pageable) {
    	if (name == null || name.isBlank()) {
    		return findAll(pageable);
    	}
    	
        return repository.findAllByNameContainingIgnoreCase(name, pageable)
                .map(entity -> mappingService.map(entity, responseDTOClass));
    }

    /**
     * Lists entities by name.
     * @param name the name to search for
     * @return a list of response DTOs containing the found entities
     */
    public List<ResponseDTO> listByName(String name) {
        return repository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .map(entity -> mappingService.map(entity, responseDTOClass))
                .collect(Collectors.toList());
    }

    /**
     * Finds an entity by its name.
     * @param name the name of the entity to find
     * @return a response DTO containing the found entity
     * @throws EntityNotFoundException if no entity with the given name is found
     */
    public ResponseDTO findByName(String name) {
        E entity = repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Entity with name " + name + " not found"));
        return mappingService.map(entity, responseDTOClass);
    }

    /**
     * Finds entities by name containing the specified string, ignoring case.
     * @param name the string to search for in names
     * @param pageable the pagination information
     * @return a page of response DTOs containing the found entities
     */
    public Page<ResponseDTO> findAllByNameContainingIgnoreCase(String name, Pageable pageable) {
        return repository.findAllByNameContainingIgnoreCase(name, pageable)
                .map(entity -> mappingService.map(entity, responseDTOClass));
    }

    /**
     * Finds entities by name, ignoring case.
     * @param name the string to search for in names
     * @param pageable the pagination information
     * @return a page of response DTOs containing the found entities
     */
    public Page<ResponseDTO> findAllByName(String name, Pageable pageable) {
        return repository.findAllByName(name, pageable)
                .map(entity -> mappingService.map(entity, responseDTOClass));
    }

    /**
     * Finds entities by name with pagination, ignoring case.
     * @param name the string to search for in names
     * @param pageable the pagination information
     * @return a page of response DTOs containing the found entities
     */
    public List<ResponseDTO> listAllByName(String name) {
        return repository.findAllByName(name)
                .stream()
                .map(entity -> mappingService.map(entity, responseDTOClass))
                .collect(Collectors.toList());
    }

    /**
     * Lists entities by name containing the specified string, ignoring case.
     * @param name the string to search for in names
     * @return a list of response DTOs containing the found entities
     */
    public List<ResponseDTO> listAllByNameContainingIgnoreCase(String name) {
        return repository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .map(entity -> mappingService.map(entity, responseDTOClass))
                .collect(Collectors.toList());
    }
}


