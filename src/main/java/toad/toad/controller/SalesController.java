package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.ProductGetDetailDto;
import toad.toad.data.dto.SalesGetDetailDto;
import toad.toad.data.dto.SalesGetSimpleDto;
import toad.toad.service.ProductService;
import toad.toad.service.SalesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;
    private final ProductService productService;

    @Autowired
    public SalesController(SalesService salesService, ProductService productService) {
        this.salesService = salesService;
        this.productService = productService;
    }

    @Operation(summary = "회사 상품 판매 기록 조회하기", description = "본사의 상품 판매 기록을 조회하는 api 입니다.")
    @GetMapping("/list")
    public ResponseEntity<List<SalesGetSimpleDto>> getAllSales(@RequestParam int companyId) {
        List<SalesGetSimpleDto> allSales = salesService.getAllSales(companyId);
        return ResponseEntity.ok(allSales);
    }

    @Operation(summary = "상품별 판매 기록 조회하기", description = "특정 상품에 대한 판매(주문)들만 조회하는 api 입니다. \n productInfo에는 상품 정보가 담겨있고, salesList에 해당 사품의 판매 리스트가 담겨있습니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getSalesByProductId(@PathVariable int productId) {
        Optional<ProductGetDetailDto> productDetailOptional = productService.getProductDetail(productId);
        List<SalesGetDetailDto> salesList = salesService.getSalesByProductId(productId);

        Map<String, Object> response = new HashMap<>();

        productDetailOptional.ifPresent((productGetDetailDto -> response.put("productInfo", productGetDetailDto)));
        response.put("salesList", salesList);

        return ResponseEntity.ok(response);
    }
}
