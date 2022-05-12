package com.seil.englishstudy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "englishstudy_data")
public class EnglishStudyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long categorycode;

    @Column(nullable = false, length = 500)
    private String question;

    @Column(nullable = false, length = 500)
    private String answer;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        EnglishStudyData data = (EnglishStudyData) obj;
        return Objects.equals(this.id, data.id)
                && Objects.equals(this.categorycode, data.categorycode)
                && Objects.equals(this.question, data.question)
                && Objects.equals(this.answer, data.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categorycode, question, answer);
    }
}
