package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.Address;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query(value = "SELECT * FROM `address` WHERE `user_idx` = :userIdx and `is_main` = true", nativeQuery = true)
    Address findByMainAddressByUserIdx(@Param("user_idx") Long userIdx);
}
