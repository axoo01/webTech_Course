package auca.ac.rw.demo.book;

public class Book {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;

    public Book() {
    }

    public Book(Long id, String title, String author, String isbn, int publicationYear){
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;

    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getIsbn(){
        return isbn;
    }
    public int getPublicationYear(){
        return publicationYear;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

}
