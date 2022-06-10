package br.com.tqi.bootcamp.bookstore.model;

import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "book")
@NoArgsConstructor
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private Integer price;
    @Column(name = "image_url")
    private String image;

    @ManyToOne(optional = false)
    private AuthorEntity author;

    public BookEntity(final BookRequest request, final String urlImage, final AuthorEntity author) {
        this.code = UUID.randomUUID().toString().toUpperCase();
        this.name = request.getName();
        this.price = Integer.valueOf(request.getPrice());
        this.image = urlImage;
        this.author = author;
    }
}
