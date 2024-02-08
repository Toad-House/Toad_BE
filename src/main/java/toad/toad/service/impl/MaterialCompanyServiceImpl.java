package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.dto.*;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.MaterialCompanyService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialCompanyServiceImpl implements MaterialCompanyService {

    private final MaterialRepository materialRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final UserRepository userRepository;
    private final ApprovedMaterialRequestRepository approvedMaterialRequestRepository;
    private final CanceledMaterialRequestRepository canceledMaterialRequestRepository;
    private final CompletedMaterialRequestRepository completedMaterialRequestRepository;

    public MaterialCompanyServiceImpl(MaterialRepository materialRepository, MaterialRequestRepository materialRequestRepository, UserRepository userRepository, ApprovedMaterialRequestRepository approvedMaterialRequestRepository, CanceledMaterialRequestRepository canceledMaterialRequestRepository, CompletedMaterialRequestRepository completedMaterialRequestRepository) {
        this.materialRepository = materialRepository;
        this.materialRequestRepository = materialRequestRepository;
        this.userRepository = userRepository;
        this.approvedMaterialRequestRepository = approvedMaterialRequestRepository;
        this.canceledMaterialRequestRepository = canceledMaterialRequestRepository;
        this.completedMaterialRequestRepository = completedMaterialRequestRepository;
    }

    @Override
    public List<RequestCompanyDto> getAllRequests(Integer companyId) {
        List<Material> materials = materialRepository.findAllByCompanyId(companyId);
        List<RequestCompanyDto> requestCompanyDtos = new ArrayList<>();

        for (Material material : materials) {

            List<MaterialRequest> materialRequests = materialRequestRepository.findAllByMaterialId(material.getMaterialId());

            for (MaterialRequest materialRequest : materialRequests) {
                RequestCompanyDto requestCompanyDto = new RequestCompanyDto();

                requestCompanyDto.setMaterialId(material.getMaterialId());
                requestCompanyDto.setMaterialName(material.getMaterialName());

                requestCompanyDto.setRequestId(materialRequest.getRequestId());
                requestCompanyDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
                requestCompanyDto.setCollectionArea(materialRequest.getCollectionArea());
                requestCompanyDto.setCollectionState(materialRequest.getCollectionState());

                User user = userRepository.findById(materialRequest.getUserId()).orElse(null);

                requestCompanyDto.setUserName(user.getUserName());
                requestCompanyDto.setUserContact(user.getUserContact());

                if ("approved".equals(materialRequest.getCollectionState())) {
                    ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(requestCompanyDto.getRequestId());
                    requestCompanyDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
                    requestCompanyDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
                }
                else if ("completed".equals(materialRequest.getCollectionState())) {
                    CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(requestCompanyDto.getRequestId());
                    requestCompanyDto.setPoints(completedMaterialRequest.getPoints());
                }
                else if ("canceled".equals(materialRequest.getCollectionState())) {
                    CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByRequestId(requestCompanyDto.getRequestId());
                    requestCompanyDto.setCancelReason(canceledMaterialRequest.getCancelReason());
                }

                requestCompanyDtos.add(requestCompanyDto);

            }

        }
        return requestCompanyDtos;
    }

    @Override
    public RequestGetCompanyDto getOneRequest (Integer requestId) {
        MaterialRequest materialRequest = materialRequestRepository.findById(requestId).orElse(null);

        Material material = materialRepository.findById(materialRequest.getMaterialId()).orElse(null);
        User user = userRepository.findById(materialRequest.getUserId()).orElse(null);

        RequestGetCompanyDto requestGetCompanyDto = new RequestGetCompanyDto();

        requestGetCompanyDto.setMaterialId(material.getMaterialId());
        requestGetCompanyDto.setMaterialName(material.getMaterialName());
        requestGetCompanyDto.setProductId(material.getProductId());
        requestGetCompanyDto.setPointsPerWeight(material.getPointsPerWeight());
        requestGetCompanyDto.setMinimumQuantity(material.getMinimumQuantity());
        requestGetCompanyDto.setRestrictedArea(material.getRestrictedArea());
        requestGetCompanyDto.setAvailableArea(material.getAvailableArea());
        requestGetCompanyDto.setQuantityOfMaterial(materialRequest.getQuantityOfMaterial());
        requestGetCompanyDto.setCollectionArea(materialRequest.getCollectionArea());
        requestGetCompanyDto.setCollectionState(materialRequest.getCollectionState());

        requestGetCompanyDto.setUserName(user.getUserName());
        requestGetCompanyDto.setUserContact(user.getUserContact());

        if ("approved".equals(materialRequest.getCollectionState())) {
            ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(requestId);
            requestGetCompanyDto.setExpectedDate(approvedMaterialRequest.getExpectedDate());
            requestGetCompanyDto.setExpectedTime(approvedMaterialRequest.getExpectedTime());
        }
        else if ("completed".equals(materialRequest.getCollectionState())) {
            CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(requestId);
            requestGetCompanyDto.setPoints(completedMaterialRequest.getPoints());
        }
        else if ("canceled".equals(materialRequest.getCollectionState())) {
            CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByRequestId(requestId);
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

        approvedMaterialRequest.setRequestId(requestId);
        approvedMaterialRequest.setExpectedDate(ExpectedDate);
        approvedMaterialRequest.setExpectedTime(ExpectedTime);

        approvedMaterialRequestRepository.save(approvedMaterialRequest);
    }

    @Override
    public void saveCancelRequest (Integer requestId, String cancelReason) {
        CanceledMaterialRequest canceledMaterialRequest = new CanceledMaterialRequest();

        canceledMaterialRequest.setRequestId(requestId);
        canceledMaterialRequest.setCancelReason(cancelReason);

        canceledMaterialRequestRepository.save(canceledMaterialRequest);

        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(requestId);
        if (approvedMaterialRequest != null)
            deleteApproveRequest(approvedMaterialRequest.getApproveId());
    }

    @Override
    public void saveCompleteRequest (Integer requestId, Integer points) {
        CompletedMaterialRequest completedMaterialRequest = new CompletedMaterialRequest();

        completedMaterialRequest.setRequestId(requestId);
        completedMaterialRequest.setPoints(points);

        completedMaterialRequestRepository.save(completedMaterialRequest);
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
        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(requestId);

        approvedMaterialRequest.setExpectedDate(ExpectedDate);
        approvedMaterialRequest.setExpectedTime(ExpectedTime);

        approvedMaterialRequestRepository.save(approvedMaterialRequest);
    }

    @Override
    public void updateCancelRequest (Integer requestId, String cancelReason) {
        CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByRequestId(requestId);

        canceledMaterialRequest.setCancelReason(cancelReason);

        canceledMaterialRequestRepository.save(canceledMaterialRequest);


    }

    @Override
    public void updateCompleteRequest (Integer requestId, Integer points) {
        CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(requestId);

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
