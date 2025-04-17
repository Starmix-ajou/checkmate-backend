package com.starmix.checkmate.infrastructure.config;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class SpringEnv {
    private Environment environment;

    public Boolean isProdProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(EnvStep.PROD.getDisplayName());
    }

    public Boolean isDevProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(EnvStep.DEV.getDisplayName());
    }

    public Boolean isLocalProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(EnvStep.LOCAL.getDisplayName());
    }

    public String getProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        return activeProfiles[0];
    }
}
