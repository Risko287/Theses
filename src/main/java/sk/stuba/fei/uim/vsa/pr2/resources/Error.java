package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String type;
    private String trace;
}