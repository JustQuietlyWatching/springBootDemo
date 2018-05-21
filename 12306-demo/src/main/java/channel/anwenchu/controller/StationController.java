package channel.anwenchu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import channel.anwenchu.response.GenericResponse;
import channel.anwenchu.service.StationService;
import channel.anwenchu.utils.StationCodeUtil;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by an_wch on 2018/2/8.
 */
@RestController
@Slf4j
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/list")
    public Callable<GenericResponse<List<StationCodeUtil.StationCode>>>  getStationList(){
        return () -> GenericResponse.success(stationService.getStationList());
    }
}
