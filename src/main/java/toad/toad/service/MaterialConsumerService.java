package toad.toad.service;

import toad.toad.data.dto.PatchConsumerRequestDto;
import toad.toad.data.dto.PostConsumerRequestDto;
import toad.toad.data.dto.RequestConsumerDto;
import toad.toad.data.dto.RequestGetConsumerDto;

import java.util.List;

public interface MaterialConsumerService {
    List<RequestConsumerDto> getAllRequests(Integer userId);

    RequestGetConsumerDto getOneRequest(Integer requestId);

    void updateRequest(PatchConsumerRequestDto patchConsumerRequestDto);

    Integer saveCancelRequest(PostConsumerRequestDto postConsumerRequestDto);
}
