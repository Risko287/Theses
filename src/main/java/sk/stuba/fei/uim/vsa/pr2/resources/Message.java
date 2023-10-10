package sk.stuba.fei.uim.vsa.pr2.resources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer code;
    private String message;
    private Error error;
}
