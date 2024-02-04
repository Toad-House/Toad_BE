package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.dto.RequestCompanyDto;
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
        List<RequestCompanyDto> requestcompanyDtos = new ArrayList<>();

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

                requestcompanyDtos.add(requestCompanyDto);

            }

        }
        return requestcompanyDtos;
    }
}
