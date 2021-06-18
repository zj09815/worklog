package com.uwntek.worklog.dto.base;


import org.springframework.lang.NonNull;

import static com.uwntek.worklog.util.BeanUtils.updateProperties;

public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {
    @SuppressWarnings("unchecked")
    @NonNull
    default <T extends DTO> T convertFrom(@NonNull DOMAIN domain) {

        updateProperties(domain, this);

        return (T) this;
    }

}
