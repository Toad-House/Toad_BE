package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.dto.PatchConsumerRequestDto;
import toad.toad.data.dto.PostConsumerRequestDto;
import toad.toad.data.dto.RequestConsumerDto;
import toad.toad.data.dto.RequestGetConsumerDto;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.MaterialConsumerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialConsumerServiceImpl implements MaterialConsumerService {
    private final MaterialRequestRepository materialRequestRepository;
    private final CompanyRepository companyRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    private final ApprovedMaterialRequestRepository approvedMaterialRequestRepository;
    private final CompletedMaterialRequestRepository completedMaterialRequestRepository;
    private final CanceledMaterialRequestRepository canceledMaterialRequestRepository;

    public MaterialConsumerServiceImpl(MaterialRequestRepository materialRequestRepository, CompanyRepository companyRepository, MaterialRepository materialRepository, ProductRepository productRepository, ApprovedMaterialRequestRepository approvedMaterialRequestRepository, CompletedMaterialRequestRepository completedMaterialRequestRepository, CanceledMaterialRequestRepository canceledMaterialRequestRepository) {
        this.materialRequestRepository = materialRequestRepository;
        this.companyRepository = companyRepository;
        this.materialRepository = materialRepository;
        this.productRepository = productRepository;
        this.approvedMaterialRequestRepository = approvedMaterialRequestRepository;
        this.completedMaterialRequestRepository = completedMaterialRequestRepository;
        this.canceledMaterialRequestRepository = canceledMaterialRequestRepository;
    }

    @Override
    public List<RequestConsumerDto> getAllRequests(Integer userId) {
        List<MaterialRequest> materialRequests = materialRequestRepository.findAllByUserId(userId);
        List<RequestConsumerDto> requestConsumerDtos = new ArrayList<>();

        if (materialRequests != null) {
            for (MaterialRequest materialRequest : materialRequests) {
                RequestConsumerDto requestConsumerDto = new RequestConsumerDto();
                Material material = materialRepository.findById(materialRequest.getMaterialId()).orElse(null);

                Company company = companyRepository.findById(material.getCompanyId()).orElse(null);
                if (company != null) {
                    requestConsumerDto.setCompanyName(company.getCompanyName());
                    requestConsumerDto.setCompanyContact(company.getCompanyContact());
                }

                requestConsumerDto.setRequestId(materialRequest.getRequestId());
                requestConsumerDto.setMaterialId(materialRequest.getMaterialId());
                requestConsumerDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
                requestConsumerDto.setCollectionArea(materialRequest.getCollectionArea());
                requestConsumerDto.setCollectionState(materialRequest.getCollectionState());

                requestConsumerDtos.add(requestConsumerDto);
            }
        }
       return requestConsumerDtos;
    }

    @Override
    public RequestGetConsumerDto getOneRequest(Integer requestId) {
        RequestGetConsumerDto requestGetConsumerDto = new RequestGetConsumerDto();

        MaterialRequest materialRequest = materialRequestRepository.findById(requestId).orElse(null);
        if (materialRequest != null) {
            Material material = materialRepository.findById(materialRequest.getMaterialId()).orElse(null);

            if (material != null) {

                requestGetConsumerDto.setMaterialName(material.getMaterialName());
                requestGetConsumerDto.setExpectedCondition(material.getExpectedCondition());
                requestGetConsumerDto.setPointsPerWeight(material.getPointsPerWeight());
                requestGetConsumerDto.setMinimumQuantity(material.getMinimumQuantity());
                requestGetConsumerDto.setRestrictedArea(material.getRestrictedArea());
                requestGetConsumerDto.setAvailableArea(material.getAvailableArea());

                Product product = productRepository.findById(material.getProductId()).orElse(null);
                if (product != null) {
                    requestGetConsumerDto.setProductName(product.getProductName());
                }

                Company company = companyRepository.findById(material.getCompanyId()).orElse(null);
                if (company != null) {
                    requestGetConsumerDto.setCompanyName(company.getCompanyName());
                    requestGetConsumerDto.setCompanyContact(company.getCompanyContact());
                }
            }

            requestGetConsumerDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
            requestGetConsumerDto.setCollectionArea(materialRequest.getCollectionArea());
            requestGetConsumerDto.setCollectionState(materialRequest.getCollectionState());

            if ("approved".equals(requestGetConsumerDto.getCollectionState())) {
                ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(requestId);
                requestGetConsumerDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
                requestGetConsumerDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
            }
            else if ("completed".equals(requestGetConsumerDto.getCollectionState())) {
                CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(requestId);
                requestGetConsumerDto.setPoints(completedMaterialRequest.getPoints());
            }
            else if ("canceled".equals(requestGetConsumerDto.getCollectionState())) {
                CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByRequestId(requestId);
                requestGetConsumerDto.setCancelReason(canceledMaterialRequest.getCancelReason());
            }
        }



        return requestGetConsumerDto;
    }

    @Override
    public void updateRequest(PatchConsumerRequestDto patchConsumerRequestDto) {
        if ("applicated".equals(patchConsumerRequestDto.getCollectionState())) {
            MaterialRequest materialRequest = materialRequestRepository.findById(patchConsumerRequestDto.getRequestId()).orElse(null);
            materialRequest.setQuantityOfMaterial(patchConsumerRequestDto.getQuantityOfMaterial());
            materialRequest.setCollectionArea(patchConsumerRequestDto.getCollectionArea());

            materialRequestRepository.save(materialRequest);
        }
        else if ("canceled".equals(patchConsumerRequestDto.getCollectionState())) {
            CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByRequestId(patchConsumerRequestDto.getRequestId());
            canceledMaterialRequest.setCancelReason(patchConsumerRequestDto.getCancelReason());

            canceledMaterialRequestRepository.save(canceledMaterialRequest);
        }
    }

    @Override
    public Integer saveCancelRequest(PostConsumerRequestDto postConsumerRequestDto) {
        CanceledMaterialRequest canceledMaterialRequest = new CanceledMaterialRequest();
        MaterialRequest materialRequest = materialRequestRepository.findById(postConsumerRequestDto.getRequestId()).orElse(null);

        if (materialRequest != null) {
            materialRequest.setCollectionState("canceled");

            canceledMaterialRequest.setRequestId(postConsumerRequestDto.getRequestId());
            canceledMaterialRequest.setCancelReason(postConsumerRequestDto.getCancelReason());

            canceledMaterialRequestRepository.save(canceledMaterialRequest);
            materialRequestRepository.save(materialRequest);

        }


        return canceledMaterialRequest.getCancelId();
    }
}
