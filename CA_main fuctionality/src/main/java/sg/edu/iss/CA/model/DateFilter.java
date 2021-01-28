package sg.edu.iss.CA.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DateFilter {

@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
private LocalDateTime startDate;

@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
private LocalDateTime endDate;
}
