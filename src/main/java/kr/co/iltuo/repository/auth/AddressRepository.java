package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.Address;
import org.springframework.data.jpa.repository.*;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
