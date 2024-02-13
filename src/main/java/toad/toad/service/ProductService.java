package toad.toad.service;

import toad.toad.data.dto.ProductPostDto;
import toad.toad.data.dto.ProductGetDetailDto;
import toad.toad.data.dto.ProductGetSimpleDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    int saveProduct(ProductPostDto productPostDto) throws Exception;

    List<ProductGetSimpleDto> getAllProducts();

    List<ProductGetSimpleDto> findProductsByKeywords(String keyword);

    Optional<ProductGetDetailDto> getProductDetail(int id);

    int updateProduct(int productId, ProductPostDto productPostDto) throws Exception;

    void deleteProduct(int productId);

}