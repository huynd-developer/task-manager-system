package org.example.springcustomauthwithdb.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springcustomauthwithdb.Entity.User;
import org.example.springcustomauthwithdb.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserDetailManager implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElse(null);
        if (user != null) {
            String[] roles = user.getUserRoles().stream()
                    .map(ur -> ur.getRole().getId().substring(5))
                    .toList()
                    .toArray(new String[0]);
            return org.springframework.security.core.userdetails.User.withUsername(username)
                    .password(user.getPassword())
                    .roles(roles)
                    .build();
        }
        throw new UsernameNotFoundException(username);
    }
}

