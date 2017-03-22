package ti.dvaja.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

/**
 * Created by drichtar on 2/16/17.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    /**
    @ManyToMany
    @NotNull
    private User author; **/


    @NotNull
    private GregorianCalendar date = new GregorianCalendar();

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
}
