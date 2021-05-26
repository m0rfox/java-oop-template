package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.Author;
import com.epam.izh.rd.online.entity.SchoolBook;

import java.time.LocalDate;
import java.util.Arrays;

public class SimpleAuthorRepository implements AuthorRepository{
    Author[] authors = new Author[0];

    /**
     * Метод должен сохранять автора в массив authors.
     * Массив при каждом сохранении должен увеличиваться в размере ровно на 1.
     * То есть он ровно того размера, сколько сущностей мы в него сохранили.
     * <p>
     * Если на вход для сохранения приходит автор, который уже есть в массиве (сохранен), то автор не сохраняется и
     * метод возвращает false.
     * <p>
     * Можно сравнивать только по полному имени (имя и фамилия), считаем, что двух авторов
     * с одинаковыми именем и фамилией быть не может.
     * Подсказка - можно использовать для проверки метод findByFullName.
     * <p>
     * Если сохранение прошло успешно, метод должен вернуть true.
     *
     * @param author
     */
    @Override
    public boolean save(Author author) {
        boolean faund = false;
        if (authors.length == 0){
            authors = new Author[1];
            authors[0] = author;
            faund = true;
        }else {
            for (int i=0; i < authors.length; i++) {
                if (author.getName() == authors[i].getName() && author.getLastName() == authors[i].getLastName()) {
                    faund = false;
                } else {
                    Author[] hold = new Author[authors.length + 1];
                    System.arraycopy(authors, 0, hold, 0, authors.length);
                    authors = new Author[hold.length];
                    System.arraycopy(hold, 0, authors, 0, authors.length);
                    authors[authors.length - 1] = author;
                    faund = true;
                }
            }
        } if (faund == true){ return true;
        }else  return false;
    }

    /**
     * Метод должен находить в массиве authors автора по имени и фамилии (считаем, что двух авторов
     * с одинаковыми именем и фамилией быть не может.)
     * <p>
     * Если автор с таким именем и фамилией найден - возвращаем его, если же не найден, метод должен вернуть null.
     *
     * @param name
     * @param lastname
     */
    @Override
    public Author findByFullName(String name, String lastname) {
        Author currentAuthor = null;
        for (int i = 0; i < authors.length; i++){
            if (name == authors[i].getName() && lastname == authors[i].getLastName()){
                currentAuthor = authors[i];
            }
        }
        if (currentAuthor != null){
            return currentAuthor;
        }else return null;
    }

    /**
     * Метод должен удалять автора из массива authors, если он там имеется.
     * Автора опять же, можно определять только по совпадению имении и фамилии.
     * <p>
     * Важно: при удалении автора из массива размер массива должен уменьшиться!
     * То есть, если мы сохранили 2 авторов и вызвали count() (метод ниже), то он должен вернуть 2.
     * Если после этого мы удалили 1 автора, метод count() должен вернуть 1.
     * <p>
     * Если автор был найден и удален, метод должен вернуть true, в противном случае, если автор не был найден, метод
     * должен вернуть false.
     *
     * @param author
     */
    @Override
    public boolean remove(Author author) {
        boolean found = false;
        int countOfFound = 0;
        Author[] arrayToRemove = new Author[authors.length];
        int[] arrayToIndex = new int[authors.length];
        for (int i = 0, j = 0; i < authors.length; i++){
            if (author.equals(authors[i])){
                countOfFound++;
                arrayToRemove[j] = authors[i];
                arrayToIndex[j] = i;
                j++;
                found = true;
            }
        }
        for (int i = 0, j = 0; i < authors.length; i++){
            if (i == arrayToIndex[j]){
                j++;
                authors[i] = null;
            }
        }
        for (int j = 0; j < authors.length; j++ ){
            if (authors[j] == null){
                for (int k = j + 1; k < authors.length; k++){
                    authors[k - 1] = authors[k];
                    authors[authors.length - 1] = null;


                    break;
                }
            }
            Author [] hold = new Author[authors.length];
            System.arraycopy(authors, 0, hold, 0, hold.length);
            authors = new Author[hold.length-countOfFound];
            System.arraycopy(hold, 0, authors, 0, authors.length);
        }

        return found;
    }

    /**
     * Метод возвращает количество сохраненных сущностей в массиве authors.
     */
    @Override
    public int count() {
        return authors.length;
    }


}

