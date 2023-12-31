package com.aprendearabe.backend.app.services;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aprendearabe.backend.app.models.entities.Country;
import com.aprendearabe.backend.app.models.repositories.ICountryRepository;
import com.aprendearabe.backend.app.utils.Utils;

@Service
public class CountryService implements ICrudService<Country> {
	@Autowired
	private ICountryRepository countryRepository;
	private List<String> countriesValid = new ArrayList<>(List.of("Spain","Andorra","France","Italy","Portugal","Germany"));
	
	@Override
	@Transactional(readOnly = true)
	public List<Country> getAll() {
		return countryRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Country getById(Long id) {
		return countryRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Country save(Country country) {
		return countryRepository.save(country);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		countryRepository.deleteById(id);
	}
	
	/* API FLAGSAPI */
	public List<Country> getAllCountriesApi() throws IOException{
		List<Country> countries = new ArrayList<>();
		try {
			Document webHtml = Jsoup.connect("https://flagsapi.com/").get();
			Elements paisesHtml = webHtml.getElementsByClass("item_country cell small-4 medium-2 large-2");
			for (Element element : paisesHtml) {
				String name = element.select("p").get(1).text();
				if(isCountryValid(name)) {
					String code = element.getElementsByClass("mb0 bold").text();
					String flag = element.getElementsByClass("theme-flat").attr("data-pagespeed-lazy-src")
							.replace("64", "32");
					if (!name.isEmpty()) {
						flag = !flag.isEmpty() ? flag : String.format("https://flagsapi.com/%s/flat/32.png", code);
						byte[] image = Utils.convertImg(flag);
						if (image != null) {
							Country country = new Country(name,code,image);
							countries.add(country);
						}
					}
				}
			}
		}catch(Exception e) {
			countries = null;
		}
		return countries;
	}
	
	public boolean isCountryValid(String country) {
		if (countriesValid.contains(country)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	public Boolean addAllCountriesApi() throws IOException {
		List<Country> countries = getAllCountriesApi();
		if (countries!=null) {
			countryRepository.saveAll(countries);
			return true;
		}
		else {
			return false;
		}
	}
}
