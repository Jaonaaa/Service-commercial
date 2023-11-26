package com.commercial.commerce.SocietyService.RequestStruct;

import java.util.List;

import com.commercial.commerce.SocietyService.Models.RequestSCDetails;
import com.commercial.commerce.SocietyService.Models.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestSCStruct {

    Service service_commercial;// setted aty anaty metier
    Service service_sender;
    String date;
    Boolean validate;
    List<RequestSCDetails> details;
}
