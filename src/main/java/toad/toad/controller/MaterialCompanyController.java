package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.*;
import toad.toad.service.MaterialCompanyService;

import java.util.List;

@Controller
@RequestMapping("/company")
public class MaterialCompanyController {
    private final MaterialCompanyService materialCompanyService;

    @Autowired
    public MaterialCompanyController (MaterialCompanyService materialCompanyService) {
        this.materialCompanyService = materialCompanyService;
    }

    @Operation(summary="재료 제공 요청 보기", description = "회사의 마이페이지에서 유저들이 회사로 재료 제공 요청을 보낸 list들을 볼 수 있습니다.")
    @GetMapping("/request")
    public ResponseEntity<List<RequestCompanyDto>> showAllRequest (@RequestParam(name="companyId") Integer companyId) throws Exception {
        List<RequestCompanyDto> requestCompanyDtos = materialCompanyService.getAllRequests(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(requestCompanyDtos);
    }

    @Operation(summary="재료 제공 요청 상세 보기", description = "회사의 마이페이지에서 유저들이 회사로 재료 제공 요청을 보낸 list 중 하나를 볼 수 있습니다.")
    @GetMapping("/request/{requestId}")
    public ResponseEntity<RequestGetCompanyDto> showOneRequest (@PathVariable(name="requestId") Integer requestId) {
        RequestGetCompanyDto requestGetCompanyDto = materialCompanyService.getOneRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(requestGetCompanyDto);
    }

    @Operation(summary = "재료 제공 상태 변경", description = "승인 상태로 변경 시, 'collectionArea' = 'approved'" + "완료 상태로 변경 시, 'collectionArea' = 'completed'" + "취소 상태로 변경 시, 'collectionArea' = 'canceled'")
    @PostMapping("/request")
    public ResponseEntity<Void> saveRequest (@RequestBody PostRequestDto postRequestDto) {
        materialCompanyService.saveRequest(postRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "재료 제공 요청 승인 내용 수정", description = "승인 상태에서 수정 시, 'collectionArea' = 'approved'" + "완료 상태에서 수정 시, 'collectionArea' = 'completed'" + "취소 상태에서 수정 시, 'collectionArea' = 'canceled'")
    @PatchMapping("/request")
    public ResponseEntity<Void> updateApproveRequest (@RequestBody PostRequestDto postRequestDto) {
        materialCompanyService.updateRequest(postRequestDto);
        return ResponseEntity.ok().build();
    }

}
