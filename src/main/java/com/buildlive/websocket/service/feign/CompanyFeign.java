package com.buildlive.websocket.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyFeign {

        @GetMapping("/api/v1/company/{companyId}/get-partyMember-Id/{email}")
        String getPartyMemberId(@PathVariable("email") String email, @PathVariable UUID companyId);
}
