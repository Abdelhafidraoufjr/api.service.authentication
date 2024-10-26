package api.service.auth.controller;

import java.util.List;
import java.util.Optional;

import api.service.auth.repository.UserRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.service.auth.entity.LoginAttempt;
import api.service.auth.entity.Permission;
import api.service.auth.entity.Role;
import api.service.auth.entity.Session;
import api.service.auth.entity.User;
import api.service.auth.service.LoginAttemptService;
import api.service.auth.service.PermissionService;
import api.service.auth.service.RoleService;
import api.service.auth.service.SessionService;
import api.service.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
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
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Authentication API is up and running");
}


    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @ApiResponse(responseCode = "200", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Operation(summary = "Update user", description = "Updates the details of an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
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

    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des rôles (Role)
    @Operation(summary = "Create a new role", description = "Creates a new role in the system")
    @ApiResponse(responseCode = "200", description = "Role created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.saveRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    @ApiResponse(responseCode = "200", description = "List of roles retrieved successfully")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Update role", description = "Updates the details of an existing role")
    @ApiResponse(responseCode = "200", description = "Role updated successfully")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @PutMapping("/roles/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
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

    @Operation(summary = "Delete role", description = "Deletes a role by their ID")
    @ApiResponse(responseCode = "204", description = "Role deleted successfully")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des permissions (Permission)
    @Operation(summary = "Create a new permission", description = "Creates a new permission in the system")
    @ApiResponse(responseCode = "200", description = "Permission created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.savePermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    @Operation(summary = "Get all permissions", description = "Retrieves a list of all permissions")
    @ApiResponse(responseCode = "200", description = "List of permissions retrieved successfully")
    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.findAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @Operation(summary = "Update permission", description = "Updates the details of an existing permission")
    @ApiResponse(responseCode = "200", description = "Permission updated successfully")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @PutMapping("/permissions/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permissionDetails) {
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

    @Operation(summary = "Delete permission", description = "Deletes a permission by their ID")
    @ApiResponse(responseCode = "204", description = "Permission deleted successfully")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des tentatives de connexion (LoginAttempt)
    @Operation(summary = "Create a login attempt", description = "Creates a new login attempt record")
    @ApiResponse(responseCode = "200", description = "Login attempt created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/login-attempts")
    public ResponseEntity<LoginAttempt> createLoginAttempt(@RequestBody LoginAttempt loginAttempt) {
        LoginAttempt createdLoginAttempt = loginAttemptService.saveLoginAttempt(loginAttempt);
        return ResponseEntity.ok(createdLoginAttempt);
    }

    @Operation(summary = "Get all login attempts", description = "Retrieves a list of all login attempts")
    @ApiResponse(responseCode = "200", description = "List of login attempts retrieved successfully")
    @GetMapping("/login-attempts")
    public ResponseEntity<List<LoginAttempt>> getAllLoginAttempts() {
        List<LoginAttempt> loginAttempts = loginAttemptService.findAllLoginAttempts();
        return ResponseEntity.ok(loginAttempts);
    }

    @Operation(summary = "Delete login attempt", description = "Deletes a login attempt byz their ID")
    @ApiResponse(responseCode = "204", description = "Login attempt deleted successfully")
    @ApiResponse(responseCode = "404", description = "Login attempt not found")
    @DeleteMapping("/login-attempts/{id}")
    public ResponseEntity<Void> deleteLoginAttempt(@PathVariable Long id) {
        loginAttemptService.deleteLoginAttempt(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des sessions (Session)
    @Operation(summary = "Create a new session", description = "Creates a new session record")
    @ApiResponse(responseCode = "200", description = "Session created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/sessions")
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session createdSession = sessionService.saveSession(session);
        return ResponseEntity.ok(createdSession);
    }

    @Operation(summary = "Get all sessions", description = "Retrieves a list of all sessions")
    @ApiResponse(responseCode = "200", description = "List of sessions retrieved successfully")
    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.findAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @Operation(summary = "Delete session", description = "Deletes a session by their ID")
    @ApiResponse(responseCode = "204", description = "Session deleted successfully")
    @ApiResponse(responseCode = "404", description = "Session not found")
    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
