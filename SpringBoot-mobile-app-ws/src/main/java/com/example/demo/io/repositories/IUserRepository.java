package com.example.demo.io.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.io.entity.UserEntity;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<UserEntity, Long> 
{
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
}
