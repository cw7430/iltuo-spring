package kr.co.iltuo.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`miner_category`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MinerCategory {

    @Id
    @Column(name = "`miner_category_id`", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long minerCategoryId;

    @Column(name = "`major_category_id`", nullable = false)
    private Long majorCategoryId;

    @Column(name = "`miner_category_name`", nullable = false, length = 45)
    private String minerCategoryName;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean valid = true;
}
