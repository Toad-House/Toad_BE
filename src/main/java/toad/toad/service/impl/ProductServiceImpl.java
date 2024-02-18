package toad.toad.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private ImageServiceImpl imageService;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int saveProduct(ProductPostDto productPostDto) throws Exception {

        Company company = companyRepository.findById(productPostDto.getCompanyId())
                                        .orElseThrow(() -> new Exception("company not found"));

        Product newProduct = new Product();
        newProduct.setProductName(productPostDto.getProductName());
        newProduct.setProductPrice(productPostDto.getProductPrice());
        newProduct.setProductDesc(productPostDto.getProductDesc());
        newProduct.setCompany(company);

        if (!productPostDto.getImage().isEmpty()) {
            String imageUrl = imageService.imageHandler(productPostDto.getImage());
            newProduct.setImageUrl(imageUrl);
        }

        productRepository.save(newProduct);

        return newProduct.getProductId();
    }

    @Override
    public List<ProductGetSimpleDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    ProductGetSimpleDto simpleDto = modelMapper.map(product, ProductGetSimpleDto.class);
                    simpleDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + product.getImageUrl());
                    return simpleDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductGetSimpleDto> findProductsByKeywords(String keyword) {
        List<Product> targetProducts = productRepository.findByProductNameContainingIgnoreCaseOrProductDescContainingIgnoreCase(keyword, keyword);


        return targetProducts.stream()
                .map(product -> {
                    ProductGetSimpleDto simpleDto = new ProductGetSimpleDto();
                    simpleDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + product.getImageUrl());
                    return simpleDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductGetDetailDto> getProductDetail(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(product -> {
            ProductGetDetailDto detailDto = modelMapper.map(product, ProductGetDetailDto.class);
            detailDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + product.getImageUrl());
            return detailDto;
        });
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
