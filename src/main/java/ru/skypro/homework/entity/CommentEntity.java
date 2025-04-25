package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

    @Column(name = "author", nullable = false)
    private Integer author;
    @Column(name = "author_image", nullable = false)
    private String authorImage;
    @Column(name = "author_first_name", nullable = false)
    private String authorFirstName;
    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    private Integer pk;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;


    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private AdEntity ad;
}
