package kr.co.iltuo.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.Instant;

@Entity
@Table(name = "`payment_view`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class PaymentView {
    @Id
    @Column(name = "`payment_id`")
    private Long paymentId;

    @Column(name = "`user_idx`")
    private Long userIdx;

    @Column(name = "`payment_method_code`")
    private String paymentMethodCode;

    @Column(name = "`total_price`")
    private long totalPrice;

    @Column(name = "`delivery_price`")
    private long deliveryPrice;

    @Column(name = "`payment_date`")
    private Instant paymentDate;

    @Column(name = "`postal_code`")
    private String postalCode;

    @Column(name = "`default_address`", columnDefinition = "TEXT")
    private String defaultAddress;

    @Column(name = "`detail_address`", columnDefinition = "TEXT")
    private String detailAddress;

    @Column(name = "`extra_address`", columnDefinition = "TEXT")
    private String extraAddress;

    @Column(name = "`courier_company`")
    private String courierCompany;

    @Column(name = "`invoice_number`")
    private String invoiceNumber;

    @Column(name = "`delivery_date`")
    private Instant deliveryDate;

    @Column(name = "`arrive_date`")
    private Instant arriveDate;
}
