package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {

    @Id
    @Indexed(unique = true)
    private String id;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    private String phone;

    @Field(name = "home_country_id")
    private String homeCountryId;

    @Field(name = "home_city")
    private String homeCity;

    @Field(name = "about_me")
    private String aboutMe;

    private Sex sex;

    @NotNull
    private Role role;

    @Field(name = "ticket_ids")
    private List<String> ticketIds = new ArrayList<>();

    @Field(name = "user_picture_id")
    private String pictureId;

    @Field(name = "selected_language")
    @NotNull
    private Lang selectedLang;

    @Field(name = "selected_cinema_id")
    @NotNull
    private String selectedCinemaId;

    @Field(name = "favourite_movie_ids")
    private List<String> favouriteMovieIds = new ArrayList<>();

    @Field(name = "viewed_movie_ids")
    private List<String> viewedMovieIds = new ArrayList<>();

    @Field(name = "wait_movie_ids")
    private List<String> waitMovieIds = new ArrayList<>();

    @Field(name = "message_ids")
    private List<String> messageIds = new ArrayList<>();

    @NotNull
    @Field(name = "phone_confirmed")
    private Boolean phoneConfirmed = false;

    @NotNull
    @Field(name = "email_confirmed")
    private Boolean emailConfirmed = false;

    @NotNull
    @Field(name = "use_light_theme")
    private Boolean useLightTheme = false;

    @NotNull
    @Field(name = "hide_18_plus_movies")
    private Boolean hide18PlusMovies = false;

    @NotNull
    @Field(name = "use_real_name")
    private Boolean useRealName = false;

    @NotNull
    @Field(name = "banned")
    private Boolean banned = false;

    @Field(name = "banned_to_date")
    private LocalDate bannedToDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add((GrantedAuthority) () -> role.name());
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
