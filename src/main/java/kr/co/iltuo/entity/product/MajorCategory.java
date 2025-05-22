package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`major_category`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MajorCategory {

    @Id
    @Column(name = "`major_category_id`", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majorCategoryId;

    @Column(name = "`major_category_name`", nullable = false, length = 45)
    private String majorCategoryName;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean valid  = true;
}
