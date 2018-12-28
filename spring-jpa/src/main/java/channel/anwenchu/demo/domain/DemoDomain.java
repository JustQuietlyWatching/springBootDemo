package channel.anwenchu.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by an_wch on 2018/5/3.
 */
@Entity
@Data
@NoArgsConstructor
public class DemoDomain{

    @Id
    @GenericGenerator(name="sid", strategy="assigned")
    private Long id;

    @Column
    private String userName;
}
