package com.rm.springboot.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull
    @Size(min = 5, max = 1000)
    private String title;

    @NotNull
    @Size(max = 6000)
    private String text;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Comments> comments;

    public News(long l, LocalDateTime now, String titleNews, String textNews) {
        this.id = l;
        this.date = now;
        this.title = titleNews;
        this.text = textNews;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public List<Comments> getComments() {
        return this.comments;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                '}';
    }
}
