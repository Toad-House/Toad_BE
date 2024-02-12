package toad.toad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.MaterialGetDto;
import toad.toad.data.dto.MaterialPostDto;
import toad.toad.data.dto.MaterialRequestPostDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.Material;
import toad.toad.data.entity.MaterialRequest;
import toad.toad.data.entity.User;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.MaterialRepository;
import toad.toad.repository.MaterialRequestRepository;
import toad.toad.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository, MaterialRequestRepository materialRequestRepository, CompanyRepository companyRepository, UserRepository userRepository) throws Exception {
        this.materialRepository = materialRepository;
        this.materialRequestRepository = materialRequestRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Integer saveMaterial(MaterialPostDto materialPostDto) throws Exception {
        Material material = new Material();
        Company company = companyRepository.findById(materialPostDto.getCompanyId())
                .orElseThrow(() -> new Exception("Invalid company id: " + materialPostDto.getCompanyId()));

        // 유효한 Company 인지 확인
        // 유효한 product 인지 확인

        material.setCompany(company);
        material.setMaterialName(materialPostDto.getMaterialName());
        material.setExpectedCondition(materialPostDto.getExpectedCondition());
        material.setMinimumQuantity(materialPostDto.getMinimumQuantity());
        material.setProductId(materialPostDto.getProductId());
        material.setPointsPerWeight(materialPostDto.getPointsPerWeight());
        material.setRestrictedArea(materialPostDto.getRestrictedArea());
        material.setAvailableArea(materialPostDto.getAvailableArea());

        Integer materialId = materialRepository.save(material).getMaterialId();

        return materialId;
    }

    public List<MaterialGetDto> findAllMaterials() throws Exception {
        List<Material> materials = materialRepository.findAll();
        List<MaterialGetDto> materialGetDtos = new ArrayList<>();

        for (Material material : materials) {
            MaterialGetDto materialGetDto = new MaterialGetDto();
            Company company = material.getCompany();

            materialGetDto.setMaterialId(material.getMaterialId());
            materialGetDto.setCompanyId(company.getCompanyId());
            materialGetDto.setMaterialName(material.getMaterialName());
            materialGetDto.setExpectedCondition(material.getExpectedCondition());
            materialGetDto.setMinimumQuantity(material.getMinimumQuantity());
            materialGetDto.setProductId(material.getProductId());
            materialGetDto.setPointsPerWeight(material.getPointsPerWeight());
            materialGetDto.setRestrictedArea(material.getRestrictedArea());
            materialGetDto.setAvailableArea(material.getAvailableArea());

            materialGetDtos.add(materialGetDto);
        }
        return materialGetDtos;
    }

    public List<MaterialGetDto> findByMaterialNameContaining(String keyword) throws Exception {
        List<Material> materials = materialRepository.findByMaterialNameContaining(keyword);
        List<MaterialGetDto> materialGetDtos = new ArrayList<>();

        for (Material material : materials) {
            MaterialGetDto materialGetDto = new MaterialGetDto();
            Company company = material.getCompany();

            materialGetDto.setMaterialId(material.getMaterialId());
            materialGetDto.setCompanyId(company.getCompanyId());
            materialGetDto.setMaterialName(material.getMaterialName());
            materialGetDto.setExpectedCondition(material.getExpectedCondition());
            materialGetDto.setMinimumQuantity(material.getMinimumQuantity());
            materialGetDto.setProductId(material.getProductId());
            materialGetDto.setPointsPerWeight(material.getPointsPerWeight());
            materialGetDto.setRestrictedArea(material.getRestrictedArea());
            materialGetDto.setAvailableArea(material.getAvailableArea());

            materialGetDtos.add(materialGetDto);
        }
        return materialGetDtos;
    }

    public MaterialGetDto findByMaterialId (Integer id) throws Exception {
        Material material = materialRepository.findById(id).orElseThrow(() -> new Exception("재료가 존재하지 않습니다."));
        MaterialGetDto materialGetDto = new MaterialGetDto();
        Company company = material.getCompany();

        materialGetDto.setMaterialId(material.getMaterialId());
        materialGetDto.setCompanyId(company.getCompanyId());
        materialGetDto.setMaterialName(material.getMaterialName());
        materialGetDto.setExpectedCondition(material.getExpectedCondition());
        materialGetDto.setMinimumQuantity(material.getMinimumQuantity());
        materialGetDto.setProductId(material.getProductId());
        materialGetDto.setPointsPerWeight(material.getPointsPerWeight());
        materialGetDto.setRestrictedArea(material.getRestrictedArea());
        materialGetDto.setAvailableArea(material.getAvailableArea());

        return materialGetDto;
    }

    public Integer saveMaterialRequest(MaterialRequestPostDto materialRequestPostDto) throws Exception {
        MaterialRequest materialRequest = new MaterialRequest();
        Material material = materialRepository.findById(materialRequestPostDto.getMaterialId()).orElse(null);
        User user = userRepository.findById(materialRequestPostDto.getUserId()).orElse(null);

        materialRequest.setMaterial(material);
        materialRequest.setUser(user);
        materialRequest.setQuantityOfMaterial(materialRequestPostDto.getQuantityOfMaterial());
        materialRequest.setCollectionArea(materialRequestPostDto.getCollectionArea());
        materialRequest.setCollectionState("applied");

        Integer requestId = materialRequestRepository.save(materialRequest).getRequestId();

        return requestId;
    }
}
