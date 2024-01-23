package toad.toad.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toad.toad.domain.Company;
import toad.toad.domain.Product;
import toad.toad.dto.ProductDetailDto;
import toad.toad.dto.ProductSimpleDto;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public int saveProduct(ProductDetailDto productDetailDto) {

        Optional<Company> company = companyRepository.findByCompanyName(productDetailDto.getCompanyName());

        Product newProduct = Product.builder()
                .productName(productDetailDto.getProductName())
                .productPrice(productDetailDto.getProductPrice())
                .productDesc(productDetailDto.getProductDesc())
                .imageUrl(productDetailDto.getImageUrls().getBytes())
                .company(company.get())
                .build();
        productRepository.save(newProduct);

        return newProduct.getProductId();
    }

    @Override
    public List<ProductSimpleDto> getAllProducts() {
        return null;
    }

    @Override
    public List<ProductSimpleDto> findProductsByKeywords() {
        return null;
    }

    @Override
    public List<ProductDetailDto> getProductDetail() {
        return null;
    }

    @Override
    public int updateProduct(ProductDetailDto productDetailDto, int id) {
        return 0;
    }

    @Override
    public int deleteProduct(int id) {
        return 0;
    }
}
