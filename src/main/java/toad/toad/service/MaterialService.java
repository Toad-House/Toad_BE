package toad.toad.service;

import toad.toad.data.dto.MaterialGetDto;
import toad.toad.data.dto.MaterialPostDto;
import toad.toad.data.dto.MaterialRequestPostDto;

import java.util.List;

public interface MaterialService {
    Integer saveMaterial(MaterialPostDto materialPostDto) throws Exception;

    List<MaterialGetDto> findAllMaterials();

    List<MaterialGetDto> findByMaterialNameContaining(String keyword);

    MaterialGetDto findByMaterialId (Integer id) throws Exception;

    Integer saveMaterialRequest(MaterialRequestPostDto materialRequestPostDto) throws Exception;

    void deleteMaterial(Integer id) throws Exception;

    void deleteMaterialRequest(Integer id) throws Exception;
}
