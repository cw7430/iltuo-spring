package kr.co.iltuo.repository.product;

import kr.co.iltuo.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}