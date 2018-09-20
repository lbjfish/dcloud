package com.sida.dcloud.training.client;

import com.sida.xiruo.po.common.UserCentricDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrainingProxy {
    @Autowired
    private SystemClient systemClient;

    public <T extends UserCentricDTO> void fillUserNamesByIds(Collection<T> dtoList) {
        //Set可去重
        Set<String> ids = new HashSet<>();
        dtoList.forEach(vo -> {
            if (Optional.ofNullable(vo.getCreatedUser()).isPresent()) {
                ids.add(vo.getCreatedUser());
            }
            if (Optional.ofNullable(vo.getUpdatedUser()).isPresent()) {
                ids.add(vo.getUpdatedUser());
            }
        });

        Map<String, Object> map = (LinkedHashMap) systemClient.findUserNamesByIds(new ArrayList<>(ids));
        dtoList.forEach(vo -> {
            vo.setCreatedUserName(Optional.<String>ofNullable((String) map.get(vo.getCreatedUser())).orElse(""));
            vo.setUpdatedUserName(Optional.<String>ofNullable((String) map.get(vo.getUpdatedUser())).orElse(""));
        });
    }
}
