package com.example.room_management.repositories;

import com.example.room_management.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberShipRepository extends JpaRepository<Membership, UUID> {
}
