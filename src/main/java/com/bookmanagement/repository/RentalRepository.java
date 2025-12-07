package com.bookmanagement.repository;

import com.bookmanagement.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserIdAndReturnDateIsNull(Long userId);
    Optional<Rental> findByBookIdAndReturnDateIsNull(Long bookId);
}