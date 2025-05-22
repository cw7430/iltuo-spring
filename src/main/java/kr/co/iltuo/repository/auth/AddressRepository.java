package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.Address;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByUserIdxAndValidTrueAndMainTrue(Long userIdx);
    List<Address> findByUserIdxAndValidTrue(Long userIdx, Sort sort);

    @Modifying
    @Query(value = "UPDATE `address` SET `is_valid` = false, `is_main` = false WHERE `address_id` IN (:addressIds)", nativeQuery = true)
    void invalidateAddresses(@Param("addressIds") List<Long> addressIds);
}
