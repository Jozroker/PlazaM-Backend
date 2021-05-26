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

//    Optional<User> findByUsername(String username);
//
//    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    //    @Query(value = "{'banned':true}")
    List<User> findByBannedTrue();

    //    @Query(value = "{'banned':true}")
    Page<User> findByBannedTrue(Pageable pageable);

    List<User> findByBannedToDate(LocalDate date);

//    @Query(value = "{" +
//            "$or: [" +
//            "{'first_name.en':/?0/i}," +
//            "{'first_name.ua':/?0/i}," +
//            "{'first_name.pl':/?0/i}," +
//            "{'last_name.en':/?1/i}," +
//            "{'last_name.ua':/?1/i}," +
//            "{'last_name.pl':/?1/i}" +
//            "]" +
//            "}")
//    List<User> findByFirstNameOrLastName(String firstName, String lastName);
//
//    @Query(value = "{" +
//            "$or: [" +
//            "{'first_name.en':/?0/i}," +
//            "{'first_name.ua':/?0/i}," +
//            "{'first_name.pl':/?0/i}," +
//            "{'last_name.en':/?1/i}," +
//            "{'last_name.ua':/?1/i}," +
//            "{'last_name.pl':/?1/i}" +
//            "]" +
//            "}")
//    Page<User> findByFirstNameOrLastName(String firstName, String lastName,
//                                         Pageable pageable);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'first_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'first_name.pol': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.eng': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.ukr': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.pol': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.eng': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.ukr': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.pol': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.pol': {$regex: ?0, $options: 'i'}}," +
            "{'username': {$regex: ?2}}" +
            "]" +
            "}")
    List<User> findByFirstNameOrLastNameOrUsername(String firstName,
                                                   String lastName,
                                                   String username);

    @Query(value = "{" +
            "$or: [" +
            "{'first_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'first_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'first_name.pol': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.eng': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.ukr': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.pol': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.eng': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.ukr': {$regex: ?1, $options: 'i'}}," +
            "{'first_name.pol': {$regex: ?1, $options: 'i'}}," +
            "{'last_name.eng': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.ukr': {$regex: ?0, $options: 'i'}}," +
            "{'last_name.pol': {$regex: ?0, $options: 'i'}}," +
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

//    @Query(value = "{'_id':ObjectId('?0')},{'_id':0, 'favourite_movies_ids':1}")
//    List<String> getFavouritesMoviesByUserId(String id);
}