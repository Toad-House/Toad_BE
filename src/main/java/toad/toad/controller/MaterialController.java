package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.MaterialGetDto;
import toad.toad.data.dto.MaterialPostDto;
import toad.toad.data.dto.MaterialRequestPostDto;
import toad.toad.service.MaterialService;

import java.util.List;

@Controller
@RequestMapping("/material")
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Operation(summary = "재료 등록", description = "minimum_quantity, expected_condition, restricted_area는 null이어도 됨.")
    @PostMapping
    public ResponseEntity<Integer> createMaterial (@ModelAttribute MaterialPostDto materialPostDto) throws Exception {
        System.out.println(materialPostDto);
        Integer materialId = materialService.saveMaterial(materialPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(materialId);
    }

    @Operation(summary = "모든 재료 보기", description = "모든 재료를 볼 수 있는 api 입니다.")
    @GetMapping("/all")
    public ResponseEntity<List<MaterialGetDto>> showAllMaterials () throws Exception {
        List<MaterialGetDto> materialGetDtos = materialService.findAllMaterials();
        return ResponseEntity.status(HttpStatus.OK).body(materialGetDtos);
    }

    @Operation(summary = "재료 검색", description = "검색한 재료들만 보내주는 api 입니다.")
    @GetMapping("/search")
    public ResponseEntity<List<MaterialGetDto>> searchMaterialByKeyword (@RequestParam(name = "keyword") String keyword) throws Exception {
        List<MaterialGetDto> materialGetDtos = materialService.findByMaterialNameContaining(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(materialGetDtos);
    }

    @Operation(summary = "재료 상세 페이지", description = "한 재료의 상세 페이지를 위한 api 입니다.")
    @GetMapping("/detail/{materialId}")
    public ResponseEntity<MaterialGetDto> showMaterialDetail (@PathVariable(name="materialId") Integer materialId) throws Exception {
        MaterialGetDto materialGetDto = materialService.findByMaterialId(materialId);
        return ResponseEntity.status(HttpStatus.OK).body(materialGetDto);
    }

    @Operation(summary = "재료 요청 api", description = "유저가 회사에 재료 제공 요청을 할 때 사용하는 api입니다.")
    @PostMapping("/request")
    public ResponseEntity<Integer> createMaterialRequest (@ModelAttribute MaterialRequestPostDto materialRequestPostDto) throws Exception {
        Integer requestId = materialService.saveMaterialRequest(materialRequestPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(requestId);
    }

    @Operation(summary = "재료 삭제")
    @DeleteMapping("/{materialId}")
    public ResponseEntity deleteMaterial (@PathVariable(name="materialId") Integer materialId) throws Exception {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.status(HttpStatus.OK).body("delete complete");
    }

    @Operation(summary = "재료 요청 삭제")
    @DeleteMapping("/request/{requestId}")
    public ResponseEntity deleteMaterialRequest (@PathVariable(name="requestId") Integer requestId) throws Exception {
        materialService.deleteMaterialRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).body("delete complete");
    }
}
