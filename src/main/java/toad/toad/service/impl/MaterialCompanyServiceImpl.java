package toad.toad.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.*;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.MaterialCompanyService;
import toad.toad.service.PointService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MaterialCompanyServiceImpl implements MaterialCompanyService {

    private final MaterialRepository materialRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final ApprovedMaterialRequestRepository approvedMaterialRequestRepository;
    private final CanceledMaterialRequestRepository canceledMaterialRequestRepository;
    private final CompletedMaterialRequestRepository completedMaterialRequestRepository;
    private final PointService pointService;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public MaterialCompanyServiceImpl(MaterialRepository materialRepository, MaterialRequestRepository materialRequestRepository, ApprovedMaterialRequestRepository approvedMaterialRequestRepository, CanceledMaterialRequestRepository canceledMaterialRequestRepository, CompletedMaterialRequestRepository completedMaterialRequestRepository, PointService pointService) {
        this.materialRepository = materialRepository;
        this.materialRequestRepository = materialRequestRepository;
        this.approvedMaterialRequestRepository = approvedMaterialRequestRepository;
        this.canceledMaterialRequestRepository = canceledMaterialRequestRepository;
        this.completedMaterialRequestRepository = completedMaterialRequestRepository;
        this.pointService = pointService;
    }

    @Override
    public List<RequestCompanyDto> getAllRequests(Integer companyId) {
        List<Material> materials = materialRepository.findAllByCompanyCompanyId(companyId);
        Set<Material> uniqueMaterials = new HashSet<>(materials);
        List<RequestCompanyDto> requestCompanyDtos = new ArrayList<>();

        for (Material material : uniqueMaterials) {

            List<MaterialRequest> materialRequests = materialRequestRepository.findAllByMaterialMaterialId(material.getMaterialId());
            Set<MaterialRequest> uniqueMaterialRequests = new HashSet<>(materialRequests);

            if (uniqueMaterialRequests != null) {
                for (MaterialRequest materialRequest : uniqueMaterialRequests) {
                    RequestCompanyDto requestCompanyDto = new RequestCompanyDto();

                    requestCompanyDto.setMaterialId(material.getMaterialId());
                    requestCompanyDto.setMaterialName(material.getMaterialName());

                    requestCompanyDto.setRequestId(materialRequest.getRequestId());
                    requestCompanyDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
                    requestCompanyDto.setCollectionArea(materialRequest.getCollectionArea());
                    requestCompanyDto.setCollectionState(materialRequest.getCollectionState());

                    User user = materialRequest.getUser();

                    requestCompanyDto.setUserName(user.getUserName());
                    requestCompanyDto.setUserContact(user.getUserContact());

                    if ("approved".equals(materialRequest.getCollectionState())) {
                        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestCompanyDto.getRequestId());
                        requestCompanyDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
                        requestCompanyDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
                    } else if ("completed".equals(materialRequest.getCollectionState())) {
                        CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByMaterialRequestRequestId(requestCompanyDto.getRequestId());
                        requestCompanyDto.setPoints(completedMaterialRequest.getPoints());
                    } else if ("canceled".equals(materialRequest.getCollectionState())) {
                        CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByMaterialRequestRequestId(requestCompanyDto.getRequestId());
                        requestCompanyDto.setCancelReason(canceledMaterialRequest.getCancelReason());
                    }

                    requestCompanyDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + materialRequest.getImageUrl());

                    requestCompanyDtos.add(requestCompanyDto);

                }
            }
        }
        return requestCompanyDtos;
    }

    @Override
    public RequestGetCompanyDto getOneRequest (Integer requestId) {
        MaterialRequest materialRequest = materialRequestRepository.findById(requestId).orElse(null);

        Material material = materialRequest.getMaterial();
        User user = materialRequest.getUser();

        RequestGetCompanyDto requestGetCompanyDto = new RequestGetCompanyDto();

        requestGetCompanyDto.setMaterialId(material.getMaterialId());
        requestGetCompanyDto.setMaterialName(material.getMaterialName());
        requestGetCompanyDto.setProductId(material.getProduct().getProductId());
        requestGetCompanyDto.setPointsPerWeight(material.getPointsPerWeight());
        requestGetCompanyDto.setMinimumQuantity(material.getMinimumQuantity());
        requestGetCompanyDto.setRestrictedArea(material.getRestrictedArea());
        requestGetCompanyDto.setAvailableArea(material.getAvailableArea());
        requestGetCompanyDto.setMaterialImageUrl("https://storage.googleapis.com/" + bucketName + "/" + material.getImageUrl());

        requestGetCompanyDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
        requestGetCompanyDto.setCollectionArea(materialRequest.getCollectionArea());
        requestGetCompanyDto.setCollectionState(materialRequest.getCollectionState());
        requestGetCompanyDto.setRequestImageUrl("https://storage.googleapis.com/" + bucketName + "/" + materialRequest.getImageUrl());

        requestGetCompanyDto.setUserName(user.getUserName());
        requestGetCompanyDto.setUserContact(user.getUserContact());

        if ("approved".equals(materialRequest.getCollectionState())) {
            ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
            requestGetCompanyDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
            requestGetCompanyDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
        }
        else if ("completed".equals(materialRequest.getCollectionState())) {
            ApprovedMaterialRequest approvedMaterialReqeust = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
            requestGetCompanyDto.setExpectedDate(approvedMaterialReqeust.getExpectedDate());
            requestGetCompanyDto.setExpectedTime(approvedMaterialReqeust.getExpectedTime());

            CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
            requestGetCompanyDto.setPoints(completedMaterialRequest.getPoints());
        }
        else if ("canceled".equals(materialRequest.getCollectionState())) {
            CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
            requestGetCompanyDto.setCancelReason(canceledMaterialRequest.getCancelReason());
        }

        return requestGetCompanyDto;
    }

    @Override
    public void saveRequest (PostRequestDto postRequestDto) {
        MaterialRequest materialRequest = materialRequestRepository.findById(postRequestDto.getRequestId()).orElse(null);
        if (materialRequest != null) {
            materialRequest.setCollectionState(postRequestDto.getCollectionState());
            materialRequestRepository.save(materialRequest);

            if ("approved".equals(postRequestDto.getCollectionState())) {
                saveApproveRequest(postRequestDto.getRequestId(), postRequestDto.getExpectedDate(), postRequestDto.getExpectedTime());
            } else if ("completed".equals(postRequestDto.getCollectionState())) {
                saveCompleteRequest(postRequestDto.getRequestId(), postRequestDto.getPoints());
            } else if ("canceled".equals(postRequestDto.getCollectionState())) {
                saveCancelRequest(postRequestDto.getRequestId(), postRequestDto.getCancelReason());
            }
        }

    }
    @Override
    public void saveApproveRequest (Integer requestId, String ExpectedDate, String ExpectedTime) {
        ApprovedMaterialRequest approvedMaterialRequest = new ApprovedMaterialRequest();
        MaterialRequest materialRequest = materialRequestRepository.findById(requestId).orElse(null);

        approvedMaterialRequest.setMaterialRequest(materialRequest);
        approvedMaterialRequest.setExpectedDate(ExpectedDate);
        approvedMaterialRequest.setExpectedTime(ExpectedTime);

        approvedMaterialRequestRepository.save(approvedMaterialRequest);
    }

    @Override
    public void saveCancelRequest (Integer requestId, String cancelReason) {
        CanceledMaterialRequest canceledMaterialRequest = new CanceledMaterialRequest();
        MaterialRequest materialRequest = materialRequestRepository.findById(requestId).orElse(null);

        canceledMaterialRequest.setMaterialRequest(materialRequest);
        canceledMaterialRequest.setCancelReason(cancelReason);

        canceledMaterialRequestRepository.save(canceledMaterialRequest);

        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);
        if (approvedMaterialRequest != null)
            deleteApproveRequest(approvedMaterialRequest.getApproveId());
    }

    @Override
    public void saveCompleteRequest (Integer requestId, Integer points) {
        CompletedMaterialRequest completedMaterialRequest = new CompletedMaterialRequest();
        MaterialRequest materialRequest = materialRequestRepository.findById(requestId).orElse(null);

        completedMaterialRequest.setMaterialRequest(materialRequest);
        completedMaterialRequest.setPoints(points);

        completedMaterialRequestRepository.save(completedMaterialRequest);

        pointService.pointFromCompanyToUserWhenMaterialTrading(requestId, points);
    }

    @Override
    public void updateRequest (PostRequestDto postRequestDto) {
        if ("approved".equals(postRequestDto.getCollectionState())) {
            updateApproveRequest(postRequestDto.getRequestId(), postRequestDto.getExpectedDate(), postRequestDto.getExpectedTime());
        }
        else if ("completed".equals(postRequestDto.getCollectionState())) {
            updateCompleteRequest(postRequestDto.getRequestId(), postRequestDto.getPoints());
        }
        else if ("canceled".equals(postRequestDto.getCollectionState())) {
            updateCancelRequest(postRequestDto.getRequestId(), postRequestDto.getCancelReason());
        }
    }

    @Override
    public void updateApproveRequest (Integer requestId, String ExpectedDate, String ExpectedTime) {
        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);

        approvedMaterialRequest.setExpectedDate(ExpectedDate);
        approvedMaterialRequest.setExpectedTime(ExpectedTime);

        approvedMaterialRequestRepository.save(approvedMaterialRequest);
    }

    @Override
    public void updateCancelRequest (Integer requestId, String cancelReason) {
        CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByMaterialRequestRequestId(requestId);

        canceledMaterialRequest.setCancelReason(cancelReason);

        canceledMaterialRequestRepository.save(canceledMaterialRequest);


    }

    @Override
    public void updateCompleteRequest (Integer requestId, Integer points) {
        CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByMaterialRequestRequestId(requestId);

        completedMaterialRequest.setPoints(points);

        completedMaterialRequestRepository.save(completedMaterialRequest);
    }

    @Override
    public void deleteApproveRequest (Integer approveId) {
        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findById(approveId).orElse(null);
        System.out.println("why" + approveId);
        approvedMaterialRequestRepository.delete(approvedMaterialRequest);
    }


}
