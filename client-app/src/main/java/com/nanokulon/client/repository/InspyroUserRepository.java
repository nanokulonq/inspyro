package com.nanokulon.client.repository;

import com.nanokulon.client.entity.InspyroUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InspyroUserRepository extends CrudRepository<InspyroUser, Integer> {

    Optional<InspyroUser> findByUsername(String username);
}
