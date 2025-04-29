package com.libManager.membermanagementservice.repository;

import com.libManager.membermanagementservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByContactNumber(String contactNumber);
    // Add more custom query methods as needed
}