package toad.toad.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Product;
import toad.toad.data.dto.ProductDetailDto;
import toad.toad.data.dto.ProductSimpleDto;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
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
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductSimpleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductSimpleDto> findProductsByKeywords(String keyword) {
        List<Product> targetProducts = productRepository.findByProductNameContainingIgnoreCaseOrProductDescContainingIgnoreCase(keyword);
        return targetProducts.stream()
                .map(product -> modelMapper.map(product, ProductSimpleDto.class))
                .collect(Collectors.toList());
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
