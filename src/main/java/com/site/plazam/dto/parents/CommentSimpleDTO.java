package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentSimpleDTO {

    private String id;

    @NotNull
    private String text;

    private boolean reported;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentSimpleDTO that = (CommentSimpleDTO) o;
        return reported == that.reported &&
                Objects.equals(id, that.id) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, reported);
    }
}
