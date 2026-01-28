package com.example.waterdelivery.service.eventlistener;

import com.example.waterdelivery.model.Role;
import com.example.waterdelivery.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@AllArgsConstructor
public class SpringContextStartedListener {

    private final AuthService authService;

    /**
     * This listener checks once after spring context created(app started)
     * if there are exist any user in app. If no creates default.
     */
    @EventListener({ContextRefreshedEvent.class})
    public void onContextStarted() {
        if (authService.existsAnyUser()) {
            return;
        }

        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_ADMIN);
        roles.add(Role.ROLE_USER);


        try {
            authService.registerUser("admin", "admin", roles);
        } catch (Exception e) {
            log.error("can not create default user:", e);
        }
    }
}
