package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toad.toad.data.dto.CompanyJoinDto;
import toad.toad.data.dto.UserJoinDto;
import toad.toad.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "사용자 회원가입", description = "사용자용 회원가입 api 입니다.")
    @PostMapping("/join/user")
    public ResponseEntity<Boolean> joinUser(@RequestBody UserJoinDto userJoinDto) throws Exception {
        authService.joinUser(userJoinDto);
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "회사 회원가입", description = "회사용 회원가입 api 입니다.")
    @PostMapping("/join/company")
    public ResponseEntity<Boolean> joinCompany(@RequestBody CompanyJoinDto companyJoinDto) throws Exception {
        authService.joinCompany(companyJoinDto);
        return ResponseEntity.ok(true);
    }



}
