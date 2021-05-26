package com.site.plazam.dto;

import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class MovieForHomeSliderDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO widePicture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieForHomeSliderDTO that = (MovieForHomeSliderDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(widePicture, that.widePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, widePicture);
    }
}
