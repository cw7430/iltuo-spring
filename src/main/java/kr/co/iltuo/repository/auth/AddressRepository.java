package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.Address;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByUserIdxAndIsMainTrue(Long userIdx);
}
