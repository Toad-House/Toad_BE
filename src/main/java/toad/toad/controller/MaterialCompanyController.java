package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toad.toad.data.dto.RequestCompanyDto;
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

}
