package com.libManager.membermanagementservice.service;

import com.libManager.membermanagementservice.model.Member;
import com.libManager.membermanagementservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> getMemberByContactNumber(String contactNumber) {
        return memberRepository.findByContactNumber(contactNumber);
    }

    public Member addMember(Member member) {
        // Check if a member with the given contact number already exists
        Optional<Member> existingMember = memberRepository.findByContactNumber(member.getContactNumber());
        if (existingMember.isPresent()) {
            throw new IllegalArgumentException("Member with contact number " + member.getContactNumber() + " already exists.");
        }
        return memberRepository.save(member);
    }

    public void updateMember(Long id, Member updatedMember) {
        Optional<Member> existingMemberOptional = memberRepository.findById(id);
        existingMemberOptional.ifPresent(existingMember -> {
            // Prevent updating contact number to an existing one (excluding the member being updated)
            if (!updatedMember.getContactNumber().equals(existingMember.getContactNumber())) {
                Optional<Member> memberWithNewContact = memberRepository.findByContactNumber(updatedMember.getContactNumber());
                if (memberWithNewContact.isPresent()) {
                    throw new IllegalArgumentException("Contact number " + updatedMember.getContactNumber() + " is already in use by another member.");
                }
            }
            updatedMember.setId(existingMember.getId());
            memberRepository.save(updatedMember);
        });
        // Consider handling the case where the member doesn't exist
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}