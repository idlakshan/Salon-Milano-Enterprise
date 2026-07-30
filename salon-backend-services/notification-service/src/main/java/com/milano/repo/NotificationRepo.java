package com.milano.repo;

import com.milano.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface NotificationRepo extends JpaRepository<Notification, UUID> {

    List<Notification> findByUserId(UUID userId);
    List<Notification> findBySalonId(UUID salonId);



}
