package logistika.finders;

import java.sql.SQLException;
import java.util.List;

import logistika.domain.Address;
import logistika.domain.Country;
import logistika.manager.EntityService;

public class CountryService {
	
	public Country selectByCountryCode(String countryCode) throws InstantiationException, IllegalAccessException, SQLException{
		if (countryCode == null){
			return null;
		}		
		List<Country> countries = EntityService.em.searchQuery(Country.SELECT_BY_COUNTRY_CODE, Country.class, countryCode);
		if (countries == null || countries.isEmpty()){
			return null;
		}
		return countries.get(0);
	}
	
	
}
