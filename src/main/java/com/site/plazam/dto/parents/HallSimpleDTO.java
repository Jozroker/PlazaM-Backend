package com.site.plazam.dto.parents;

import com.site.plazam.domain.Technology;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallSimpleDTO {

    private String id;

    @NotNull
    private Technology technology;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallSimpleDTO that = (HallSimpleDTO) o;
        return Objects.equals(id, that.id) &&
                technology == that.technology;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, technology);
    }
}
