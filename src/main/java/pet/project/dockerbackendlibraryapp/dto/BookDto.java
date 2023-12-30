package pet.project.dockerbackendlibraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private Integer year;
    private String author;
}
