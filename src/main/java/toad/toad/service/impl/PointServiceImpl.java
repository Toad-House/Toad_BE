package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.dto.CompanyChargePointDto;
import toad.toad.data.dto.CompanyPointUsageDto;
import toad.toad.data.dto.UserPointUsageDto;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.PointService;

import java.util.ArrayList;
import java.util.List;
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
    public List<UserPointUsageDto> getAllUserPointUsage(int userId) {
        List<UserPointUsage> userPointUsages = userPointUsageRepository.findByUser_UserId(userId);
        List<UserPointUsageDto> userPointUsageDtos = new ArrayList<>();

        for (UserPointUsage userPointUsage : userPointUsages) {
            UserPointUsageDto userPointUsageDto = new UserPointUsageDto();
            User user = userPointUsage.getUser();
            userPointUsageDto.setUserId(user.getUserId());
            userPointUsageDto.setUserName(user.getUserName());
            userPointUsageDto.setReceiveOrUse(userPointUsage.isReceiveOrUse());
            userPointUsageDto.setPoint(userPointUsage.getPoint());
            if (!userPointUsage.isReceiveOrUse()) { // receive
                Company company = userPointUsage.getCompany();
                userPointUsageDto.setCompanyId(company.getCompanyId());
                userPointUsageDto.setCompanyName(company.getCompanyName());
            } else {  // use
                Order order = userPointUsage.getOrder();
                userPointUsageDto.setOrderId(order.getOrderId());
                userPointUsageDto.setProductId(order.getProduct().getProductId());
                userPointUsageDto.setProductName(order.getProduct().getProductName());
            }
            userPointUsageDtos.add(userPointUsageDto);
        }
        return userPointUsageDtos;
    }

    @Override
    public List<CompanyPointUsageDto> getAllCompanyPointUsage(int companyId) {
        List<CompanyPointUsage> companyPointUsages = companyPointUsageRepository.findByCompany_CompanyId(companyId);
        List<CompanyPointUsageDto> companyPointUsageDtos = new ArrayList<>();

        for (CompanyPointUsage companyPointUsage : companyPointUsages) {
            CompanyPointUsageDto companyPointUsageDto = new CompanyPointUsageDto();
            Company company = companyPointUsage.getCompany();
            companyPointUsageDto.setCompanyId(company.getCompanyId());
            companyPointUsageDto.setCompanyName(company.getCompanyName());
            companyPointUsageDto.setChargeOrPay(companyPointUsage.isChargeOrPay());
            companyPointUsageDto.setPoint(companyPointUsage.getPoint());
            if (companyPointUsage.isChargeOrPay()) { // receive
                User user = companyPointUsage.getUser();
                companyPointUsageDto.setUserId(user.getUserId());
                companyPointUsageDto.setUserName(user.getUserName());
            }
            companyPointUsageDtos.add(companyPointUsageDto);
        }
        return companyPointUsageDtos;
    }

    @Override
    public void chargePointsToCompany(CompanyChargePointDto companyChargePointDto) {
        Optional<Company> companyOptional = companyRepository.findById(companyChargePointDto.getCompanyId());
        if (companyOptional.isPresent()) {
            // company 포인트 충전
            Company company = companyOptional.get();
            company.setCompanyPoint(company.getCompanyPoint() + companyChargePointDto.getChargePoints());

            // company 포인트 변동 기록 저장
            CompanyPointUsage companyPointUsage = CompanyPointUsage.builder()
                    .company(company)
                    .chargeOrPay(false) // false: charge, true: pay
                    .point(companyChargePointDto.getChargePoints())
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
