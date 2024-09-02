package kr.co.mrlee.story.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.co.mrlee.story.common.UserAuthority;

@Converter
public class UserAuthorityConverter implements AttributeConverter<UserAuthority, Integer>{

	@Override
	public Integer convertToDatabaseColumn(UserAuthority userAuth) {
		return userAuth.getCode();
	}

	@Override
	public UserAuthority convertToEntityAttribute(Integer dbData) {
		return UserAuthority.ofCode(dbData);
	}
	
}
