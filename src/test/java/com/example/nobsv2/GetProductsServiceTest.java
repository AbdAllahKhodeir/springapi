package com.example.nobsv2;

import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.GetProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetProductsServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductsService getProductsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_products_exist_when_get_products_service_return_product_dto() {
        // Given
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product Name 1");
        product1.setDescription("Product Description which is at least 20 characters");
        product1.setPrice(9.99);

        Product product2 = new Product();
        product1.setId(2);
        product1.setName("Product Name 2");
        product1.setDescription("Product Description which is at least 20 characters");
        product1.setPrice(9.99);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        // Map products to DTOs for the expected result
        ProductDTO dto1 = new ProductDTO(product1);
        ProductDTO dto2 = new ProductDTO(product2);
        List<ProductDTO> expectedDTOs = Arrays.asList(dto1, dto2);

        // When
        ResponseEntity<List<ProductDTO>> response = getProductsService.execute(null);

        // Then
        assertEquals(ResponseEntity.ok(expectedDTOs), response);
    }

    @Test
    public void given_products_do_not_exist_when_get_products_service_return_empty_list() {
        // Given
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        // When & Then
        ResponseEntity<List<ProductDTO>> response = getProductsService.execute(null);
        List<ProductDTO> result = response.getBody();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }
}
