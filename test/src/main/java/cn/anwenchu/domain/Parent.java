package cn.anwenchu.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by an_wch on 2018/6/13.
 */
@Data
@Component
public abstract class Parent {
    @Value("${sharedVal}")
    private String sharedVal;



}
