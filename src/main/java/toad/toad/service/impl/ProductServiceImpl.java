package toad.toad.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.ProductRequestDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Product;
import toad.toad.data.dto.ProductResponseDetailDto;
import toad.toad.data.dto.ProductResponseSimpleDto;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.ProductService;

import java.util.List;
import java.util.Optional;
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
    public int saveProduct(ProductRequestDto productRequestDto) throws Exception {

        Company company = companyRepository.findById(productRequestDto.getCompanyId())
                                        .orElseThrow(() -> new Exception("company not found"));

        Product newProduct = modelMapper.map(productRequestDto, Product.class);
        newProduct.setCompany(company);
        productRepository.save(newProduct);

        return newProduct.getProductId();
    }

    @Override
    public List<ProductResponseSimpleDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductResponseSimpleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseSimpleDto> findProductsByKeywords(String keyword) {
        List<Product> targetProducts = productRepository.findByProductNameContainingIgnoreCaseOrProductDescContainingIgnoreCase(keyword, keyword);
        return targetProducts.stream()
                .map(product -> modelMapper.map(product, ProductResponseSimpleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductResponseDetailDto> getProductDetail(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(product -> modelMapper.map(product, ProductResponseDetailDto.class));
    }

    @Override
    public int updateProduct(int productId, ProductRequestDto productRequestDto) throws Exception {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Company company = companyRepository.findById(productRequestDto.getCompanyId())
                                .orElseThrow(() -> new Exception("Company not found"));

            product.setProductName(productRequestDto.getProductName());
            product.setProductPrice(productRequestDto.getProductPrice());
            product.setProductDesc(productRequestDto.getProductDesc());
            product.setImageUrl(productRequestDto.getImageUrls().getBytes());
            product.setCompany(company);

            return productRepository.save(product).getProductId();
        } else {
            throw new Exception("Product not found");
        }
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }
}
