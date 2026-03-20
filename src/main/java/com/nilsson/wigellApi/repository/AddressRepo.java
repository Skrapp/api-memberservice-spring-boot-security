package com.nilsson.wigellApi.repository;

import com.nilsson.wigellApi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findAddressByStreetAndPostalCodeAndCity(String street, String postalCode, String city);
}
