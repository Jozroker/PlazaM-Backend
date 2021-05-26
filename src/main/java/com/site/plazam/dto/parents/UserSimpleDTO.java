package com.site.plazam.dto.parents;

import com.site.plazam.domain.Role;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserSimpleDTO {

    private String id;

    @NotNull
    private Role role = Role.USER;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSimpleDTO that = (UserSimpleDTO) o;
        return Objects.equals(id, that.id) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
