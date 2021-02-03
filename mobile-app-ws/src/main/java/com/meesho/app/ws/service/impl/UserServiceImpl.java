package com.meesho.app.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meesho.app.ws.UserRepository;
import com.meesho.app.ws.io.entity.UserEntity;
import com.meesho.app.ws.service.UserService;
import com.meesho.app.ws.shared.Utils;
import com.meesho.app.ws.shared.dto.UserDto;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@Override
	public UserDto createUser(UserDto user) {
		
		
		if(userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("Record Already exists");
		
		
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		
		String publicUserId = utils.generateUserId(30);
		
		
		
		userEntity.setEncryptedPassword("test");
		userEntity.setUserId(publicUserId);
		
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		return returnValue;
	}

}
