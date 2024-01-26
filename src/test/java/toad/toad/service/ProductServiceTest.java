package toad.toad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toad.toad.data.dto.ProductSimpleDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Product;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.impl.ProductServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts() {
        Company company = new Company();
        company.setCompanyId(0);
        company.setCompanyName("SolidIT");
        company.setPassword("0000");

        // 가짜 Product 엔티티 리스트 생성
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductName("Product 1");
        product1.setProductPrice(50.0);
        product1.setCompany(company);

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setProductName("Product 2");
        product2.setProductPrice(30.0);
        product2.setCompany(company);

        List<Product> fakeProductList = Arrays.asList(product1, product2);

        System.out.println("Fake Product List:");
        for (Product product : fakeProductList) {
            System.out.println("Product ID: " + product.getProductId() + ", Product Name: " + product.getProductName() + ", Product Price: " + product.getProductPrice() + ", Company: " + product.getCompany().getCompanyName());
        }

        // ModelMapper에 대한 행동 설정
        when(modelMapper.map(product1, ProductSimpleDto.class)).thenReturn(
                new ProductSimpleDto(1, "Product 1", 50.0, "", 0, "SolidIT")
        );
        when(modelMapper.map(product2, ProductSimpleDto.class)).thenReturn(
                new ProductSimpleDto(2, "Product 2", 30.0, "", 0, "SolidIT")
        );

        // ProductRepository에 대한 행동 설정
        when(productRepository.findAll()).thenReturn(fakeProductList);

        // 테스트 수행
        List<ProductSimpleDto> result = productService.getAllProducts();

        // 결과 검증
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getProductId());
        assertEquals("Product 1", result.get(0).getProductName());
        assertEquals(50.0, result.get(0).getProductPrice());
        assertEquals("SolidIT", result.get(0).getCompanyName());
        assertEquals(2, result.get(1).getProductId());
        assertEquals("Product 2", result.get(1).getProductName());
        assertEquals(30.0, result.get(1).getProductPrice());
    }
}
