package toad.toad.service;

import toad.toad.dto.ProductDetailDto;
import toad.toad.dto.ProductSimpleDto;

import java.util.List;

public interface ProductService {

    int saveProduct(ProductDetailDto productDetailDto);

    List<ProductSimpleDto> getAllProducts();

    List<ProductSimpleDto> findProductsByKeywords();

    List<ProductDetailDto> getProductDetail();

    int updateProduct(ProductDetailDto productDetailDto, int id);

    int deleteProduct(int id);

}