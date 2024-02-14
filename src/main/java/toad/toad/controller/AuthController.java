package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.CompanyJoinDto;
import toad.toad.data.dto.UserJoinDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.User;
import toad.toad.service.AuthService;

import java.util.List;

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

    @Operation(summary = "사용자 목록 조회")
    @GetMapping("/user")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = authService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "회사 목록 조회")
    @GetMapping("/company")
    public ResponseEntity<List<Company>> findAllCompanies() {
        List<Company> companies = authService.findAllCompanies();
        return ResponseEntity.ok(companies);
    }


}
