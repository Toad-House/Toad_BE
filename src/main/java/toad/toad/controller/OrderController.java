package toad.toad.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toad.toad.data.dto.OrderGetDto;
import toad.toad.data.dto.OrderPostDto;
import toad.toad.data.dto.UserInfoDto;
import toad.toad.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // CREATE
    @Operation(summary = "상품 주문하기", description = "상품을 주문하는 api 입니다.")
    @PostMapping("/new")
    public ResponseEntity<Integer> createOrder(@RequestBody OrderPostDto orderPostDto) throws Exception {
        int orderId = orderService.saveOrder(orderPostDto);
        return ResponseEntity.ok(orderId);
    }

    // READ
    @Operation(summary = "나의 주문 리스트 조회하기", description = "자신이 주문했던 항목에 대한 리스트를 반환하는 api 입니다.")
    @GetMapping("/list")
    public ResponseEntity<List<OrderGetDto>> getAllMyOrders( @RequestParam int userId) {
        List<OrderGetDto> allMyOrders = orderService.getAllMyOrders(userId);
        return ResponseEntity.ok(allMyOrders);
    }

    @Operation(summary = "개별 주문 조회하기", description = "개별 주문 정보를 조회하는 api 입니다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<Optional<OrderGetDto>> getOneMyOrder(@PathVariable int orderId) {
        Optional<OrderGetDto> targetOrder = orderService.getOneOrder(orderId);
        return ResponseEntity.ok(targetOrder);
    }
}
