package toad.toad.service;

import toad.toad.data.dto.ProductRequestDto;
import toad.toad.data.dto.ProductResponseDetailDto;
import toad.toad.data.dto.ProductResponseSimpleDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    int saveProduct(ProductRequestDto productRequestDto) throws Exception;

    List<ProductResponseSimpleDto> getAllProducts();

    List<ProductResponseSimpleDto> findProductsByKeywords(String keyword);

    Optional<ProductResponseDetailDto> getProductDetail(int id);

    int updateProduct(int productId, ProductRequestDto productRequestDto) throws Exception;

    void deleteProduct(int productId);

}