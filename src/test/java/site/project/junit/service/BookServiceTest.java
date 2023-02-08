package site.project.junit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.project.junit.domain.Book;
import site.project.junit.domain.BookRepository;
import site.project.junit.util.MailSender;
import site.project.junit.web.dto.BookResponseDto;
import site.project.junit.web.dto.BookSaveRequestDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    void 등록() throws Exception {
        //given
        BookSaveRequestDto requestDto = new BookSaveRequestDto();
        requestDto.setTitle("책이름");
        requestDto.setAuthor("책제목");
        when(bookRepository.save(any())).thenReturn(requestDto.toEntity());
        when(mailSender.send()).thenReturn(true);
        //when
        BookResponseDto bookResponseDto = bookService.createBook(requestDto);
        //then
        assertThat(bookResponseDto.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(requestDto.getAuthor());
    }

    @Test
    void 목록() throws Exception {
        //given

        //when
        List<Book> list = new ArrayList<>();
        list.add(new Book(1L, "책이름1", "책저자1"));
        list.add(new Book(2L, "책이름2", "책저자2"));

        when(bookRepository.findAll()).thenReturn(list);
        List<BookResponseDto> bookResponseDtos = bookService.bookList();
        bookResponseDtos.stream().forEach(bookResponseDto -> {
            System.out.println(bookResponseDto.getId());
            System.out.println(bookResponseDto.getTitle());

        });

        //then
        assertThat(bookResponseDtos.get(0).getTitle()).isEqualTo("책이름1");
        assertThat(bookResponseDtos.get(1).getTitle()).isEqualTo("책이름2");

    }
}
