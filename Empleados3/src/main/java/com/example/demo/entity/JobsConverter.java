package com.example.demo.entity;

import java.util.Optional;
import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JobsConverter implements AttributeConverter<Feina, String> {
	
	
	    @Override
	    public String convertToDatabaseColumn(Feina jobs) {
	        // TODO Auto-generated method stub
	        return Optional.ofNullable(jobs).map(Feina::getCarrec).orElse(null);
	    }


	    @Override
	    public Feina convertToEntityAttribute(String dbData) {

	        return Arrays.stream(Feina.values()).filter(targetEnum -> targetEnum.getCarrec().equalsIgnoreCase(dbData)).findFirst().orElse(null);
	    }
	}


