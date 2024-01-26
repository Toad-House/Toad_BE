package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.ProductSimpleDto;
import toad.toad.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "전체 상품 조회", description = "전체 상품 리스트를 조회하는 api 입니다.")
    @GetMapping
    public ResponseEntity<List<ProductSimpleDto>> getAllProducts() {
        List<ProductSimpleDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSimpleDto>> searchProducts(@RequestParam String keyword) {
        List<ProductSimpleDto> products = productService.findProductsByKeywords(keyword);
        return ResponseEntity.ok(products);
    }
}
