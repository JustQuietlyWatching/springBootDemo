package space.anwenchu.service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by an_wch on 2018/4/4.
 */
@Slf4j
public class UpStorageType implements StorageType  {
    @Override
    public void handleStorage() {
        log.info("=================上传==================");
    }
}
