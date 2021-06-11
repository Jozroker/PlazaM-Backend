package com.site.plazam.dto.comparator;

import com.site.plazam.dto.CommentForReportedListDTO;
import com.site.plazam.dto.UserForBannedListDTO;
import com.site.plazam.dto.UserForUsersListDTO;

import java.util.Comparator;

public class UsersByUsernameComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1.getClass().getSimpleName().equals("UserForUsersListDTO")) {
            UserForUsersListDTO user1 = (UserForUsersListDTO) o1;
            UserForUsersListDTO user2 = (UserForUsersListDTO) o2;
            return user1.getUsername().compareToIgnoreCase(user2.getUsername());
        } else if (o1.getClass().getSimpleName().equals("CommentForReportedListDTO")) {
            CommentForReportedListDTO user1 = (CommentForReportedListDTO) o1;
            CommentForReportedListDTO user2 = (CommentForReportedListDTO) o2;
            return user1.getUser().getUsername().compareToIgnoreCase(user2.getUser().getUsername());
        } else if (o1.getClass().getSimpleName().equals("UserForBannedListDTO")) {
            UserForBannedListDTO user1 = (UserForBannedListDTO) o1;
            UserForBannedListDTO user2 = (UserForBannedListDTO) o2;
            return user1.getUsername().compareToIgnoreCase(user2.getUsername());
        }
        return 0;
    }
}
