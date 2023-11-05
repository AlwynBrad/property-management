// This code defines a controller class called PropertyController, which handles HTTP requests related to property operations in the application.
package com.alwyn.propertymanagement.controller;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.exception.BusinessException;
import com.alwyn.propertymanagement.service.PropertyService;

import java.util.List;

//import com.alwyn.propertymanagement.service.impl.PropertyServiceImpl; (resolver tells it was never used)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// Define the base request mapping for this controller.
@RequestMapping("/api/v1")
public class PropertyController {

    @Value("${spring.datasource.url:}")
    private String dbURL;

    @Autowired
    private PropertyService propertyService;

     // Define a POST endpoint for saving a new property.
    @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> saveProperty(@RequestBody PropertyDTO propertyDTO) throws BusinessException{
        propertyDTO = propertyService.saveProperty(propertyDTO);
        return new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
    }

    // Define a GET endpoint to retrieve a list of all properties.
    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties(){
        List<PropertyDTO> propertyList= propertyService.getAllProperties();
        return new ResponseEntity<>(propertyList, HttpStatus.OK);

    }

     // Define a GET endpoint to retrieve a list of properties for a specific user.
    @GetMapping("/properties/users/{userId}")
    public ResponseEntity<List<PropertyDTO>> getAllPropertiesForUser(@PathVariable("userId") Long userId){
        List<PropertyDTO> propertyList= propertyService.getAllPropertiesForUser(userId);
        return new ResponseEntity<>(propertyList, HttpStatus.OK);

    }

      // Define a PUT endpoint to update a property's information.
    @PutMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO, @PathVariable Long propertyId){
        propertyDTO = propertyService.updateProperty(propertyDTO, propertyId);
        return new ResponseEntity<>(propertyDTO, HttpStatus.OK);
    }

     // Define a PATCH endpoint to update a property's description.
    @PatchMapping("/properties/update-description/{propertyId}")
    public ResponseEntity<PropertyDTO> updatePropertyDescription(@RequestBody PropertyDTO propertyDTO, @PathVariable Long propertyId){
        propertyDTO = propertyService.updatePropertyDescription(propertyDTO, propertyId);
        return new ResponseEntity<>(propertyDTO, HttpStatus.OK);
    }

     // Define a PATCH endpoint to update a property's price.
    @PatchMapping("/properties/update-price/{propertyId}")
    public ResponseEntity<PropertyDTO> updatePropertyPrice(@RequestBody PropertyDTO propertyDTO, @PathVariable Long propertyId){
        propertyDTO = propertyService.updatePropertyPrice(propertyDTO, propertyId);
        return new ResponseEntity<>(propertyDTO, HttpStatus.OK);
    }

    // Define a DELETE endpoint to delete a property
    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId){
        propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    
}
