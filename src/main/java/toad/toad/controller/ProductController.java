package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.ProductDetailDto;
import toad.toad.data.dto.ProductSimpleDto;
import toad.toad.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CREATE
    @Operation(summary = "새 상품 등록", description = "새로운 상품을 추가하는 api 입니다.")
    @PostMapping("/new")
    public ResponseEntity<Integer> createProduct(@RequestBody ProductDetailDto productDetailDto) throws Exception {
        int productId = productService.saveProduct(productDetailDto);
        return ResponseEntity.ok(productId);
    }

    // READ
    @Operation(summary = "전체 상품 조회", description = "전체 상품 리스트를 조회하는 api 입니다.")
    @GetMapping
    public ResponseEntity<List<ProductSimpleDto>> getAllProducts() {
        List<ProductSimpleDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "상품 검색", description = "키워드로 상품을 검색하는 api 입니다.")
    @GetMapping("/search")
    public ResponseEntity<List<ProductSimpleDto>> searchProducts(@RequestParam String keyword) {
        List<ProductSimpleDto> products = productService.findProductsByKeywords(keyword);
        return ResponseEntity.ok(products);
    }


    @Operation(summary = "특정 상품 조회", description = "특정 상품의 상세 정보를 조회하는 api 입니다.")
    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        Optional<ProductDetailDto> productDetailOptional = productService.getProductDetail(productId);
        return productDetailOptional.map((ResponseEntity::ok))
                .orElse(ResponseEntity.notFound().build());
    }
}
