package channel.anwenchu.demo.reposrity;

import channel.anwenchu.demo.domain.DemoDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by an_wch on 2018/5/3.
 */
@Repository
public interface DemoRepository extends JpaRepository<DemoDomain, Long> {

    List<DemoDomain> findByUserName(String userName);

}
