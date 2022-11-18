package com.example.eventservice.model.entity.entityfactory;

import com.example.eventservice.model.dto.AddressDto;
import com.example.eventservice.model.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityFactory implements EntityFactory<Address, AddressDto> {
    @Override
    public Address buildEntityFromDto(AddressDto dto) {
        return new Address(dto.getAddressCity(), dto.getAddressStreet(), dto.getAddressHouseNumber());
    }
}