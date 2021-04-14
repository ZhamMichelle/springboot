package com.learning.springboot.devicedetection;

import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceDetectionController {

    @RequestMapping("/detect-device")
    public @ResponseBody String detectDevice(Device device){  //аннотация говорит Spring MVC писать полученный объект в тело ответа, вместо того, чтобы отображать модель в представлении
        String deviceType="unknown";
        if(device.isMobile()){
            deviceType="mobile";
        }else if(device.isNormal()){
            deviceType="normal";
        } else if (device.isTablet()) {
            deviceType="tablet";
        }
            return "device type is " + deviceType;
    }
}

