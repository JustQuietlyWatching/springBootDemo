package channel.anwenchu.service;

import lombok.extern.slf4j.Slf4j;
import channel.anwenchu.service.impl.FtpStorageType;

/**
 * Created by an_wch on 2018/4/4.
 */
@Slf4j
public class UpStorageType {

    public static void main(String[] args) {
        UpStorageType u = new FtpStorageType();
        FtpStorageType f = new FtpStorageType();


        System.out.println(String.valueOf(u.getClass()));
        System.out.println(String.valueOf(FtpStorageType.class));

        System.out.println(String.valueOf(f.getClass()));
    }
}
