package toad.toad.service;

import toad.toad.data.dto.CompanyChargePointDto;
import toad.toad.data.dto.CompanyPointUsageDto;
import toad.toad.data.dto.UserPointUsageDto;

import java.util.List;

public interface PointService {

    List<UserPointUsageDto> getAllUserPointUsage(int userId);

    List<CompanyPointUsageDto> getAllCompanyPointUsage(int companyId);

    // 회사 - 포인트 충전
    void chargePointsToCompany(CompanyChargePointDto companyChargePointDto);

    // 사용자 - 상품 구매 시 포인트 사용
    void  usePointsByUser(int orderId);

    // 재료 거래 시 회사 -> 사용자 포인트 지급
    void pointFromCompanyToUserWhenMaterialTrading(int requestId, int points);
}
