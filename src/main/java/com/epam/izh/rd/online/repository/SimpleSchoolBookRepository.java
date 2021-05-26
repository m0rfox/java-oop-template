package com.epam.izh.rd.online.repository;


import com.epam.izh.rd.online.entity.SchoolBook;

public class SimpleSchoolBookRepository implements BookRepository<SchoolBook>{
    SchoolBook[] schoolBooks = new SchoolBook[0];
    /**
     * Метод должен сохранять школьную книгу в массив schoolBooks.
     * Массив при каждом сохранении должен увеличиваться в размере ровно на 1.
     * То есть он ровно того размера, сколько сущностей мы в него сохранили.
     * <p>
     * Одну и ту же книгу МОЖНО сохранить в массиве несколько раз, проверки на то, что такая книга уже сохранена, делать не нужно.
     * <p>
     * Если сохранение прошло успешно, метод должен вернуть true.
     *
     * @param book
     */
    @Override
    public boolean save(SchoolBook book) {


        if (schoolBooks.length == 0){
            schoolBooks = new SchoolBook[1];
            schoolBooks[0] = book;
        }else {
            SchoolBook[] hold = new SchoolBook[schoolBooks.length + 1];
            System.arraycopy(schoolBooks, 0, hold, 0, schoolBooks.length);
            schoolBooks = new SchoolBook[hold.length];
            System.arraycopy(hold, 0, schoolBooks, 0, hold.length);
            schoolBooks[schoolBooks.length - 1] = book;
        }
        return true;
    }

    /**
     * Метод должен находить в массиве schoolBooks все книги по имени.
     * <p>
     * Если книги найдены - метод должен их вернуть.
     * Если найденных по имени книг нет, должен вернуться пустой массив.
     *
     * @param name
     */
    @Override
    public SchoolBook[] findByName(String name) {

        SchoolBook[] arrayToCopy = new SchoolBook[0];
        for (int i = 0; i < schoolBooks.length; i++) {
            if (name.equals(schoolBooks[i].getName())) {
                if (arrayToCopy.length == 0) {
                    arrayToCopy = new SchoolBook[1];
                    arrayToCopy[0] = schoolBooks[i];
                } else {
                    SchoolBook[] hold = new SchoolBook[arrayToCopy.length + 1];
                    System.arraycopy(arrayToCopy, 0, hold, 0, arrayToCopy.length);
                    arrayToCopy = new SchoolBook[hold.length];
                    System.arraycopy(hold, 0, arrayToCopy, 0, hold.length);
                    arrayToCopy[arrayToCopy.length - 1] = schoolBooks[i];
                }
            }
        }

        return arrayToCopy;
    }



    /**
     * Метод должен удалять книги из массива schoolBooks по названию.
     * Если книг с одинаковым названием в массиве несколько, метод должен удалить их все.
     * <p>
     * Важно: при удалении книги из массива размер массива должен уменьшиться!
     * То есть, если мы сохранили 2 разные книги и вызвали count() (метод ниже), то он должен вернуть 2.
     * Если после этого мы удалили 1 книгу, метод count() должен вернуть 1.
     * <p>
     * Если хотя бы одна книга была найдена и удалена, метод должен вернуть true, в противном случае,
     * если книга не была найдена, метод должен вернуть false.
     *
     * @param name
     */
    @Override
    public boolean removeByName(String name) {
        boolean found = false;
        for (int i=0, l=0; i < schoolBooks.length; i++){
            if (name.equals(schoolBooks[i].getName()) ){
                schoolBooks[i] = null;
                l++;
                for (int j = 0; j < schoolBooks.length; j++ ){
                    if (schoolBooks[j] == null){
                        for (int k = j + 1; k < schoolBooks.length; k++){
                            schoolBooks[k - 1] = schoolBooks[k];
                            schoolBooks[schoolBooks.length - 1] = null;


                            break;
                        }
                    }
                    SchoolBook [] hold = new SchoolBook[schoolBooks.length - 1];
                    System.arraycopy(schoolBooks, 0, hold, 0, hold.length);
                    schoolBooks = new SchoolBook[hold.length-l];
                    System.arraycopy(hold, 0, schoolBooks, 0, schoolBooks.length);
                } found = true;
            }
        } return found;
    }


    /**
     * Метод возвращает количество сохраненных сущностей в массиве schoolBooks.
     */
    @Override
    public int count() {
        return schoolBooks.length;
    }
}
