package com.alwyn.propertymanagement.controller;

import com.alwyn.propertymanagement.dto.CalculatorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorController {
    @GetMapping("/add")
    public Double add(@RequestParam("num1")Double n1, @RequestParam("num2")  Double n2){
        return n1 + n2;
    }

    @GetMapping("/sub/{num1}/{num2}")
    public Double subtract(@PathVariable("num1") Double num1, @PathVariable("num2") Double num2){
        if (num1>num2){
            return num1-num2;
        }
        else {
            return  num2-num1;
        }
    }

    @PostMapping("/mul")
    public  ResponseEntity<Double> mul(@RequestBody CalculatorDTO calculatorDto){
        Double ans = calculatorDto.getNum1()*calculatorDto.getNum2()*calculatorDto.getNum3();
        ResponseEntity<Double> responseEntity = new ResponseEntity<Double>(ans, HttpStatus.CREATED);
        return responseEntity;
    }
}
