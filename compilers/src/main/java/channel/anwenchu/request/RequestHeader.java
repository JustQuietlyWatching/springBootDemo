package channel.anwenchu.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/5/4.
 */
@Data
@NoArgsConstructor
public class RequestHeader {
    private String version = "1.0.0";
    private String msgType = "01";
    private String reqDate;

    public String toXML() {
        return null;
    }
}
