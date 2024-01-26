package toad.toad.service;

import toad.toad.data.dto.ProductDetailDto;
import toad.toad.data.dto.ProductSimpleDto;

import java.util.List;

public interface ProductService {

    int saveProduct(ProductDetailDto productDetailDto) throws Exception;

    List<ProductSimpleDto> getAllProducts();

    List<ProductSimpleDto> findProductsByKeywords(String keyword);

    List<ProductDetailDto> getProductDetail();

    int updateProduct(ProductDetailDto productDetailDto, int id);

    int deleteProduct(int id);

}