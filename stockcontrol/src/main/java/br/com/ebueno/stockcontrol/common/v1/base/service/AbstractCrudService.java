package br.com.ebueno.stockcontrol.common.v1.base.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ebueno.stockcontrol.common.v1.base.repository.BaseRepository;
import br.com.ebueno.stockcontrol.common.v1.util.MappingService;

/***
 * AbstractCrudService.java
 * This class provides a base implementation for CRUD operations
 * for entities of type T with identifier type ID.
 * It can be extended to provide specific CRUD operations for different
 * entities.
 */
public class AbstractCrudService<E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

	protected final BaseRepository<E, ID> repository;
	protected final ApplicationEventPublisher eventPublisher;
	protected final MappingService mappingService;

	private final Class<E> entityClass;
	private final Class<ResponseDTO> responseDTOClass;

	/***
	 * Constructor for AbstractCrudService.
	 * 
	 * @param repository       the repository for the entity type.
	 * @param eventPublisher   the event publisher for application events.
	 * @param mappingService   the service for mapping between DTOs and entities.
	 * @param entityClass      the class of the entity type.
	 * @param responseDTOClass the class of the response DTO type.
	 */
	public AbstractCrudService(BaseRepository<E, ID> repository,
			ApplicationEventPublisher eventPublisher,
			MappingService mappingService,
			Class<E> entityClass,
			Class<ResponseDTO> responseDTOClass) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
		this.mappingService = mappingService;
		this.entityClass = entityClass;
		this.responseDTOClass = responseDTOClass;
	}

	/***
	 * Validates the CreateDTO before creating an entity.
	 * 
	 * @param createDTO the DTO containing the data to create the entity.
	 * @throws IllegalArgumentException if the CreateDTO is null or invalid.
	 */
	protected void validadeBeforeCreate(E Entity) {
		// Placeholder for validation logic before creating an entity
		// Actual implementation would depend on the specific requirements
		// and architecture of the application.
	}

	/***
	 * Set specific item on entity before create
	 * 
	 * @param entity
	 */
	protected void setBeforeCreate(E entity) {
	}

	/***
	 * Creates a new entity using the provided CreateDTO.
	 * 
	 * @param createDTO the DTO containing the data to create the entity.
	 * @return ResponseDTO containing the created item.
	 * @throws IllegalArgumentException if the CreateDTO is null or invalid.
	 */
	public ResponseDTO create(CreateDTO createDTO) {
		E entity = mappingService.map(createDTO, entityClass);
		setBeforeCreate(entity);
		validadeBeforeCreate(entity);
		E savedEntity = repository.save(entity);
		// PublichCreateEvent(savedEntity);
		return mappingService.map(savedEntity, responseDTOClass);
	}

	/***
	 * Validates the ID and UpdateDTO before updating an entity.
	 * 
	 * @param id        the identifier of the item to update.
	 * @param updateDTO the DTO containing the updated data.
	 * @throws IllegalArgumentException if the ID is null or does not match the ID
	 *                                  in UpdateDTO.
	 */
	protected void validateBeforeUpdate(ID id, E entity) {
		if (id == null || entity == null) {
			throw new IllegalArgumentException("ID and UpdateDTO must not be null");
		}

		// Placeholder for validation logic before updating an entity
		// Actual implementation would depend on the specific requirements
		// and architecture of the application.
	}

	/***
	 * Set specific item on entity before update
	 * 
	 * @param entity
	 */
	protected void setBeforeUpdate(E entity) {
	}

	/***
	 * Updates an existing entity with the provided ID and UpdateDTO.
	 * 
	 * @param id        the identifier of the item to update.
	 * @param updateDTO the DTO containing the updated data.
	 * @return ResponseDTO containing the updated item.
	 * @throws IllegalArgumentException if the ID is null or does not exist.
	 */
	public ResponseDTO update(ID id, UpdateDTO updateDTO) {
		E existingEntity = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Entity not found with ID: " + id));
		mappingService.merge(updateDTO, existingEntity);
		setBeforeUpdate(existingEntity);
		validateBeforeUpdate(id, existingEntity);
		E updatedEntity = repository.save(existingEntity);
		// PublichUpdateEvent(updatedEntity);
		return mappingService.map(updatedEntity, responseDTOClass);
	}

	/***
	 * Checks if an entity with the given ID exists in the repository.
	 * 
	 * @param id the identifier of the item to check.
	 * @return true if the entity exists, false otherwise.
	 */
	public boolean exists(ID id) {
		return repository.existsById(id);
	}

	/***
	 * Validates the ID before deleting an entity.
	 * 
	 * @param id the identifier of the item to delete.
	 * @throws IllegalArgumentException if the ID is null or does not exist.
	 */
	protected void validateBeforeDelete(ID id) {
		if (id == null) {
			throw new IllegalArgumentException("ID must not be null");
		}
		if (!exists(id)) {
			throw new IllegalArgumentException("Entity not found with ID: " + id);
		}
		// Placeholder for additional validation logic before deleting an entity
		// Actual implementation would depend on the specific requirements
		// and architecture of the application.
	}

	/***
	 * Deletes an item by its ID.
	 * 
	 * @param id the identifier of the item to delete.
	 * @throws IllegalArgumentException if the ID is null or does not exist.
	 */
	public void delete(ID id) {
		validateBeforeDelete(id);
		repository.deleteById(id);
		// PublichDeleteEvent(id);
	}

	/***
	 * Finds an item by its ID.
	 * 
	 * @param id the identifier of the item to find.
	 * @return ResponseDTO containing the found item, or null if not found.
	 */
	public ResponseDTO findById(ID id) {
		E entity = repository.findById(id).orElse(null);
		if (entity == null) {
			return null; // or throw an exception
		}
		return mappingService.map(entity, responseDTOClass);
	}

	/***
	 * Finds all items in the repository.
	 * 
	 * @return a List of ResponseDTO containing all items.
	 */
	public List<ResponseDTO> findAll() {
		List<E> entities = repository.findAll();
		return mappingService.mapList(entities, responseDTOClass);
	}

	/***
	 * Finds all items in the repository with pagination.
	 * 
	 * @param pageable the pagination information.
	 * @return a Page of ResponseDTO containing the items.
	 */
	public Page<ResponseDTO> findAll(Pageable pageable) {
		Page<E> entityPage = repository.findAll(pageable);
		return mappingService.mapPage(entityPage, responseDTOClass);
	}

	/***
	 * Counts the total number of items in the repository.
	 * 
	 * @return the total count of items.
	 */
	public long count() {
		return repository.count();
	}

}
