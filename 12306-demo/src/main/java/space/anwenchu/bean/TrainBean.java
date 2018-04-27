package space.anwenchu.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/1/30.
 */
@Data
@NoArgsConstructor
public class TrainBean {
    String secretStr;
    String trainNo;
    String gaotie2;

    // 日期
    String trainDate;
    // 出发站
    String startStation;
    // 终点站
    String endStation;
    // 开始时间
    String startTime;
    // 结束时间
    String endTime;
    // 历时
    String longDate;
    // 头等座
    String specialSeat;
    // 一等座
    String oneSeat;
    // 二等座
    String twoSeat;
    // 高级软卧
    String specialSleep;
    // 软卧
    String softSleep;
    // 动卧
    String moveSleep;
    // 硬卧
    String hardSleep;
    // 软座
    String softSeat;
    // 硬座
    String hardSeat;
    // 无座
    String noSeat;
    // 其他
    String other;
    // 是否可以购买 Y:可以  N:不可以
    String isBuy;

}
