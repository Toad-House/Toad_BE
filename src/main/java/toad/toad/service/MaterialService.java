package toad.toad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.MaterialPostDto;
import toad.toad.data.entity.Material;
import toad.toad.repository.MaterialRepository;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Integer saveMaterial(MaterialPostDto materialPostDto) throws Exception {
        Material material = new Material();

        // 유효한 Company 인지 확인
        // 유효한 product 인지 확인

        material.setCompanyId(materialPostDto.getCompanyId());
        material.setMaterialName(materialPostDto.getMaterialName());
        material.setExpectedCondition(materialPostDto.getExpectedCondition());
        material.setMinimumQuantity(materialPostDto.getMinimumQuantity());
        material.setPointsPerWeight(materialPostDto.getPointsPerWeight());
        material.setRestrictedArea(materialPostDto.getRestrictedArea());
        material.setAvailableArea(materialPostDto.getAvailableArea());

        materialRepository.save(material);

        return 0;
    }

}
