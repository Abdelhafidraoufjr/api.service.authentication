package api.service.auth.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import api.service.auth.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.service.auth.service.LoginAttemptService;
import api.service.auth.service.PermissionService;
import api.service.auth.service.RoleService;
import api.service.auth.service.SessionService;
import api.service.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "APIs for user authentication, roles, permissions, and sessions management")
public class GlobalAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Check if the authentication API is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Authentication API is up and running");
    }

    @PostMapping("/users")
    @Operation(summary = "Create User", description = "Create a new user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get User by ID", description = "Retrieve user details by user ID")
    public ResponseEntity<User> getUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update User", description = "Update user details by user ID")
    public ResponseEntity<User> updateUser(@Parameter(description = "ID of the user to be updated") @PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userService.findUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setRoles(userDetails.getRoles());
            user.setPermissions(userDetails.getPermissions());
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete User", description = "Delete a user by user ID")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/roles")
    @Operation(summary = "Create Role", description = "Create a new role")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.saveRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/roles")
    @Operation(summary = "Get All Roles", description = "Retrieve a list of all roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/roles/{id}")
    @Operation(summary = "Update Role", description = "Update a role by ID")
    public ResponseEntity<Role> updateRole(@Parameter(description = "ID of the role to be updated") @PathVariable Long id, @RequestBody Role roleDetails) {
        Optional<Role> roleOptional = roleService.findRoleById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            role.setName(roleDetails.getName());
            role.setPermissions(roleDetails.getPermissions());
            Role updatedRole = roleService.saveRole(role);
            return ResponseEntity.ok(updatedRole);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/roles/{id}")
    @Operation(summary = "Delete Role", description = "Delete a role by ID")
    public ResponseEntity<Void> deleteRole(@Parameter(description = "ID of the role to be deleted") @PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/permissions")
    @Operation(summary = "Create Permission", description = "Create a new permission")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.savePermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    @GetMapping("/permissions")
    @Operation(summary = "Get All Permissions", description = "Retrieve a list of all permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.findAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @PutMapping("/permissions/{id}")
    @Operation(summary = "Update Permission", description = "Update a permission by ID")
    public ResponseEntity<Permission> updatePermission(@Parameter(description = "ID of the permission to be updated") @PathVariable Long id, @RequestBody Permission permissionDetails) {
        Optional<Permission> permissionOptional = permissionService.findPermissionById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            permission.setName(permissionDetails.getName());
            permission.setDescription(permissionDetails.getDescription());
            Permission updatedPermission = permissionService.savePermission(permission);
            return ResponseEntity.ok(updatedPermission);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/permissions/{id}")
    @Operation(summary = "Delete Permission", description = "Delete a permission by ID")
    public ResponseEntity<Void> deletePermission(@Parameter(description = "ID of the permission to be deleted") @PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login-attempts")
    @Operation(summary = "Create Login Attempt", description = "Create a new login attempt")
    public ResponseEntity<LoginAttempt> createLoginAttempt(@RequestBody LoginAttempt loginAttempt) {
        LoginAttempt createdLoginAttempt = loginAttemptService.saveLoginAttempt(loginAttempt);
        return ResponseEntity.ok(createdLoginAttempt);
    }

    @GetMapping("/login-attempts")
    @Operation(summary = "Get All Login Attempts", description = "Retrieve a list of all login attempts")
    public ResponseEntity<List<LoginAttempt>> getAllLoginAttempts() {
        List<LoginAttempt> loginAttempts = loginAttemptService.findAllLoginAttempts();
        return ResponseEntity.ok(loginAttempts);
    }

    @DeleteMapping("/login-attempts/{id}")
    @Operation(summary = "Delete Login Attempt", description = "Delete a login attempt by ID")
    public ResponseEntity<Void> deleteLoginAttempt(@Parameter(description = "ID of the login attempt to be deleted") @PathVariable Long id) {
        loginAttemptService.deleteLoginAttempt(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sessions")
    @Operation(summary = "Create Session", description = "Create a new session")
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session createdSession = sessionService.saveSession(session);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping("/sessions")
    @Operation(summary = "Get All Sessions", description = "Retrieve a list of all sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.findAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/sessions/{id}")
    @Operation(summary = "Delete Session", description = "Delete a session by ID")
    public ResponseEntity<Void> deleteSession(@Parameter(description = "ID of the session to be deleted") @PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
