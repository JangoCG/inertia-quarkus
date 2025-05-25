package com.gurtus.inertia.runtime;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Action filter for conditional Inertia shared data.
 * Based on Rails InertiaRails::ActionFilter.
 */
public class InertiaActionFilter {
    
    public enum ConditionalKey {
        ONLY, EXCEPT
    }
    
    private final ConditionalKey conditionalKey;
    private final Set<String> actions;
    
    public InertiaActionFilter(ConditionalKey conditionalKey, String... actions) {
        this.conditionalKey = conditionalKey;
        this.actions = new HashSet<>(Arrays.asList(actions));
    }
    
    public InertiaActionFilter(ConditionalKey conditionalKey, Set<String> actions) {
        this.conditionalKey = conditionalKey;
        this.actions = new HashSet<>(actions);
    }
    
    /**
     * Check if this filter matches the current action.
     */
    public boolean match(String currentAction) {
        if (currentAction == null) {
            return false;
        }
        
        switch (conditionalKey) {
            case ONLY:
                return actions.contains(currentAction);
            case EXCEPT:
                return !actions.contains(currentAction);
            default:
                return false;
        }
    }
    
    public ConditionalKey getConditionalKey() {
        return conditionalKey;
    }
    
    public Set<String> getActions() {
        return new HashSet<>(actions);
    }
    
    @Override
    public String toString() {
        return "InertiaActionFilter{" +
                "conditionalKey=" + conditionalKey +
                ", actions=" + actions +
                '}';
    }
} 