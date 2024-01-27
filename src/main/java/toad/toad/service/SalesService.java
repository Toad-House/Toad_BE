package toad.toad.service;

import toad.toad.data.dto.SalesDetailDto;
import toad.toad.data.dto.SalesSimpleDto;

import java.util.List;
import java.util.Optional;

public interface SalesService {

    List<SalesSimpleDto> getAllSales(int companyId);

    List<SalesDetailDto> getSalesByProductId(int productId);

    Optional<SalesDetailDto> getOneSales(int orderId);
}
