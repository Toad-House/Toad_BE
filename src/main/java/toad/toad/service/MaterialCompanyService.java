package toad.toad.service;

import org.springframework.stereotype.Service;
import toad.toad.data.dto.RequestCompanyDto;

import java.util.List;

public interface MaterialCompanyService {
    List<RequestCompanyDto> getAllRequests(Integer companyId);
}
