package com.bookmanagement.controller;

import com.bookmanagement.entity.Rental;
import com.bookmanagement.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    
    @Autowired
    private RentalService rentalService;
    
    @PostMapping("/borrow")
    public ResponseEntity<Rental> borrowBook(@RequestParam Long userId, 
                                           @RequestParam Long bookId, 
                                           @RequestParam(defaultValue = "14") int days) {
        Rental rental = rentalService.borrowBook(userId, bookId, days);
        return rental != null ? ResponseEntity.ok(rental) : ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/return/{rentalId}")
    public ResponseEntity<Rental> returnBook(@PathVariable Long rentalId) {
        Rental rental = rentalService.returnBook(rentalId);
        return rental != null ? ResponseEntity.ok(rental) : ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/user/{userId}")
    public List<Rental> getUserActiveRentals(@PathVariable Long userId) {
        return rentalService.getUserActiveRentals(userId);
    }
}