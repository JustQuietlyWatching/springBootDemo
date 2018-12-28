package cn.anwenchu.domain;

import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/6/13.
 */
@NoArgsConstructor
public class ChildA extends Parent {
    String a1;

    public ChildA (String a1) {
        super();
        this.a1 = a1;
    }


}
