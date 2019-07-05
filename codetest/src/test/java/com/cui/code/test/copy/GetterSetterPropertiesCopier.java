package com.cui.code.test.copy;

import com.cui.code.test.model.Address;
import com.cui.code.test.model.Contact;
import com.cui.code.test.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * get set 拷贝属性
 *
 * @author cuiswing
 * @date 2019-07-05
 */
public class GetterSetterPropertiesCopier implements PropertiesCopier {

    @Override
    public void copyProperties(Object source, Object target) {
        Customer customer = (Customer) source;

        Customer.CustomerBuilder customerBuilder = Customer.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .age(customer.getAge());

        List<Contact> contactDetails = customer.getContactDetails();
        if (contactDetails != null) {
            List<Contact> targetContactDetails = new ArrayList<>();
            for (Contact contactDetail : contactDetails) {
                Contact contact = Contact.builder().number(contactDetail.getNumber())
                        .type(contactDetail.getType())
                        .build();
                targetContactDetails.add(contact);
            }
            customerBuilder.contactDetails(targetContactDetails);
        }

        Address homeAddress = customer.getHomeAddress();
        if (homeAddress != null) {
            Address address = Address.builder().city(homeAddress.getCity())
                    .line(homeAddress.getLine())
                    .state(homeAddress.getState())
                    .zip(homeAddress.getZip())
                    .build();
            customerBuilder.homeAddress(address);
        }

        target = customerBuilder.build();
    }
}
