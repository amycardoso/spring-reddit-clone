package com.clone.reddit.service;

import static com.clone.reddit.util.Constants.ACTIVATION_EMAIL;
import static java.time.Instant.now;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.reddit.dto.AuthenticationResponse;
import com.clone.reddit.dto.LoginRequest;
import com.clone.reddit.dto.RegisterRequest;
import com.clone.reddit.exception.SpringRedditException;
import com.clone.reddit.models.NotificationEmail;
import com.clone.reddit.models.User;
import com.clone.reddit.models.VerificationToken;
import com.clone.reddit.repository.UserRepository;
import com.clone.reddit.repository.VerificationTokenRepository;
import com.clone.reddit.security.JwtProvider;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtProvider jwtProvider;
	@Autowired
    private MailContentBuilder mailContentBuilder;
	@Autowired
    private MailService mailService;
	@Autowired
    private VerificationTokenRepository verificationTokenRepository;
    
	@Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);
 
        userRepository.save(user);
        log.info("User Registered Successfully, Sending Authentication Email");
        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);
 
        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
    }
 
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }
 
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
 
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }
 
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token")));
    }
 
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}