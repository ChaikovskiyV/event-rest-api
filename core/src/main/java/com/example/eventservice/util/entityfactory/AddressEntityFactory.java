package com.example.eventservice.util.entityfactory;

import com.example.eventservice.dto.AddressDto;
import com.example.eventservice.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityFactory implements EntityFactory<Address, AddressDto> {
    @Override
    public Address buildEntityFromDto(AddressDto dto) {
        return new Address(dto.getAddressCity(), dto.getAddressStreet(), dto.getAddressHouseNumber());
    }
}