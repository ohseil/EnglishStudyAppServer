package com.seil.englishstudy.entity;

import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false, length = 100)
    private String question;

    @Column(nullable = false, length = 100)
    private String answer;
}
