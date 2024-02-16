package toad.toad.service;

public interface PointService {
    // 회사 - 포인트 충전
    void chargePointsToCompany(int companyId, int points);

    // 사용자 - 상품 구매 시 포인트 사용
    void  usePointsByUser(int orderId);

    // 재료 거래 시 회사 -> 사용자 포인트 지급
    void pointFromCompanyToUserWhenMaterialTrading(int requestId, int points);
}
