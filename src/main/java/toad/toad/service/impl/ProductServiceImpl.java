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
    public int saveProduct(ProductDetailDto productDetailDto) throws Exception {

//        Company company = companyRepository.findById(productDetailDto.getCompanyId())
//                                        .orElseThrow(() -> new Exception("company not found"));

        Product newProduct = modelMapper.map(productDetailDto, Product.class);
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
    public Optional<ProductDetailDto> getProductDetail(int id) {
        Optional<Product> tarGetProduct = productRepository.findById(id);
        return tarGetProduct.map(product -> modelMapper.map(product, ProductDetailDto.class));
    }

    @Override
    public int updateProduct(int productId, ProductDetailDto productDetailDto) throws Exception {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Company company = companyRepository.findById(productDetailDto.getCompanyId())
                                .orElseThrow(() -> new Exception("Company not found"));

            product.setProductName(productDetailDto.getProductName());
            product.setProductPrice(productDetailDto.getProductPrice());
            product.setProductDesc(productDetailDto.getProductDesc());
            product.setImageUrl(productDetailDto.getImageUrls().getBytes());
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
