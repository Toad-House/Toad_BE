package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.CompanyChargePointDto;
import toad.toad.data.dto.CompanyPointUsageDto;
import toad.toad.data.dto.UserPointUsageDto;
import toad.toad.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {
    private final PointService pointService;


    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @Operation(summary = "유저 포인트 사용기록 조회하기", description = "receive(재료 거래 완료 후, 회사에게 포인트를 받는 경우): 0, use(상품 구매시, 포인트를 사용하는 경우): 1")
    @GetMapping("/user")
    public ResponseEntity<List<UserPointUsageDto>> getAllUserPointUsage(@RequestParam int userId) {
        List<UserPointUsageDto> userPointUsageDtoList = pointService.getAllUserPointUsage(userId);
        return ResponseEntity.ok(userPointUsageDtoList);
    }

    @Operation(summary = "회사 포인트 사용기록 조회하기", description = "charge(포인트 충전): 0, pay(재료 거래 완료 후, 유저에게 포인트를 지급하는 경우): 1")
    @GetMapping("/company")
    public ResponseEntity<List<CompanyPointUsageDto>> getAllCompanyPointUsage(@RequestParam int companyId) {
        List<CompanyPointUsageDto> companyPointUsageDtoList = pointService.getAllCompanyPointUsage(companyId);
        return ResponseEntity.ok(companyPointUsageDtoList);
    }

    @Operation(summary = "회사 포인트 충전")
    @PostMapping("/company/charge")
    public void chargeCompanyPoint(@RequestBody CompanyChargePointDto companyChargePointDto) {
        pointService.chargePointsToCompany(companyChargePointDto);
    }
}
