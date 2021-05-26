package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Author;
import com.epam.izh.rd.online.entity.Book;
import com.epam.izh.rd.online.entity.SchoolBook;
import com.epam.izh.rd.online.repository.BookRepository;
import com.epam.izh.rd.online.repository.SimpleAuthorRepository;
import com.epam.izh.rd.online.repository.SimpleSchoolBookRepository;

import java.time.LocalDate;

public class SimpleSchoolBookService implements BookService{
    BookRepository<SchoolBook> schoolBookBookRepository = new SimpleSchoolBookRepository();
    AuthorService authorService = new SimpleAuthorService();

    public SimpleSchoolBookService() {
    }

    public SimpleSchoolBookService(BookRepository<SchoolBook> schoolBookBookRepository, AuthorService authorService) {
        this.schoolBookBookRepository = schoolBookBookRepository;
        this.authorService = authorService;
    }

    /**
     * Метод должен сохранять книгу.
     * <p>
     * Перед сохранением книги нужно проверить, сохранен ли такой автор в базе авторов.
     * То есть вы должен взять имя и фамилию автора из книги и обратиться к сервису авторов и узнать о наличии такого автора.
     * Напомню, что мы считаем, что двух авторов с одинаковыми именем и фамилией быть не может.
     * <p>
     * Если такой автор существует (сохранен) - значит можно сохранять и книгу.
     * Если же такого автора в базе нет, значит книгу сохранять нельзя.
     * <p>
     * Соответственно, если книга была успешно сохранена - метод возвращает true, если же книга не была сохранена - метод возвращает false.
     *
     * @param book
     */
    @Override
    public boolean save(Book book) {
        SchoolBook schoolBook = (SchoolBook) book;
        if (authorService.findByFullName(((SchoolBook) book).getAuthorName(), schoolBook.getAuthorLastName()) == null) return false;
        else return schoolBookBookRepository.save((SchoolBook) book);
    }




    /**
     * Метод должен находить книгу по имени.
     * <p>
     * По факту, он просто обращается к репозиторию с книгами и вызывает аналогичный метод, после чего возвращает результат.
     *
     * @param name
     */
    @Override
    public Book[] findByName(String name) {
        return schoolBookBookRepository.findByName(name);
    }

    /**
     * Метод должен находить количество сохраненных книг по конкретному имени книги.
     *
     * @param name
     */
    @Override
    public int getNumberOfBooksByName(String name) {
        int numbersOfBooks = 0;
        if (schoolBookBookRepository.findByName(name) != null){
            return schoolBookBookRepository.findByName(name).length;
        } else return 0;
    }

    /**
     * Метод должен удалять все книги по имени.
     * <p>
     * По факту, он просто обращается к репозиторию с книгами и вызывает аналогичный метод, после чего возвращает результат.
     *
     * @param name
     */
    @Override
    public boolean removeByName(String name) {
        return schoolBookBookRepository.removeByName(name);
    }

    /**
     * Метод должен возвращать количество всех книг.
     * <p>
     * По факту, он просто обращается к репозиторию с книгами и вызывает аналогичный метод, после чего возвращает результат.
     */
    @Override
    public int count() {

        return schoolBookBookRepository.count();
    }

    /**
     * Метод должен возвращать автора книги по названию книги.
     * <p>
     * То есть придется сходить и в репозиторий с книгами и в сервис авторов.
     * <p>
     * Если такой книги не найдено, метод должен вернуть null.
     *
     * @param name
     */
    @Override
    public Author findAuthorByBookName(String name) {
        SchoolBook[] foundAuthorByName =  schoolBookBookRepository.findByName(name);
        Author foundAuthor = authorService.findByFullName(foundAuthorByName[0].getAuthorName(), foundAuthorByName[0].getAuthorLastName());
        return foundAuthor;
    }
}
