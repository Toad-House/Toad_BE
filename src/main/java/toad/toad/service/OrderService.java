package toad.toad.service;

import toad.toad.data.dto.OrderGetDto;
import toad.toad.data.dto.OrderPostDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    int saveOrder(OrderPostDto orderPostDto) throws Exception;

    List<OrderGetDto> getAllMyOrders(int userId);

    Optional<OrderGetDto> getOneOrder(int orderId);
}
