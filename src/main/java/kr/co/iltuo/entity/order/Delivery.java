package kr.co.iltuo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "`delivery`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Delivery {

    @Id
    @Column(name = "`payment_id`", nullable = false)
    private Long paymentId;

    @Builder.Default
    @Column(name = "`delivery_status_code`", nullable = false, length = 6)
    private String deliveryStatusCode = "DS001";

    @Column(name = "`postal_code`", nullable = false, length = 7)
    private String postalCode;

    @Column(name = "`default_address`", nullable = false, columnDefinition = "TEXT")
    private String defaultAddress;

    @Column(name = "`detail_address`", columnDefinition = "TEXT")
    private String detailAddress;

    @Column(name = "`extra_address`", columnDefinition = "TEXT")
    private String extraAddress;

    @Column(name = "`courier_company`", length = 100)
    private String courierCompany;

    @Column(name = "`invoice_number`", length = 100)
    private String invoiceNumber;

    @Column(name = "`delivery_date`")
    private Instant deliveryDate;

    @Column(name = "`arrive_date`")
    private Instant arriveDate;
}
