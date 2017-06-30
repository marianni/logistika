package logistika.finders;

import logistika.domain.Country;

public class CountryPrinter {
private static final CountryPrinter INSTANCE = new CountryPrinter();
    
    public static CountryPrinter getInstance() { return INSTANCE; }
    
    private CountryPrinter() { }
        
    public void print(Country country) {
        if (country == null) {
            throw new NullPointerException("country cannot be null");
        }
        
        System.out.print("id :          ");
        System.out.println(country.getId());
        System.out.print("Country name:   ");
        System.out.println(country.getName());
        System.out.print("Shortcut of the country:    ");
        System.out.println(country.getShortcutOfCountry());
        System.out.print("Continent: ");
        System.out.println(country.getContinent());
        System.out.println();
    }
}

