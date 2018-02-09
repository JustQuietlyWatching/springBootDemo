package space.anwenchu.formBean;

import lombok.Data;
import lombok.NoArgsConstructor;
import space.anwenchu.bean.AutoBuyTicketModel;

/**
 * Created by an_wch on 2018/2/9.
 */
@Data
@NoArgsConstructor
public class QueryTrainFormBean {
    /**
     * 开车日期
     * */
    private String trainDate;

    /**
     * 出发城市
     * */
    private String fromCity;
    /**
     * 目标城市
     * */
    private String toCity;
}
