package space.anwenchu.formBean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/1/30.
 */
@Data
@NoArgsConstructor
public class LoginFormBean {
    private String username;
    private String password;
    private String code;
}
