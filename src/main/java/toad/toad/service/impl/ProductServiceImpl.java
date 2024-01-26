package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Product;
import toad.toad.data.dto.ProductDetailDto;
import toad.toad.data.dto.ProductSimpleDto;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public int saveProduct(ProductDetailDto productDetailDto) throws Exception {

        Company company = companyRepository.findByCompanyName(productDetailDto.getCompanyName())
                                        .orElseThrow(() -> new Exception("company not found"));

        Product newProduct = Product.builder()
                .productName(productDetailDto.getProductName())
                .productPrice(productDetailDto.getProductPrice())
                .productDesc(productDetailDto.getProductDesc())
                .imageUrl(productDetailDto.getImageUrls().getBytes())
                .company(company)
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
