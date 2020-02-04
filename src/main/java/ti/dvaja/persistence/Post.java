package ti.dvaja.persistence;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drichtar on 2/16/17.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToMany()
    @Column(table = "post_user")
    private Set<User> authors;

    @Column(nullable = false)
    private Date dateCreated = new Date(2017, 01, 20);

    private Date dateModified;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    private Category category;

    @ManyToMany()
    @JoinColumn(table = "post_tag")
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    public Post(){}

    public Post(String title, String content, Set<User> authors, Category category, HashSet<Tag> tags) {
        this.title = title;
        this.content = content;
        this.authors = authors;
        this.category = category;
        this.tags = tags;
        this.dateModified = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Set<User> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<User> authors) {
        this.authors = authors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Transient
    public String getSummary() {
        return this.getContent().substring(0, this.getContent().length() / 2) + "...";
    }
}
