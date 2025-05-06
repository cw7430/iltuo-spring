package kr.co.iltuo.entity.util;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`code`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`code_id`", nullable = false)
    private Long codeId;

    @Column(name = "`code_category`", nullable = false, length = 100)
    private String codeCategory;

    @Column(name = "`code_value`", nullable = false, length = 6)
    private String codeValue;

    @Column(name = "`code_comment`", nullable = false, length = 100, unique = true)
    private String codeComment;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false)
    private boolean isValid = true;
}
