package com.site.plazam.repository;

import com.site.plazam.domain.Role;
import com.site.plazam.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByRole(Role role);

    Page<User> findByRole(Role role, Pageable pageable);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query(value = "{'banned':true}")
    List<User> findBannedUsers();

    @Query(value = "{'banned':true}")
    Page<User> findBannedUsers(Pageable pageable);

    List<User> findByBannedToDate(LocalDate date);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.en':/?0/}," +
            "{'first_name.ua':/?0/}," +
            "{'first_name.pl':/?0/}," +
            "{'last_name.en':/?1/}," +
            "{'last_name.ua':/?1/}," +
            "{'last_name.pl':/?1/}" +
            "]" +
            "}")
    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.en':/?0/}," +
            "{'first_name.ua':/?0/}," +
            "{'first_name.pl':/?0/}," +
            "{'last_name.en':/?1/}," +
            "{'last_name.ua':/?1/}," +
            "{'last_name.pl':/?1/}" +
            "]" +
            "}")
    Page<User> findByFirstNameOrLastName(String firstName, String lastName,
                                         Pageable pageable);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.en':/?0/}," +
            "{'first_name.ua':/?0/}," +
            "{'first_name.pl':/?0/}," +
            "{'last_name.en':/?1/}," +
            "{'last_name.ua':/?1/}," +
            "{'last_name.pl':/?1/}," +
            "{'email':/?2/}" +
            "]" +
            "}")
    List<User> findByFirstNameOrLastNameOrUsername(String firstName,
                                                   String lastName, String email);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.en':/?0/}," +
            "{'first_name.ua':/?0/}," +
            "{'first_name.pl':/?0/}," +
            "{'last_name.en':/?1/}," +
            "{'last_name.ua':/?1/}," +
            "{'last_name.pl':/?1/}," +
            "{'email':/?2/}" +
            "]" +
            "}")
    Page<User> findByFirstNameOrLastNameOrUsername(String firstName,
                                                   String lastName,
                                                   String email, Pageable pageable);

    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);
}