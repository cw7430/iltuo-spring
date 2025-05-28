package kr.co.iltuo.service.product;

import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.IdxRequestDto;
import kr.co.iltuo.dto.response.product.ProductDataResponseDto;
import kr.co.iltuo.entity.product.*;
import kr.co.iltuo.repository.product.*;
import kr.co.iltuo.service.product.util.ProductEntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final MajorCategoryRepository majorCategoryRepository;
    private final MinerCategoryRepository minerCategoryRepository;
    private final ProductViewRepository productViewRepository;
    private final OptionRepository optionRepository;
    private final OptionViewRepository optionViewRepository;

    @Override
    public List<MajorCategory> getMajorCategoryList() {
        return majorCategoryRepository.findByValidTrue();
    }

    @Override
    public List<ProductDataResponseDto> getRecommendedProductList() {
        List<ProductView> productList = productViewRepository.findByRecommendedTrue();
        return ProductEntityUtil.makeProductList(productList);
    }

    @Override
    public List<MinerCategory> getMinerCategoryList(IdxRequestDto idxRequestDto) {
        return minerCategoryRepository.findByMajorCategoryIdAndValidTrue(idxRequestDto.getIdx());
    }

    @Override
    public List<ProductDataResponseDto> getProductList(IdxRequestDto idxRequestDto) {
        List<ProductView> productList = productViewRepository.findByMajorCategoryId(idxRequestDto.getIdx());
        return ProductEntityUtil.makeProductList(productList);
    }

    @Override
    public ProductDataResponseDto getProduct(IdxRequestDto idxRequestDto) {
        ProductView product = productViewRepository.findById(idxRequestDto.getIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
        return ProductEntityUtil.makeProductData(product);
    }

    @Override
    public List<Option> getOptionList(IdxRequestDto idxRequestDto) {
        return optionRepository.findByMajorCategoryIdAndValidTrue(idxRequestDto.getIdx());
    }

    @Override
    public List<OptionView> getOptionDetailList(IdxRequestDto idxRequestDto) {
        return optionViewRepository.findByMajorCategoryId(idxRequestDto.getIdx());
    }

}
