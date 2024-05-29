package com.example.App.service.impl;

import com.example.App.dto.ContactDto;
import com.example.App.entity.Contact;
import com.example.App.entity.Role;
import com.example.App.repository.RoleRepository;
import com.example.App.repository.ContactRepository;
import com.example.App.service.ContactService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ContactServiceImpl(ContactRepository contactRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.contactRepository = contactRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveContact(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getFirstName() + " " + contactDto.getLastName());
        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());

        // Шифрування паролю при інтеграції Spring Security
        contact.setPassword(passwordEncoder.encode(contactDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        contact.setRoles(List.of(role));
        contactRepository.save(contact);
    }

    @Override
    public Contact findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    @Override
    public List<ContactDto> findAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private ContactDto convertEntityToDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        String[] name = contact.getName().split(" ");
        contactDto.setFirstName(name[0]);
        contactDto.setLastName(name[1]);
        contactDto.setPhone(contact.getPhone());
        contactDto.setEmail(contact.getEmail());
        return contactDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
