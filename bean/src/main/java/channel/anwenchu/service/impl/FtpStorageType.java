package channel.anwenchu.service.impl;

import org.springframework.stereotype.Service;
import channel.anwenchu.service.UpStorageType;

/**
 * Created by an_wch on 2018/4/4.
 */
@Service("ftpStorageType")
public class FtpStorageType extends UpStorageType {

    public void handleStorage() {

        System.out.println("-----ftp---storageType-----");

    }
}
