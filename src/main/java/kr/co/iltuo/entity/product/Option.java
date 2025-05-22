package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`option`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`option_id`", nullable = false)
    private Long optionId;

    @Column(name = "`major_category_id`", nullable = false)
    private Long majorCategoryId;

    @Column(name = "`priority_index`", nullable = false)
    private Long priorityIndex;

    @Column(name = "`option_name`", nullable = false, length = 45)
    private String optionName;

    @Column(name = "`option_type_code`", nullable = false, length = 6)
    private String optionTypeCode;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean valid = true;
}