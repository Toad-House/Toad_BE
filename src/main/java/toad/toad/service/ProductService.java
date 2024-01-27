package toad.toad.service;

import toad.toad.data.dto.ProductDetailDto;
import toad.toad.data.dto.ProductSimpleDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    int saveProduct(ProductDetailDto productDetailDto) throws Exception;

    List<ProductSimpleDto> getAllProducts();

    List<ProductSimpleDto> findProductsByKeywords(String keyword);

    Optional<ProductDetailDto> getProductDetail(int id);

    int updateProduct(ProductDetailDto productDetailDto, int productId) throws Exception;

    void deleteProduct(int productId);

}