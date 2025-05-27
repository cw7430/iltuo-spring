package kr.co.iltuo.service.global.util;

public class CalculateUtil {
    public static long calculateDiscountPrice(long price, int discountedRate) {
        long discountedPrice = price * (100 - discountedRate) / 100;
        if (discountedPrice % 10 != 0) {
            discountedPrice = ((discountedPrice / 10) + 1) * 10;
        }
        return discountedPrice;
    }

    public static long calculateOptionPrice(long price, int fluctuatingValue, String optionTypeCode) {
        if ("OPT002".equals(optionTypeCode)) return fluctuatingValue;
        long adjusted = (price * fluctuatingValue) / 100;
        return ((adjusted / 10) * 10) - price;
    }
}
