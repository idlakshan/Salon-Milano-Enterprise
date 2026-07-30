package com.milano.repo;

import com.milano.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepo extends JpaRepository<Notification, UUID> {


}
