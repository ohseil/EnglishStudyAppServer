package com.seil.englishstudy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "eng_study_datas",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames={"category_code", "question", "answer"}
                )
        })
@Entity
public class EngStudyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_code", nullable = false)
    private long categoryCode;

    @Column(name = "question", nullable = false, length = 380)
    private String question;

    @Column(name = "answer", nullable = false, length = 380)
    private String answer;

    @OneToMany(mappedBy = "engStudyData", orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private Set<Favorite> favoriteList = new HashSet<>();

}
