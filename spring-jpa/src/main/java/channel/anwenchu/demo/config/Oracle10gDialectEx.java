package channel.anwenchu.demo.config;

import org.hibernate.dialect.Oracle10gDialect;
import org.springframework.stereotype.Component;

/**
 * Created by an_wch on 2018/7/19.
 */
@Component
public class Oracle10gDialectEx extends Oracle10gDialect {
    public String getQuerySequencesString() {
        return "select sequence_name from user_sequences";
    }
}
