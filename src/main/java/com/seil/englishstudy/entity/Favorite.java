package com.seil.englishstudy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorites")
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "eng_study_data_id")
    private EngStudyData engStudyData;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        Favorite data = (Favorite) obj;
        return Objects.equals(this.user.getId(), data.user.getId())
                && Objects.equals(this.engStudyData.getId(), data.engStudyData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), engStudyData.getId());
    }

}
