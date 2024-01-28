package toad.toad.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.OrderGetDto;
import toad.toad.data.dto.OrderPostDto;
import toad.toad.data.entity.Order;
import toad.toad.data.entity.Product;
import toad.toad.data.entity.User;
import toad.toad.repository.CompanyRepository;
import toad.toad.repository.OrderRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.repository.UserRepository;
import toad.toad.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int saveOrder(OrderPostDto orderPostDto) throws Exception {
        // user & produdct 이미 존재하는지 체크
        User user = userRepository.findById(orderPostDto.getUserId())
                        .orElseThrow(() -> new Exception("User not found"));
        Product product = productRepository.findById(orderPostDto.getProductId())
                        .orElseThrow(() -> new Exception("Product not found"));

        Order newOrder = Order.builder()
                            .user(user)
                            .product(product)
                            .orderNum(orderPostDto.getOrderNum())
                            .build();

        Order savedOrder = orderRepository.save(newOrder);
        return savedOrder.getOrderId();
    }


    @Override
    public List<OrderGetDto> getAllMyOrders(int userId) {
        List<Order> orders = orderRepository.findByUser_UserId(userId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderGetDto> getOneOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        return orderOptional.map(order -> modelMapper.map(order, OrderGetDto.class));
    }
}
