package toad.toad.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.PatchConsumerRequestDto;
import toad.toad.data.dto.PostConsumerRequestDto;
import toad.toad.data.dto.RequestConsumerDto;
import toad.toad.data.dto.RequestGetConsumerDto;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.MaterialConsumerService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MaterialConsumerServiceImpl implements MaterialConsumerService {
    private final MaterialRequestRepository materialRequestRepository;
    private final CompanyRepository companyRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    private final ApprovedMaterialRequestRepository approvedMaterialRequestRepository;
    private final CompletedMaterialRequestRepository completedMaterialRequestRepository;
    private final CanceledMaterialRequestRepository canceledMaterialRequestRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

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
        List<MaterialRequest> materialRequests = materialRequestRepository.findAllByUserUserId(userId);
        Set<MaterialRequest> uniqueMaterialRequests = new HashSet<>(materialRequests);

        List<RequestConsumerDto> requestConsumerDtos = new ArrayList<>();

        if (uniqueMaterialRequests != null) {
            for (MaterialRequest materialRequest : uniqueMaterialRequests) {
                RequestConsumerDto requestConsumerDto = new RequestConsumerDto();
                Material material = materialRequest.getMaterial();

                Company company = material.getCompany();
                if (company != null) {
                    requestConsumerDto.setCompanyName(company.getCompanyName());
                    requestConsumerDto.setCompanyContact(company.getCompanyContact());
                }

                requestConsumerDto.setRequestId(materialRequest.getRequestId());
                requestConsumerDto.setMaterialId(material.getMaterialId());
                requestConsumerDto.setMaterialName(material.getMaterialName());
                requestConsumerDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
                requestConsumerDto.setCollectionArea(materialRequest.getCollectionArea());
                requestConsumerDto.setCollectionState(materialRequest.getCollectionState());
                if ("approved".equals(materialRequest.getCollectionState())) {
                    ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestConsumerDto.getRequestId());
                    requestConsumerDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
                    requestConsumerDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
                }
                else if ("completed".equals(materialRequest.getCollectionState())) {
                    CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByMaterialRequestRequestId(requestConsumerDto.getRequestId());
                    requestConsumerDto.setPoints(completedMaterialRequest.getPoints());
                }
                else if ("canceled".equals(materialRequest.getCollectionState())) {
                    CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByMaterialRequestRequestId(requestConsumerDto.getRequestId());
                    requestConsumerDto.setCancelReason(canceledMaterialRequest.getCancelReason());
                }
                requestConsumerDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + materialRequest.getImageUrl());

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
            Material material = materialRequest.getMaterial();

            if (material != null) {

                requestGetConsumerDto.setMaterialName(material.getMaterialName());
                requestGetConsumerDto.setExpectedCondition(material.getExpectedCondition());
                requestGetConsumerDto.setPointsPerWeight(material.getPointsPerWeight());
                requestGetConsumerDto.setMinimumQuantity(material.getMinimumQuantity());
                requestGetConsumerDto.setRestrictedArea(material.getRestrictedArea());
                requestGetConsumerDto.setAvailableArea(material.getAvailableArea());
                requestGetConsumerDto.setMaterialImageUrl("https://storage.googleapis.com/" + bucketName + "/" + material.getImageUrl());

                Product product = productRepository.findById(material.getProduct().getProductId()).orElse(null);
                if (product != null) {
                    requestGetConsumerDto.setProductName(product.getProductName());
                }

                Company company = material.getCompany();
                if (company != null) {
                    requestGetConsumerDto.setCompanyName(company.getCompanyName());
                    requestGetConsumerDto.setCompanyContact(company.getCompanyContact());
                }
            }

            requestGetConsumerDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
            requestGetConsumerDto.setCollectionArea(materialRequest.getCollectionArea());
            requestGetConsumerDto.setCollectionState(materialRequest.getCollectionState());
            requestGetConsumerDto.setRequestImageUrl("https://storage.googleapis.com/" + bucketName + "/" + materialRequest.getImageUrl());

            if ("approved".equals(requestGetConsumerDto.getCollectionState())) {
                ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
                requestGetConsumerDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
                requestGetConsumerDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
            }
            else if ("completed".equals(requestGetConsumerDto.getCollectionState())) {
                ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
                requestGetConsumerDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
                requestGetConsumerDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());

                CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
                requestGetConsumerDto.setPoints(completedMaterialRequest.getPoints());
            }
            else if ("canceled".equals(requestGetConsumerDto.getCollectionState())) {
                CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
                requestGetConsumerDto.setCancelReason(canceledMaterialRequest.getCancelReason());
            }
        }



        return requestGetConsumerDto;
    }

    @Override
    public void updateRequest(PatchConsumerRequestDto patchConsumerRequestDto) {
        if ("applied".equals(patchConsumerRequestDto.getCollectionState())) {
            MaterialRequest materialRequest = materialRequestRepository.findById(patchConsumerRequestDto.getRequestId()).orElse(null);
            materialRequest.setQuantityOfMaterial(patchConsumerRequestDto.getQuantityOfMaterial());
            materialRequest.setCollectionArea(patchConsumerRequestDto.getCollectionArea());

            materialRequestRepository.save(materialRequest);
        }
        else if ("canceled".equals(patchConsumerRequestDto.getCollectionState())) {
            CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByMaterialRequestRequestId(patchConsumerRequestDto.getRequestId());
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

            canceledMaterialRequest.setMaterialRequest(materialRequest);
            canceledMaterialRequest.setCancelReason(postConsumerRequestDto.getCancelReason());

            canceledMaterialRequestRepository.save(canceledMaterialRequest);
            materialRequestRepository.save(materialRequest);

        }


        return canceledMaterialRequest.getCancelId();
    }
}
