package logistika.finders;

import logistika.domain.Customer;

public class CustomerPrinter {
private static final CustomerPrinter INSTANCE = new CustomerPrinter();
    
    public static CustomerPrinter getInstance() { return INSTANCE; }
    
    private CustomerPrinter() { }
        
    public void print(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("customer cannot be null");
        }
        
        System.out.print("id :          ");
        System.out.println(customer.getId());
        System.out.print("first name:   ");
        System.out.println(customer.getFirstName());
        System.out.print("last name:    ");
        System.out.println(customer.getLastName());
        System.out.print("birth number: ");
        System.out.println(customer.getDateOfBirth());
        System.out.print("address:      ");
        System.out.println(customer.getAddressId());
        System.out.println();
    }
}
