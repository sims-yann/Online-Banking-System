package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
<<<<<<< Updated upstream
public interface UserRepo {
=======
public interface UserRepo extends JpaRepository<User, Integer> {
    User findById(int i);
    List<User> findAll();
>>>>>>> Stashed changes
}
