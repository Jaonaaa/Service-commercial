package com.commercial.commerce.SocietyService.ResponseStruct;

import java.util.List;

import com.commercial.commerce.SocietyService.Models.RequestSC;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestSCStructResponse {

    List<RequestSC> validates;

    List<RequestSC> invalidates;

}
