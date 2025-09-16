package com.kafkaproj.kafkaa.util;

import com.kafkaproj.kafkaa.dto.CorporateActionDistributionDto;
import com.kafkaproj.kafkaa.entity.CorporateActionDistribution;

public class MapperUtil {

    public static CorporateActionDistributionDto toDto(CorporateActionDistribution e) {
        if (e == null) return null;
        CorporateActionDistributionDto d = new CorporateActionDistributionDto();
        d.setId(e.getId());
        d.setTransactionId(e.getTransactionId());
        d.setAccountId(e.getAccountId());
        d.setAmount(e.getAmount());
        d.setCurrency(e.getCurrency());
        d.setEffectiveDate(e.getEffectiveDate());
        d.setMetadata(e.getMetadata());
        return d;
    }
}


