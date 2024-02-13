package toad.toad.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.ProductPostDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Product;
import toad.toad.data.dto.ProductGetDetailDto;
import toad.toad.data.dto.ProductGetSimpleDto;
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
    public int saveProduct(ProductPostDto productPostDto) throws Exception {

        Company company = companyRepository.findById(productPostDto.getCompanyId())
                                        .orElseThrow(() -> new Exception("company not found"));

        Product newProduct = modelMapper.map(productPostDto, Product.class);
        newProduct.setCompany(company);
        productRepository.save(newProduct);

        return newProduct.getProductId();
    }

    @Override
    public List<ProductGetSimpleDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductGetSimpleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductGetSimpleDto> findProductsByKeywords(String keyword) {
        List<Product> targetProducts = productRepository.findByProductNameContainingIgnoreCaseOrProductDescContainingIgnoreCase(keyword, keyword);
        return targetProducts.stream()
                .map(product -> modelMapper.map(product, ProductGetSimpleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductGetDetailDto> getProductDetail(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(product -> modelMapper.map(product, ProductGetDetailDto.class));
    }

    @Override
    public int updateProduct(int productId, ProductPostDto productPostDto) throws Exception {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Company company = productOptional.get().getCompany();
//                    companyRepository.findById(productOptional.get().getCompany().getCompanyId())
//                                .orElseThrow(() -> new Exception("Company not found"));

            Product product = modelMapper.map(productPostDto, Product.class);
            product.setProductId(productId);
            product.setCompany(company);

//            product.setProductName(productRequestDto.getProductName());
//            product.setProductPrice(productRequestDto.getProductPrice());
//            product.setProductDesc(productRequestDto.getProductDesc());
//            product.setImageUrl(productRequestDto.getImageUrls().getBytes());
//            product.setCompany(company);

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
