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
    public Integer saveApproveRequest (PostApproveRequestDto postApproveRequestDto) {
        ApprovedMaterialRequest approvedMaterialRequest = new ApprovedMaterialRequest();

        approvedMaterialRequest.setRequestId(postApproveRequestDto.getRequestId());
        approvedMaterialRequest.setExpectedDate(postApproveRequestDto.getExpectedDate());
        approvedMaterialRequest.setExpectedTime(postApproveRequestDto.getExpectedTime());

        approvedMaterialRequestRepository.save(approvedMaterialRequest);

        return approvedMaterialRequest.getApproveId();
    }

    @Override
    public Integer saveCancelRequest (PostCancelRequestDto postCancelRequestDto) {
        CanceledMaterialRequest canceledMaterialRequest = new CanceledMaterialRequest();

        canceledMaterialRequest.setRequestId(postCancelRequestDto.getRequestId());
        canceledMaterialRequest.setCancelReason(postCancelRequestDto.getCancelReason());

        canceledMaterialRequestRepository.save(canceledMaterialRequest);


        return canceledMaterialRequest.getCancelId();
    }

    @Override
    public Integer saveCompleteRequest (PostCompleteRequestDto postCompleteRequestDto) {
        CompletedMaterialRequest completedMaterialRequest = new CompletedMaterialRequest();

        completedMaterialRequest.setRequestId(postCompleteRequestDto.getRequestId());
        completedMaterialRequest.setPoints(postCompleteRequestDto.getPoints());

        completedMaterialRequestRepository.save(completedMaterialRequest);

        return completedMaterialRequest.getCompleteId();
    }

    @Override
    public Integer updateApproveRequest (PostApproveRequestDto postApproveRequestDto) {
        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(postApproveRequestDto.getRequestId());

        approvedMaterialRequest.setExpectedDate(postApproveRequestDto.getExpectedDate());
        approvedMaterialRequest.setExpectedTime(postApproveRequestDto.getExpectedTime());

        approvedMaterialRequestRepository.save(approvedMaterialRequest);

        return approvedMaterialRequest.getApproveId();
    }

    @Override
    public Integer updateCancelRequest (PostCancelRequestDto postCancelRequestDto) {
        CanceledMaterialRequest canceledMaterialRequest = canceledMaterialRequestRepository.findByRequestId(postCancelRequestDto.getRequestId());

        canceledMaterialRequest.setCancelReason(postCancelRequestDto.getCancelReason());

        canceledMaterialRequestRepository.save(canceledMaterialRequest);

        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(postCancelRequestDto.getRequestId());
        if (approvedMaterialRequest != null)
            deleteApproveRequest(approvedMaterialRequest.getRequestId());

        CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(postCancelRequestDto.getRequestId());
        if (completedMaterialRequest != null)
            deleteCompleteRequest(completedMaterialRequest.getRequestId());

        return canceledMaterialRequest.getCancelId();
    }

    @Override
    public Integer updateCompleteRequest (PostCompleteRequestDto postCompleteRequestDto) {
        CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(postCompleteRequestDto.getRequestId());

        completedMaterialRequest.setPoints(postCompleteRequestDto.getPoints());

        completedMaterialRequestRepository.save(completedMaterialRequest);

        return completedMaterialRequest.getCompleteId();
    }

    @Override
    public void deleteApproveRequest (Integer requestId) {
        ApprovedMaterialRequest approvedMaterialRequest = approvedMaterialRequestRepository.findByRequestId(requestId);

        approvedMaterialRequestRepository.delete(approvedMaterialRequest);
    }

    @Override
    public void deleteCompleteRequest (Integer requestId) {
        CompletedMaterialRequest completedMaterialRequest = completedMaterialRequestRepository.findByRequestId(requestId);

        completedMaterialRequestRepository.delete(completedMaterialRequest);
    }

}
