package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorResponse {

    private String code;
    private String name;

    public static AuthorResponse entityToResponse(AuthorEntity entity) {
        return AuthorResponse.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .build();
    }

}
