package com.alwyn.propertymanagement.security;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alwyn.propertymanagement.entity.UserEntity;
import com.alwyn.propertymanagement.enums.Role;
import com.alwyn.propertymanagement.repository.UserRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ApplicationConfig.class})
@ExtendWith(SpringExtension.class)
class ApplicationConfigTest {
    @Autowired
    private ApplicationConfig applicationConfig;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link ApplicationConfig#passwordEncoder()}
     */
    @Test
    void testPasswordEncoder() {
        assertTrue(applicationConfig.passwordEncoder() instanceof BCryptPasswordEncoder);
    }

    /**
     * Method under test: {@link ApplicationConfig#userDetailsService()}
     */
    @Test
    void testUserDetailsService() throws UsernameNotFoundException {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setOwnerEmail("jane.doe@example.org");
        userEntity.setOwnerName("Owner Name");
        userEntity.setPassword("iloveyou");
        userEntity.setPhone("6625550144");
        userEntity.setRole(Role.USER);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = (new ApplicationConfig(userRepository)).userDetailsService()
                .loadUserByUsername("janedoe");
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
        assertSame(userEntity, actualLoadUserByUsernameResult);
    }

    /**
     * Method under test: {@link ApplicationConfig#userDetailsService()}
     */
    @Test
    void testUserDetailsServiceWithEmptyResult() throws UsernameNotFoundException {

        UserRepository userRepository = mock(UserRepository.class);
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findByOwnerEmail(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class,
                () -> (new ApplicationConfig(userRepository)).userDetailsService().loadUserByUsername("janedoe"));
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ApplicationConfig#userDetailsService()}
     */
    @Test
    void testUserDetailsServiceWithUsernameNotFoundException() throws UsernameNotFoundException {

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByOwnerEmail(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("User Not Found"));
        assertThrows(UsernameNotFoundException.class,
                () -> (new ApplicationConfig(userRepository)).userDetailsService().loadUserByUsername("janedoe"));
        verify(userRepository).findByOwnerEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ApplicationConfig#authenticationProvider()}
     */
    @Test
    void testAuthenticationProvider() {
        assertTrue(applicationConfig.authenticationProvider() instanceof DaoAuthenticationProvider);
    }

    /**
     * Method under test: {@link ApplicationConfig#authenticationManager(AuthenticationConfiguration)}
     */
    @Test
    void testAuthenticationManager() throws Exception {
        assertTrue(applicationConfig.authenticationManager(new AuthenticationConfiguration()) instanceof ProviderManager);
    }
}

