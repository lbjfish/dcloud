package com.sida.dcloud.content.client;

import com.sida.xiruo.po.common.UserCentricDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContentProxy {
    @Autowired
    private SystemClient systemClient;

    public <T extends UserCentricDTO> void fillUserNamesByIds(Collection<T> dtoList) {
        //Set可去重
        Set<String> ids = new HashSet<>();
        dtoList.forEach(dto -> {
            if (Optional.ofNullable(dto.getCreatedUser()).isPresent()) {
                ids.add(dto.getCreatedUser());
            }
            if (Optional.ofNullable(dto.getUpdatedUser()).isPresent()) {
                ids.add(dto.getUpdatedUser());
            }
        });

        Map<String, Object> map = (LinkedHashMap) systemClient.findUserNamesByIds(new ArrayList<>(ids));
        dtoList.forEach(dto -> {
            dto.setCreatedUserName(Optional.<String>ofNullable((String) map.get(dto.getCreatedUser())).orElse(""));
            dto.setUpdatedUserName(Optional.<String>ofNullable((String) map.get(dto.getUpdatedUser())).orElse(""));
        });
    }
}
