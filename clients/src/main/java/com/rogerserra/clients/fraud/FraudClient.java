package com.rogerserra.clients.fraud;

import com.rogerserra.clients.fraud.response.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// control + p -> nos chiva que puede ir dentro de los parentesis como parametro
// @RequestMapping("appi/v1/fraud-check") - mirar el controlador del microservicio fraud, es la ruta base
/*
OPCION 1
@FeignClient(
        value = "fraud",
        path = "api/v1/fraud-check"
)
*/
// esta interfaz apunta a fraudcontroller del microservicio fraud
@FeignClient("fraud")
public interface FraudClient {
    @GetMapping(path = "api/v1/fraud-check/{customerId}")
    FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId
    );
}
