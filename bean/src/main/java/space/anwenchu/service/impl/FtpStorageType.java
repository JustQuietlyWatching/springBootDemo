package space.anwenchu.service.impl;

import org.springframework.stereotype.Service;
import space.anwenchu.service.StorageType;
import space.anwenchu.service.UpStorageType;

/**
 * Created by an_wch on 2018/4/4.
 */
@Service("ftpStorageType")
public class FtpStorageType extends UpStorageType {

    @Override
    public void handleStorage() {

        System.out.println("-----ftp---storageType-----");

    }
}
