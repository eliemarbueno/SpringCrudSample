package br.com.ebueno.stockcontrol.domain.v1.category.service;

import java.util.UUID;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryCreateDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryResponseDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryUpdateDTO;
import br.com.ebueno.stockcontrol.common.v1.base.service.AbstractCrudServiceTest;
import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CategoryServiceTest extends AbstractCrudServiceTest<Category, CategoryCreateDTO, CategoryUpdateDTO, CategoryResponseDTO, UUID>	
{
    public CategoryServiceTest() {
        this.createDTOUnique = new CategoryCreateDTO("Unique Category", "This is a unique category for testing",
                "http://files.ebuenosoftware.com/test/category.webp");

        this.createDTOBefore = new CategoryCreateDTO("Before Category", "This is a category created before the unique one",
                "http://files.ebuenosoftware.com/test/category_before.webp");

        this.createDTO = new CategoryCreateDTO("Category", "This is a category for testing",
                "http://files.ebuenosoftware.com/test/category.webp");

        this.updateDTO = new CategoryUpdateDTO("Updated Category", "This is an updated category for testing",
                "http://files.ebuenosoftware.com/test/category_updated.webp");
    }

}
