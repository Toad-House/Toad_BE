package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.PatchConsumerRequestDto;
import toad.toad.data.dto.PostConsumerRequestDto;
import toad.toad.data.dto.RequestConsumerDto;
import toad.toad.data.dto.RequestGetConsumerDto;
import toad.toad.service.MaterialConsumerService;

import java.util.List;

@Controller
@RequestMapping("/consumer")
public class MaterialConsumerController {
    private final MaterialConsumerService materialConsumerService;

    public MaterialConsumerController(MaterialConsumerService materialConsumerService) {
        this.materialConsumerService = materialConsumerService;
    }

    @Operation(summary = "Buyer 재료 제공 요청 보기")
    @GetMapping("/request")
    public ResponseEntity<List<RequestConsumerDto>> showAllRequest (@RequestParam(name = "userId") Integer userId) {
        List<RequestConsumerDto> requestConsumerDtos = materialConsumerService.getAllRequests(userId);
        return ResponseEntity.status(HttpStatus.OK).body(requestConsumerDtos);
    }

    @Operation(summary = "Buyer 재료 제공 요청 상세 보기")
    @GetMapping("/request/{requestId}")
    public ResponseEntity<RequestGetConsumerDto> showOneRequest(@PathVariable(name = "requestId") Integer requestId) {
        RequestGetConsumerDto requestGetConsumerDto = materialConsumerService.getOneRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(requestGetConsumerDto);
    }

    @Operation(summary = "Buyer 재료 제공 요청 정보 변경", description = "신청 상태에서 수정 시, 'collectionArea' = 'applicated'" + "취소 상태에서 수정 시, 'collectionArea' = 'canceled'")
    @PatchMapping("/request")
    public ResponseEntity<Void> updateRequest (@RequestBody PatchConsumerRequestDto patchConsumerRequestDto) {
        materialConsumerService.updateRequest(patchConsumerRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Buyer 재료 제공 취소")
    @PostMapping("/request")
    public ResponseEntity<Integer> saveCancelRequest (@RequestBody PostConsumerRequestDto postConsumerRequestDto) {
        Integer cancelId = materialConsumerService.saveCancelRequest(postConsumerRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(cancelId);
    }
}
