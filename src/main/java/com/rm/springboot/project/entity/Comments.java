package com.rm.springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull
    @Size(max = 1000)
    private String text;

    @NotNull
    @Size(min = 3, max = 128)
    @Pattern(regexp = "^[A-z]*$")
    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @JsonBackReference
    private News news;

    public Comments(Long id, LocalDateTime date, String text, String userName) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
