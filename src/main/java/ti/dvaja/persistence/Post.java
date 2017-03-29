package ti.dvaja.persistence;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Created by drichtar on 2/16/17.
 */
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToMany(mappedBy = "posts")
    @Column(nullable = false)
    private Set<User> authors;

    @Column(nullable = false)
    private GregorianCalendar date = new GregorianCalendar();

    public Post(){}

    public Post(String title, String content, Set<User> authors) {
        this.title = title;
        this.content = content;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    @Transient
    public String getSummary() {
        return this.getContent().substring(0, this.getContent().length() / 2) + "...";
    }
}
