package com.site.plazam.dto;

import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class UserForLoginDTO extends UserSimpleDTO {

    @NotNull
    private String emailOrUsername;

    @NotNull
    private String loginPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForLoginDTO that = (UserForLoginDTO) o;
        return Objects.equals(emailOrUsername, that.emailOrUsername) &&
                Objects.equals(loginPassword, that.loginPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), emailOrUsername, loginPassword);
    }
}
