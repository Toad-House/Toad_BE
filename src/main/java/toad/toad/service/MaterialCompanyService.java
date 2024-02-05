package toad.toad.service;

import toad.toad.data.dto.*;
import toad.toad.data.entity.ApprovedMaterialRequest;

import java.util.List;

public interface MaterialCompanyService {
    List<RequestCompanyDto> getAllRequests(Integer companyId);

    RequestGetCompanyDto getOneRequest (Integer requestId);

    Integer saveApproveRequest (PostApproveRequestDto postApproveRequestDto);

    Integer saveCancelRequest (PostCancelRequestDto postCancelRequestDto);

    Integer saveCompleteRequest (PostCompleteRequestDto postCompleteRequestDto);

    Integer updateApproveRequest (PostApproveRequestDto postApproveRequestDto);

    Integer updateCancelRequest (PostCancelRequestDto postCancelRequestDto);

    Integer updateCompleteRequest (PostCompleteRequestDto postCompleteRequestDto);

    void deleteApproveRequest (Integer requestId);

    void deleteCompleteRequest (Integer requestId);
}
