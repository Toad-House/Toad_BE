package toad.toad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.MaterialGetDto;
import toad.toad.data.dto.MaterialPostDto;
import toad.toad.data.dto.MaterialRequestPostDto;
import toad.toad.data.entity.*;
import toad.toad.repository.*;
import toad.toad.service.MaterialService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final ApprovedMaterialRequestRepository approvedMaterialRequestRepository;
    private final CompletedMaterialRequestRepository completedMaterialRequestRepository;
    private final CanceledMaterialRequestRepository canceledMaterialRequestRepository;

    @Autowired
    private ImageServiceImpl imageService;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialRequestRepository materialRequestRepository, CompanyRepository companyRepository, UserRepository userRepository, ProductRepository productRepository, ApprovedMaterialRequestRepository approvedMaterialRequestRepository, CompletedMaterialRequestRepository completedMaterialRequestRepository, CanceledMaterialRequestRepository canceledMaterialRequestRepository, ImageServiceImpl imageService) {
        this.materialRepository = materialRepository;
        this.materialRequestRepository = materialRequestRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.approvedMaterialRequestRepository = approvedMaterialRequestRepository;
        this.completedMaterialRequestRepository = completedMaterialRequestRepository;
        this.canceledMaterialRequestRepository = canceledMaterialRequestRepository;
        this.imageService = imageService;
    }

    @Override
    public Integer saveMaterial(MaterialPostDto materialPostDto) throws Exception {
        Material material = new Material();
        Company company = companyRepository.findById(Integer.parseInt(materialPostDto.getCompanyId()))
                .orElseThrow(() -> new Exception("Invalid company id: " + materialPostDto.getCompanyId()));

        Product product = productRepository.findById(Integer.parseInt(materialPostDto.getProductId()))
                .orElseThrow(() -> new Exception("Invalid product id: " + materialPostDto.getProductId()));
        // 유효한 Company 인지 확인
        // 유효한 product 인지 확인

        material.setCompany(company);
        material.setMaterialName(materialPostDto.getMaterialName());
        material.setExpectedCondition(materialPostDto.getExpectedCondition());
        material.setMinimumQuantity(Integer.parseInt(materialPostDto.getMinimumQuantity()));
        material.setProduct(product);
        material.setPointsPerWeight(Integer.parseInt(materialPostDto.getPointsPerWeight()));
        material.setRestrictedArea(materialPostDto.getRestrictedArea());
        material.setAvailableArea(materialPostDto.getAvailableArea());

        if (!materialPostDto.getMultipartFile().isEmpty()) {
            String imageUrl = imageService.imageHandler(materialPostDto.getMultipartFile());
            material.setImageUrl(imageUrl);
        }

        Integer materialId = materialRepository.save(material).getMaterialId();

        return materialId;
    }

    @Override
    public List<MaterialGetDto> findAllMaterials() {
        List<Material> materials = materialRepository.findAll();
        List<MaterialGetDto> materialGetDtos = new ArrayList<>();

        for (Material material : materials) {
            MaterialGetDto materialGetDto = new MaterialGetDto();
            Company company = material.getCompany();
            Product product = material.getProduct();

            materialGetDto.setMaterialId(material.getMaterialId());
            materialGetDto.setCompanyId(company.getCompanyId());
            materialGetDto.setMaterialName(material.getMaterialName());
            materialGetDto.setExpectedCondition(material.getExpectedCondition());
            materialGetDto.setMinimumQuantity(material.getMinimumQuantity());
            materialGetDto.setProductId(product.getProductId());
            materialGetDto.setPointsPerWeight(material.getPointsPerWeight());
            materialGetDto.setRestrictedArea(material.getRestrictedArea());
            materialGetDto.setAvailableArea(material.getAvailableArea());
            materialGetDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + material.getImageUrl());

            materialGetDtos.add(materialGetDto);
        }
        return materialGetDtos;
    }

    public List<MaterialGetDto> findByMaterialNameContaining(String keyword) {
        List<Material> materials = materialRepository.findByMaterialNameContaining(keyword);
        List<MaterialGetDto> materialGetDtos = new ArrayList<>();

        for (Material material : materials) {
            MaterialGetDto materialGetDto = new MaterialGetDto();
            Company company = material.getCompany();
            Product product = material.getProduct();

            materialGetDto.setMaterialId(material.getMaterialId());
            materialGetDto.setCompanyId(company.getCompanyId());
            materialGetDto.setMaterialName(material.getMaterialName());
            materialGetDto.setExpectedCondition(material.getExpectedCondition());
            materialGetDto.setMinimumQuantity(material.getMinimumQuantity());
            materialGetDto.setProductId(product.getProductId());
            materialGetDto.setPointsPerWeight(material.getPointsPerWeight());
            materialGetDto.setRestrictedArea(material.getRestrictedArea());
            materialGetDto.setAvailableArea(material.getAvailableArea());
            materialGetDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + material.getImageUrl());

            materialGetDtos.add(materialGetDto);
        }
        return materialGetDtos;
    }

    @Override
    public MaterialGetDto findByMaterialId (Integer id) throws Exception {
        Material material = materialRepository.findById(id).orElseThrow(() -> new Exception("재료가 존재하지 않습니다."));
        MaterialGetDto materialGetDto = new MaterialGetDto();
        Company company = material.getCompany();
        Product product = material.getProduct();

        materialGetDto.setMaterialId(material.getMaterialId());
        materialGetDto.setCompanyId(company.getCompanyId());
        materialGetDto.setMaterialName(material.getMaterialName());
        materialGetDto.setExpectedCondition(material.getExpectedCondition());
        materialGetDto.setMinimumQuantity(material.getMinimumQuantity());
        materialGetDto.setProductId(product.getProductId());
        materialGetDto.setPointsPerWeight(material.getPointsPerWeight());
        materialGetDto.setRestrictedArea(material.getRestrictedArea());
        materialGetDto.setAvailableArea(material.getAvailableArea());
        materialGetDto.setImageUrl("https://storage.googleapis.com/" + bucketName + "/" + material.getImageUrl());

        return materialGetDto;
    }

    @Override
    public Integer saveMaterialRequest(MaterialRequestPostDto materialRequestPostDto) throws Exception {
        MaterialRequest materialRequest = new MaterialRequest();
        Material material = materialRepository.findById(Integer.parseInt(materialRequestPostDto.getMaterialId())).orElse(null);
        User user = userRepository.findById(Integer.parseInt(materialRequestPostDto.getUserId())).orElse(null);

        materialRequest.setMaterial(material);
        materialRequest.setUser(user);
        materialRequest.setQuantityOfMaterial(Integer.parseInt(materialRequestPostDto.getQuantityOfMaterial()));
        materialRequest.setCollectionArea(materialRequestPostDto.getCollectionArea());
        materialRequest.setCollectionState("applied");
        if (!materialRequestPostDto.getMultipartFile().isEmpty()) {
            String imageUrl = imageService.imageHandler(materialRequestPostDto.getMultipartFile());
            materialRequest.setImageUrl(imageUrl);
        }

        Integer requestId = materialRequestRepository.save(materialRequest).getRequestId();

        return requestId;
    }


    @Override
    public void deleteMaterial(Integer id) throws Exception {
        Material material = materialRepository.findById(id).orElseThrow(() -> new Exception());

        List<MaterialRequest> materialRequests = materialRequestRepository.findAllByMaterialMaterialId(material.getMaterialId());

        for (MaterialRequest materialRequest: materialRequests) {
            deleteMaterialRequest(materialRequest.getRequestId());
        }

        materialRepository.delete(material);
    }

    @Override
    public void deleteMaterialRequest(Integer id) throws Exception {
        MaterialRequest materialRequest = materialRequestRepository.findById(id).orElseThrow(() -> new Exception());

        List<ApprovedMaterialRequest> approvedMaterialRequests = approvedMaterialRequestRepository.findAllByMaterialRequestRequestId(materialRequest.getRequestId());
        for (ApprovedMaterialRequest approvedMaterialRequest : approvedMaterialRequests) {
            approvedMaterialRequestRepository.delete(approvedMaterialRequest);
        }

        List<CompletedMaterialRequest> completedMaterialRequests = completedMaterialRequestRepository.findAllByMaterialRequestRequestId(materialRequest.getRequestId());
        for (CompletedMaterialRequest completedMaterialRequest : completedMaterialRequests) {
            completedMaterialRequestRepository.delete(completedMaterialRequest);
        }

        List<CanceledMaterialRequest> canceledMaterialRequests = canceledMaterialRequestRepository.findAllByMaterialRequestRequestId(materialRequest.getRequestId());
        for (CanceledMaterialRequest canceledMaterialRequest : canceledMaterialRequests) {
            canceledMaterialRequestRepository.delete(canceledMaterialRequest);
        }

        materialRequestRepository.delete(materialRequest);
    }
}
