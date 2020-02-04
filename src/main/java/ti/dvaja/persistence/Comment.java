package ti.dvaja.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String content;

    @Column(nullable = false)
    private Date createDate = new Date(2017, 01, 20);

    @ManyToOne
    @JoinColumn(nullable = false, name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    public Comment(){}

    public Comment(@NotEmpty String content, @NotNull Date createDate, Post post, @NotNull User user) {
        this.content = content;
        this.createDate = createDate;
        this.post = post;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
