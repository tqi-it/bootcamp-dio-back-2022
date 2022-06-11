package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.BookController;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerValidationTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Shor", "The name of this book contains more than one hundred characters xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"})
    void shouldReturnBadRequestWhenBookNameHasInvalidValue(String name) throws Exception {
        BookRequest request = successBookRequest();
        request.setName(name);

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookNameIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setName(null);

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "@", "100."})
    void shouldReturnBadRequestWhenBookPriceHasInvalidValue(String price) throws Exception {
        BookRequest request = successBookRequest();
        request.setPrice(price);

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookPriceIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setPrice(null);

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookPriceIsLessThan100Cents() throws Exception {
        BookRequest request = successBookRequest();
        request.setPrice("99");

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookPriceIsGreaterThan10000000Cents() throws Exception {
        BookRequest request = successBookRequest();
        request.setPrice("10000001");

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void shouldReturnBadRequestWhenBookAuthorCodeHasInvalidValue(String authorCode) throws Exception {
        BookRequest request = successBookRequest();
        request.setAuthor_code(authorCode);

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookAuthorCodeIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setAuthor_code(null);

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name(request.getName())
                .price(request.getPrice())
                .author("Author name")
                .image("urlImage").build();

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", request.getName())
                        .param("price", request.getPrice())
                        .param("author_code", request.getAuthor_code()))
                .andExpect(status().isBadRequest());
    }

    private BookRequest successBookRequest() {
        return BookRequest.builder()
                .name("Book Name")
                .author_code("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .price("100")
                .file(Utils.createMockedFile())
                .build();
    }

}
