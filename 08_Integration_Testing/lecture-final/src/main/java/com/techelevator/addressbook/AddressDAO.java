package com.techelevator.addressbook;

import java.util.List;

public interface AddressDAO {

    List<Address> getByContactId(long contactId);
    void add(Address address, long contactId);
    void update(Address address);
    void delete(long addressId);

}
