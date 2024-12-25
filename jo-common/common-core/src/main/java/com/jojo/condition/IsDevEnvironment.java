package com.jojo.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class IsDevEnvironment implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean flag = true;
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String activeProfile:activeProfiles) {
            if (("pro").equals(activeProfile)){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
