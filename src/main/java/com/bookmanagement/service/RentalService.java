package com.bookmanagement.service;

import com.bookmanagement.entity.Book;
import com.bookmanagement.entity.Rental;
import com.bookmanagement.entity.User;
import com.bookmanagement.repository.BookRepository;
import com.bookmanagement.repository.RentalRepository;
import com.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Rental borrowBook(Long userId, Long bookId, int daysToReturn) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);
        
        if (user.isPresent() && book.isPresent() && book.get().isAvailable()) {
            Book bookEntity = book.get();
            bookEntity.setAvailable(false);
            bookRepository.save(bookEntity);
            
            Rental rental = new Rental(user.get(), bookEntity, LocalDate.now().plusDays(daysToReturn));
            return rentalRepository.save(rental);
        }
        return null;
    }
    
    public Rental returnBook(Long rentalId) {
        Optional<Rental> rental = rentalRepository.findById(rentalId);
        if (rental.isPresent() && rental.get().getReturnDate() == null) {
            Rental rentalEntity = rental.get();
            rentalEntity.setReturnDate(LocalDate.now());
            
            Book book = rentalEntity.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
            
            return rentalRepository.save(rentalEntity);
        }
        return null;
    }
    
    public List<Rental> getUserActiveRentals(Long userId) {
        return rentalRepository.findByUserIdAndReturnDateIsNull(userId);
    }
}