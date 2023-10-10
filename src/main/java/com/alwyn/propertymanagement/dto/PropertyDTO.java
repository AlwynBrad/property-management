package com.alwyn.propertymanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDTO {

    private long id;
    private String title;
    private String description;
    private  String ownerName;
    private  String ownerEmail;
    private  Double price;
    private  String address;
}
