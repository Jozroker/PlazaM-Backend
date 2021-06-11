package com.site.plazam.repository;

import com.site.plazam.domain.Country;
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

    List<User> findByHomeCountry(Country country);

    Page<User> findByHomeCountry(Country country, Pageable pageable);

    Optional<User> findFirstByUsernameOrEmail(String username, String email);

    Optional<User> findByPhone(String phone);

    List<User> findByBannedTrue();

    Page<User> findByBannedTrue(Pageable pageable);

    List<User> findByBannedToDate(LocalDate date);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name': {$regex: ?0, $options: 'i'}}," +
            "{'last_name': {$regex: ?1, $options: 'i'}}," +
            "{'first_name': {$regex: ?1, $options: 'i'}}," +
            "{'last_name': {$regex: ?0, $options: 'i'}}," +
            "{'username': {$regex: ?2}}" +
            "]" +
            "}")
    List<User> findByFirstNameOrLastNameOrUsername(String firstName,
                                                   String lastName,
                                                   String username);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name': {$regex: ?0, $options: 'i'}}," +
            "{'last_name': {$regex: ?1, $options: 'i'}}," +
            "{'first_name': {$regex: ?1, $options: 'i'}}," +
            "{'last_name': {$regex: ?0, $options: 'i'}}," +
            "{'username': {$regex: ?2}}" +
            "]" +
            "}")
    Page<User> findByFirstNameOrLastNameOrUsername(String firstName,
                                                   String lastName,
                                                   String username,
                                                   Pageable pageable);

    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);

    List<User> findByFavouriteMovieIdsContains(String id);

    List<User> findByViewedMovieIdsContains(String id);

    List<User> findByWaitMovieIdsContains(String id);

    Optional<User> findByTicketIdsContains(String id);

//    @Query(value = "{'_id':ObjectId('?0')},{'_id':0, 'favourite_movies_ids':1}")
//    List<String> getFavouritesMoviesByUserId(String id);
}