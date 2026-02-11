package auca.ac.rw.demo.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(1L, "Clean code", "Robert Martin", "978-1234", 2008));
        books.add(new Book(2L, "Effective Java", "Joshua Bloch", "978-2341", 2018));
        books.add(new Book(3L, "Spring in Action", "Craig Walls", "978-3412", 2018));
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }
    
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
    for (Book book : books) {
        if (book.getId() == id) {
                return book;
            }
        }
        return null; 
    }

    @GetMapping("/search")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    @PostMapping
    public Book addBook(@RequestBody Book newBook) {
        books.add(newBook);
        return newBook;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {  
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        
        if (removed) {
            return "Book with ID " + id + " deleted successfully";
        } else {
            return "Book with ID " + id + " not found";
        }
    }





    }
