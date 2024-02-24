package toad.toad.service;

import toad.toad.data.dto.*;

import java.util.List;

public interface MaterialCompanyService {
    List<RequestCompanyDto> getAllRequests(Integer companyId);

    RequestGetCompanyDto getOneRequest (Integer requestId);

    void saveRequest (PostRequestDto postRequestDto);

    void saveApproveRequest (Integer requestId, String ExpectedDate, String ExpectedTime);

    void saveCompleteRequest (Integer requestId, Integer points);

    void saveCancelRequest (Integer requestId, String cancelReason);

    void updateRequest (PostRequestDto postRequestDto);

    void updateApproveRequest (Integer requestId, String ExpectedDate, String ExpectedTime);

    void updateCancelRequest (Integer requestId, String cancelReason);

    void updateCompleteRequest (Integer requestId, Integer points);

    void deleteApproveRequest (Integer requestId);

}
