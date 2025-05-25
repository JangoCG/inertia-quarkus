package com.gurtus.inertia.runtime;

import java.util.Map;

import io.vertx.ext.web.Session;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Manages Inertia-specific session data like errors and history clearing.
 */
@ApplicationScoped
public class InertiaSession {

    private static final String INERTIA_ERRORS_KEY = "inertia_errors";
    private static final String INERTIA_CLEAR_HISTORY_KEY = "inertia_clear_history";

    /**
     * Store validation errors in the session for the next request.
     */
    public void setErrors(Session session, Map<String, Object> errors) {
        if (session != null && errors != null && !errors.isEmpty()) {
            session.put(INERTIA_ERRORS_KEY, errors);
        }
    }

    /**
     * Get validation errors from the session and remove them.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getAndClearErrors(Session session) {
        if (session == null) {
            return null;
        }
        
        Map<String, Object> errors = session.get(INERTIA_ERRORS_KEY);
        session.remove(INERTIA_ERRORS_KEY);
        return errors;
    }

    /**
     * Check if there are validation errors in the session.
     */
    public boolean hasErrors(Session session) {
        return session != null && session.get(INERTIA_ERRORS_KEY) != null;
    }

    /**
     * Set flag to clear history on the next request.
     */
    public void setClearHistory(Session session, boolean clearHistory) {
        if (session != null) {
            if (clearHistory) {
                session.put(INERTIA_CLEAR_HISTORY_KEY, true);
            } else {
                session.remove(INERTIA_CLEAR_HISTORY_KEY);
            }
        }
    }

    /**
     * Get and clear the clear history flag.
     */
    public boolean getAndClearClearHistory(Session session) {
        if (session == null) {
            return false;
        }
        
        Boolean clearHistory = session.get(INERTIA_CLEAR_HISTORY_KEY);
        session.remove(INERTIA_CLEAR_HISTORY_KEY);
        return clearHistory != null && clearHistory;
    }

    /**
     * Clean up all Inertia-specific session data.
     */
    public void cleanup(Session session) {
        if (session != null) {
            session.remove(INERTIA_ERRORS_KEY);
            session.remove(INERTIA_CLEAR_HISTORY_KEY);
        }
    }

    /**
     * Check if any Inertia session data exists.
     */
    public boolean hasInertiaData(Session session) {
        return session != null && 
               (session.get(INERTIA_ERRORS_KEY) != null || 
                session.get(INERTIA_CLEAR_HISTORY_KEY) != null);
    }
} 