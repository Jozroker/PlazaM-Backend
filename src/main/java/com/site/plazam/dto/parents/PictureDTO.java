package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PictureDTO {

    private String id;

    @ToString.Exclude
    private String pictureString;

    @ToString.Exclude
    private byte[] picture;

    @NotNull
    private String format;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDTO that = (PictureDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pictureString, that.pictureString) &&
                Arrays.equals(picture, that.picture) &&
                Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, pictureString, format);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }
}
