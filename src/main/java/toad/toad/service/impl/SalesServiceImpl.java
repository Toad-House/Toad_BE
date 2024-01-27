package toad.toad.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toad.toad.data.dto.SalesDetailDto;
import toad.toad.data.dto.SalesSimpleDto;
import toad.toad.data.entity.Order;
import toad.toad.data.entity.Product;
import toad.toad.repository.OrderRepository;
import toad.toad.repository.ProductRepository;
import toad.toad.service.SalesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SalesServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SalesSimpleDto> getAllSales(int companyId) {
        List<Product> productList = productRepository.findByCompany_CompanyId(companyId);
        return productList.stream()
                .map(product -> {
                    int productId = product.getProductId();
                    int totalSalesQuantity = getTotalSalesQuantityByProductId(productId);
                    SalesSimpleDto salesSimpleDto = modelMapper.map(product, SalesSimpleDto.class);
                    salesSimpleDto.setSalesQuantity(totalSalesQuantity);
                    return salesSimpleDto;
                }).collect(Collectors.toList());
    }

    private int getTotalSalesQuantityByProductId(int productId) {
        return orderRepository.getTotalSalesQuantityByProductId(productId).orElse(0);
    }

    @Override
    public List<SalesDetailDto> getSalesByProductId(int productId) {
        List<Order> orderList = orderRepository.findByProduct_ProductId(productId);
        return orderList.stream()
                .map(order -> modelMapper.map(order, SalesDetailDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SalesDetailDto> getOneSales(int orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order, SalesDetailDto.class));
    }
}
