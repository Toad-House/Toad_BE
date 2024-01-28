package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.CompanyInfoDto;
import toad.toad.data.dto.SalesDetailDto;
import toad.toad.data.dto.SalesSimpleDto;
import toad.toad.service.SalesService;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @Operation(summary = "회사 상품 판매 기록 조회하기", description = "본사의 상품 판매 기록을 조회하는 api 입니다.")
    @GetMapping("/list")
    public ResponseEntity<List<SalesSimpleDto>> getAllSales(@RequestBody CompanyInfoDto companyInfoDto) {
        List<SalesSimpleDto> allSales = salesService.getAllSales(companyInfoDto.getCompanyId());
        return ResponseEntity.ok(allSales);
    }

    @Operation(summary = "상품별 판매 기록 조회하기", description = "특정 상품에 대한 판매(주문)들만 조회하는 api 입니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<List<SalesDetailDto>> getSalesByProductId(@PathVariable int productId) {
        List<SalesDetailDto> salesList = salesService.getSalesByProductId(productId);
        return ResponseEntity.ok(salesList);
    }
}
