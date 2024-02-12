package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.dto.CompanyJoinDto;
import toad.toad.data.dto.UserJoinDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.User;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.UserRepository;
import toad.toad.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public AuthServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Exception joinUser(UserJoinDto userJoinDto) throws Exception {
        User user = User.builder()
                        .userName(userJoinDto.getUserName())
                        .password(userJoinDto.getPassword())
                        .userContact(userJoinDto.getUserContact())
                        .build();
        userRepository.save(user);
        return new Exception("유저 회원가입 성공");
    }

    @Override
    public Exception joinCompany(CompanyJoinDto companyJoinDto) throws Exception {
        Company company = Company.builder()
                                .companyName(companyJoinDto.getCompanyName())
                                .password(companyJoinDto.getPassword())
                                .companyContact(companyJoinDto.getCompanyContact())
                                .build();
        companyRepository.save(company);
        return new Exception("회사 회원가입 성공");
    }

}
