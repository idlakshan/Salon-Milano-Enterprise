package com.milano.repo;

import com.milano.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Set;
import java.util.UUID;

@EnableJpaRepositories
public interface OfferingRepo extends JpaRepository<Offering, UUID> {

    Set<Offering> findBySalonId(UUID id);

}
