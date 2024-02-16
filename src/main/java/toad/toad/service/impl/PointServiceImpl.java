package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.PointService;

import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    private final CompanyRepository companyRepository;
    private final CompanyPointUsageRepository companyPointUsageRepository;
    private final UserPointUsageRepository userPointUsageRepository;
    private final OrderRepository orderRepository;
    private final MaterialRequestRepository materialRequestRepository;

    public PointServiceImpl(CompanyRepository companyRepository, CompanyPointUsageRepository companyPointUsageRepository, UserPointUsageRepository userPointUsageRepository, OrderRepository orderRepository, MaterialRequestRepository materialRequestRepository) {
        this.companyRepository = companyRepository;
        this.companyPointUsageRepository = companyPointUsageRepository;
        this.userPointUsageRepository = userPointUsageRepository;
        this.orderRepository = orderRepository;
        this.materialRequestRepository = materialRequestRepository;
    }


    @Override
    public void chargePointsToCompany(int companyId, int chargePoints) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            // company 포인트 충전
            Company company = companyOptional.get();
            company.setCompanyPoint(company.getCompanyPoint() + chargePoints);

            // company 포인트 변동 기록 저장
            CompanyPointUsage companyPointUsage = CompanyPointUsage.builder()
                    .company(company)
                    .chargeOrPay(false) // false: charge, true: pay
                    .point(chargePoints)
                    .build();

            companyPointUsageRepository.save(companyPointUsage);
        }
    }

    @Override
    public void usePointsByUser(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            User user = order.getUser();
            user.setUserPoint(user.getUserPoint() - order.getUsedPoints());

            UserPointUsage userPointUsage = UserPointUsage.builder()
                    .user(user)
                    .receiveOrUse(true) // true: use, false: receive
                    .order(order)
                    .point(order.getUsedPoints())
                    .build();

            userPointUsageRepository.save(userPointUsage);
        }
    }

    @Override
    public void pointFromCompanyToUserWhenMaterialTrading(int requestId, int points) {
        Optional<MaterialRequest> materialRequestOptional = materialRequestRepository.findById(requestId);

        if (materialRequestOptional.isPresent()) {
            MaterialRequest materialRequest = materialRequestOptional.get();
            User user = materialRequest.getUser();
            Company company = materialRequest.getMaterial().getCompany();

            // company & user 포인트 조정
            company.setCompanyPoint(company.getCompanyPoint() - points);
            user.setUserPoint(user.getUserPoint() + points);

            // 포인트 변동 기록
            CompanyPointUsage companyPointUsage = CompanyPointUsage.builder()
                    .company(company)
                    .chargeOrPay(true)  // true: pay, false: charge
                    .user(user)
                    .point(points)
                    .build();

            companyPointUsageRepository.save(companyPointUsage);

            UserPointUsage userPointUsage = UserPointUsage.builder()
                    .user(user)
                    .receiveOrUse(false)    // false: receive, true: use
                    .company(company)
                    .point(points)
                    .build();

            userPointUsageRepository.save(userPointUsage);
        }
    }
}
