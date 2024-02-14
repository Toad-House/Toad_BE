package toad.toad.service;


import toad.toad.data.dto.CompanyJoinDto;
import toad.toad.data.dto.UserJoinDto;
import toad.toad.data.entity.Company;
import toad.toad.data.entity.User;

import java.util.List;

public interface AuthService {
    Exception joinUser(UserJoinDto userJoinDto) throws Exception;

    Exception joinCompany(CompanyJoinDto companyJoinDto) throws Exception;

    List<User> findAllUsers();

    List<Company> findAllCompanies();

}
