package net.essadiki.neuralnetwork.security;

public final class SecurityContext {

    private static final ThreadLocal<Role> currentRole =
            ThreadLocal.withInitial(() -> Role.GUEST);

    private SecurityContext() {
    }

    public static void setRole(Role role) {
        currentRole.set(role);
    }

    public static Role getRole() {
        return currentRole.get();
    }

    public static void logout() {
        currentRole.remove();
    }
}