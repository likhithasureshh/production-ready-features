package com.production_ready_features.Post.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditWareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Likitha Suresh");
    }
}
