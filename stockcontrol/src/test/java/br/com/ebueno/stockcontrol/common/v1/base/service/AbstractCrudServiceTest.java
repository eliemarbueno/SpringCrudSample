package br.com.ebueno.stockcontrol.common.v1.base.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;

import br.com.ebueno.stockcontrol.common.v1.base.repository.BaseRepository;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IIdAsUUID;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IName;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebClient
@ActiveProfiles("test")
public abstract class AbstractCrudServiceTest <E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

	@Autowired
	protected AbstractCrudService<E, CreateDTO, UpdateDTO, ResponseDTO, ID> service;
	@Autowired
	protected BaseRepository<E, ID> repository;

	protected boolean hasUniqueItem = false;
	protected boolean uniqueItemIsCreated = false;
	protected int pageSize = 2;
	protected int pageNumber = 0;
	protected String sortBy = "id";
	protected String sortDirection = "ASC";

	protected CreateDTO createDTOUnique;
	protected CreateDTO createDTOBefore;
	protected CreateDTO createDTO;
	protected UpdateDTO updateDTO;
	
	protected ResponseDTO responseBeforeDTO;
	protected ResponseDTO responseCreateDTO;
	protected ResponseDTO responseCreateUniqueDTO;
	protected ResponseDTO responseUpdateDTO;
	protected ID id;
	protected Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

	@DisplayName("Should One new item be inserted before all tests")
	@BeforeAll
	protected void BeforeAll() throws Exception {
		if (hasUniqueItem && !uniqueItemIsCreated){
			responseCreateUniqueDTO = service.create(createDTOUnique);
			uniqueItemIsCreated = true;
		}

		responseBeforeDTO = service.create(createDTOBefore);
		if (responseBeforeDTO instanceof IIdAsUUID) {
			id = (ID) ((IIdAsUUID) responseBeforeDTO).getId();
			assertNotNull(id, "ID should not be null after creation");
		} else {
			fail("ResponseDTO does not implement IIdAsUUID");
		}
		if (responseBeforeDTO instanceof IName) {
			assertNotNull(((IName) responseBeforeDTO).getName(), "Name should not be null after creation");
		} else {
			fail("ResponseDTO does not implement IName");
		}
	}


	@DisplayName("Should create a new item with success and with valid data provided")
	@Test
	void validateBeforeCreateWithSuccess() {
		try {
			responseCreateDTO = service.create(createDTO);
			assertNotNull(responseCreateDTO, "ResponseDTO should not be null after creation");

			if (responseCreateDTO instanceof IIdAsUUID) {
				assertNotNull(((IIdAsUUID) responseCreateDTO).getId(), "ResponseDTO ID should not be null");
			}
			if (responseCreateDTO instanceof IName) {
				assertNotNull(((IName) responseCreateDTO).getName(), "ResponseDTO Name should not be null");
			} else {
				fail("ResponseDTO does not implement IName");
			}
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should update an item with success and with valid data provided")
	@Test
	void validateBeforeUpdateWithSuccess() {
		try {
			responseUpdateDTO = service.update(id, updateDTO);
			assertNotNull(responseUpdateDTO, "ResponseDTO should not be null after creation");

			if (responseUpdateDTO instanceof IIdAsUUID) {
				assertNotNull(((IIdAsUUID) responseUpdateDTO).getId(), "ResponseDTO ID should not be null");
			}
			if (responseUpdateDTO instanceof IName) {
				assertNotNull(((IName) responseUpdateDTO).getName(), "ResponseDTO Name should not be null");
			} else {
				fail("ResponseDTO does not implement IName");
			}
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should delete an item with success and with valid data provided")
	@Test
	void validateBeforeDeleteWithSuccess() {
		try {
			service.delete(id);
			var responseDeleted = service.findById(id);
			assertNull(responseDeleted, "ResponseDTO should be empty after deletion");
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should find an item by ID with success and with valid data provided")
	@Test
	void validateBeforeFindByIdWithSuccess() {
		try {
			var responseFound = service.findById(id);
			assertNotNull(responseFound, "ResponseDTO should not be null when finding by ID");

		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should find all pageable items with success and with valid data provided")
	@Test
	void validateBeforeFindAllPageableWithSuccess() {
		try {
			var responseAll = service.findAll(pageable);
			assertNotNull(responseAll, "ResponseDTO should not be null when finding all items");
			assertNotNull(responseAll.getContent(), "ResponseDTO content should not be null");
			assertEquals(responseAll.getSize(), pageSize, "ResponseDTO size should match the page size");
			assertEquals(responseAll.getNumber(), pageNumber, "ResponseDTO number should match the page number");
			assertEquals(responseAll.getSort().toString(), Sort.by(sortBy).ascending().toString(), "ResponseDTO sort should match the expected sort");
			
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should find all items with success and with valid data provided")
	@Test
	void validateBeforeFindAllWithSuccess() {
		try {
			var responseAll = service.findAll();
			assertNotNull(responseAll, "ResponseDTO should not be null when finding all items");
			assertEquals(responseAll.size(), repository.count(), "ResponseDTO size should match the repository count");
			
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should find unique item by name with success and with valid data provided")
	@Test
	void validateBeforeFindUniqueByNameWithSuccess() {
		try {
			if (!hasUniqueItem && uniqueItemIsCreated && responseCreateUniqueDTO != null) {
				assertNotNull(responseCreateUniqueDTO, "Unique item should be created before this test");
				if (responseCreateUniqueDTO instanceof IIdAsUUID) {
					assertNotNull(((IIdAsUUID) responseCreateUniqueDTO).getId(), "Unique item ID should not be null");
				}
				if (responseCreateUniqueDTO instanceof IName) {
					assertNotNull(((IName) responseCreateUniqueDTO).getName(), "Unique item Name should not be null");
				} else {
					fail("Unique item ResponseDTO does not implement IName");
				}
			} else {
				assertFalse(hasUniqueItem, "Unique item is required to validate this test");				
			}


			// var responseUnique = service.findUniqueByName(createDTOUnique.getName());
			// assertNotNull(responseUnique, "ResponseDTO should not be null when finding unique item by name");
			// if (responseUnique instanceof IIdAsUUID) {
			// 	assertNotNull(((IIdAsUUID) responseUnique).getId(), "ResponseDTO ID should not be null");
			// }
			// if (responseUnique instanceof IName) {
			// 	assertNotNull(((IName) responseUnique).getName(), "ResponseDTO Name should not be null");
			// } else {
			// 	fail("ResponseDTO does not implement IName");
			// }
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}
}


