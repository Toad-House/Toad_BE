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

    @Operation(summary = "재료 제공 요청 승인")
    @PostMapping("/request/approve")
    public ResponseEntity<Integer> saveApproveRequest (@RequestBody PostApproveRequestDto postApproveRequestDto) {
        Integer approveId = materialCompanyService.saveApproveRequest(postApproveRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(approveId);
    }

    @Operation(summary = "재료 재공 요청 취소")
    @PostMapping("/request/cancel")
    public ResponseEntity<Integer> saveCancelRequest (@RequestBody PostCancelRequestDto postCancelRequestDto) {
        Integer cancelId = materialCompanyService.saveCancelRequest(postCancelRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(cancelId);
    }

    @Operation(summary = "재료 제공 요청 완료")
    @PostMapping("/request/complete")
    public ResponseEntity<Integer> saveCompleteRequest (@RequestBody PostCompleteRequestDto postCompleteRequestDto) {
        Integer completeId = materialCompanyService.saveCompleteRequest(postCompleteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(completeId);
    }

    @Operation(summary = "재료 제공 요청 승인 내용 수정")
    @PatchMapping("/request/approve")
    public ResponseEntity<Integer> updateApproveRequest (@RequestBody PostApproveRequestDto postApproveRequestDto) {
        Integer approveId = materialCompanyService.updateApproveRequest(postApproveRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(approveId);
    }

    @Operation(summary = "재료 제공 요청 취소 내용 수정")
    @PatchMapping("/request/cancel")
    public ResponseEntity<Integer> updateCancelRequest (@RequestBody PostCancelRequestDto postCancelRequestDto) {
        Integer cancelId = materialCompanyService.updateCancelRequest(postCancelRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(cancelId);
    }

    @Operation(summary = "재료 제공 요청 완료 내용 수정")
    @PatchMapping("/request/complete")
    public ResponseEntity<Integer> updateCompleteRequest (@RequestBody PostCompleteRequestDto postCompleteRequestDto) {
        Integer completeId = materialCompanyService.updateCompleteRequest(postCompleteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(completeId);
    }
}
