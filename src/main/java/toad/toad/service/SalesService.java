package toad.toad.service;

import toad.toad.data.dto.SalesGetDetailDto;
import toad.toad.data.dto.SalesGetSimpleDto;

import java.util.List;
import java.util.Optional;

public interface SalesService {

    List<SalesGetSimpleDto> getAllSales(int companyId);

    List<SalesGetDetailDto> getSalesByProductId(int productId);

    Optional<SalesGetDetailDto> getOneSales(int orderId);
}
