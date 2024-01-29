package toad.toad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import toad.toad.data.dto.ProductDetailDto;
import toad.toad.data.dto.ProductSimpleDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Product;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.impl.ProductServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    private Company createCompany() {
        Company company = new Company();
        company.setCompanyId(1);
        company.setCompanyName("Test Company");
        company.setPassword("0000");
        return company;
    }

    private Product createProduct(int productId, String productName, double productPrice, Company company) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setCompany(company);
        return product;
    }

    private Product createDetailProduct(int productId, String productName, double productPrice, String productDesc, String imageUrl, Company company) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductDesc(productDesc);
        product.setImageUrl(imageUrl.getBytes());
        product.setCompany(company);
        return product;
    }


    @Test
    void saveProduct() throws Exception {
        // 가짜 데이터 생성
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setProductName("Test Product");
        productDetailDto.setProductPrice(100.0);
        productDetailDto.setProductDesc("Test Description");
        productDetailDto.setImageUrls("test_image_url");
        productDetailDto.setCompanyId(1);
        productDetailDto.setCompanyName("Test Company");

        Company fakeCompany = new Company();
        fakeCompany.setCompanyId(1);
        fakeCompany.setCompanyName("Test Company");

        Product fakeProduct = new Product();
        fakeProduct.setProductId(1);
        fakeProduct.setProductName("Test Product");
        fakeProduct.setProductPrice(100.0);
        fakeProduct.setProductDesc("Test Description");
        fakeProduct.setImageUrl("test_image_url".getBytes());
        fakeProduct.setCompany(fakeCompany);
        System.out.println("Company ID: " + fakeProduct.getCompany().getCompanyId());

        // ModelMapper 행동 설정
        when(modelMapper.map(productDetailDto, Product.class)).thenReturn(fakeProduct);

        // ProductService의 saveProduct 호출
        productService.saveProduct(productDetailDto);

        // 행동 검증
        verify(productRepository, times(1)).save(fakeProduct);
    }

    @Test
    void getAllProducts() {
        Company company = createCompany();

        Product product1 = createProduct(1, "Product 1", 50.0, company);
        Product product2 = createProduct(2, "Product 2", 30.0, company);

        List<Product> fakeProductList = Arrays.asList(product1, product2);

        System.out.println("Fake Product List:");
        for (Product product : fakeProductList) {
            System.out.println("Product ID: " + product.getProductId() + ", Product Name: " + product.getProductName() + ", Product Price: " + product.getProductPrice() + ", Company: " + product.getCompany().getCompanyName());
        }

        // ModelMapper에 대한 행동 설정
        when(modelMapper.map(product1, ProductSimpleDto.class)).thenReturn(
                new ProductSimpleDto(1, "Product 1", 50.0, "", 1, "SolidIT")
        );
        when(modelMapper.map(product2, ProductSimpleDto.class)).thenReturn(
                new ProductSimpleDto(2, "Product 2", 30.0, "", 1, "SolidIT")
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

    @Test
    void searchProductByKeyword() {
        String keyword = "Product";

        Company company = createCompany();

        Product product1 = createProduct(1, "Product 1", 50.0, company);
        Product product2 = createProduct(2, "Product 2", 30.0, company);

        List<Product> fakeProductList = Arrays.asList(product1, product2);

        // ModelMapper에 대한 행동 설정
        when(modelMapper.map(product1, ProductSimpleDto.class)).thenReturn(
                new ProductSimpleDto(1, "Product 1", 50.0, "", 1, "Test Company")
        );
        when(modelMapper.map(product2, ProductSimpleDto.class)).thenReturn(
                new ProductSimpleDto(2, "Product 2", 30.0, "", 1, "Test Company")
        );

        // ProductRepository에 대한 행동 설정
        when(productRepository.findByProductNameContainingIgnoreCaseOrProductDescContainingIgnoreCase(keyword, keyword)).thenReturn(fakeProductList);

        // 테스트 수행
        List<ProductSimpleDto> result = productService.findProductsByKeywords(keyword);

        // 결과 검증
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getProductId());
        assertEquals("Product 1", result.get(0).getProductName());
        assertEquals(50.0, result.get(0).getProductPrice());
        assertEquals("Test Company", result.get(0).getCompanyName());
        assertEquals(2, result.get(1).getProductId());
        assertEquals("Product 2", result.get(1).getProductName());
        assertEquals(30.0, result.get(1).getProductPrice());
    }

    @Test
    void getProductDetail() {
        int productId = 1;

        Company company = createCompany();

        Product product = createDetailProduct(1, "Product 1", 50.0, "Description", "image_url", company);

        // ModelMapper에 대한 행동 설정
        when(modelMapper.map(product, ProductDetailDto.class)).thenReturn(
                new ProductDetailDto(1, "Product 1", 50.0, "Description", "image_url", 1, "Test Company")
        );

        // ProductRepository에 대한 행동 설정
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // 테스트 수행
        Optional<ProductDetailDto> result = productService.getProductDetail(productId);

        // 결과 검증
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getProductId());
        assertEquals("Product 1", result.get().getProductName());
        assertEquals(50.0, result.get().getProductPrice());
        assertEquals("Description", result.get().getProductDesc());
        assertEquals("image_url", result.get().getImageUrls());
        assertEquals(1, result.get().getCompanyId());
        assertEquals("Test Company", result.get().getCompanyName());
    }

    @Test
    void updateProduct() throws Exception {
        int productId = 1;

        Company company = createCompany();
        companyRepository.save(company);

        // 가짜 데이터 생성 후 저장
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setProductName("Test Product");
        productDetailDto.setProductPrice(100.0);
        productDetailDto.setProductDesc("Test Description");
        productDetailDto.setImageUrls("test_image_url");
        productDetailDto.setCompanyId(company.getCompanyId());
        productDetailDto.setCompanyName(company.getCompanyName());
        productService.saveProduct(productDetailDto);

        // 가짜 수정 데이터 생성
        ProductDetailDto updatedProductDetailDto = new ProductDetailDto();
        updatedProductDetailDto.setProductName("Updated Product");
        updatedProductDetailDto.setProductPrice(150.0);
        updatedProductDetailDto.setProductDesc("Updated Description");
        updatedProductDetailDto.setImageUrls("updated_image_url");
        updatedProductDetailDto.setCompanyId(1);
        updatedProductDetailDto.setCompanyName("Test Company");

        // ProductService의 updateProduct 호출
        productService.updateProduct(productId, updatedProductDetailDto);

        // 행동 검증
        Product updatedProduct = productRepository.findById(productId).orElse(null);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getProductName());
        assertEquals(150.0, updatedProduct.getProductPrice());
        assertEquals("Updated Description", updatedProduct.getProductDesc());
        assertEquals("updated_image_url", new String(updatedProduct.getImageUrl()));
        assertEquals("Test Company", updatedProduct.getCompany().getCompanyName());
    }

    @Test
    void deleteProduct() {
        int productId = 1;

        // ProductService의 deleteProduct 호출
        productService.deleteProduct(productId);

        // 행동 검증
        verify(productRepository, times(1)).deleteById(productId);
    }

}
