package com.site.plazam.dto.comparator;

import com.site.plazam.dto.UserForUsersListDTO;

import java.util.Comparator;

public class UsersByRealNameComparator implements Comparator<UserForUsersListDTO> {

    @Override
    public int compare(UserForUsersListDTO user1, UserForUsersListDTO user2) {
        if (user1.getFirstName().equals(user2.getFirstName())) {
            return user1.getLastName().compareToIgnoreCase(user2.getLastName());
        }
        return user1.getFirstName().compareToIgnoreCase(user2.getFirstName());
    }
}
