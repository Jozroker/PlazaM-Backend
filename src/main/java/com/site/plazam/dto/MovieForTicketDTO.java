package com.site.plazam.dto;

import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class MovieForTicketDTO extends MovieForHomeSliderDTO {

    @NotNull
    private PictureDTO posterPicture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieForTicketDTO that = (MovieForTicketDTO) o;
        return Objects.equals(posterPicture, that.posterPicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), posterPicture);
    }
}
