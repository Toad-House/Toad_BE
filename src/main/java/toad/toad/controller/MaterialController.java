package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import toad.toad.data.dto.MaterialPostDto;
import toad.toad.service.MaterialService;

import java.util.Map;

@Controller
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Operation(summary = "재료 등록", description = "minimum_quantity, expected_condition, restricted_area는 null이어도 됨. 폼데이터로 보내줘야 됨")
    @PostMapping("/material")
    public void createMaterial (@RequestBody MaterialPostDto materialPostDto) throws Exception {
        materialService.saveMaterial(materialPostDto);
    }

}
