package api.service.auth.controller;

import java.util.List;
import java.util.Optional;

import api.service.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

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

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.saveRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

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

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.savePermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.findAllPermissions();
        return ResponseEntity.ok(permissions);
    }

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

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login-attempts")
    public ResponseEntity<LoginAttempt> createLoginAttempt(@RequestBody LoginAttempt loginAttempt) {
        LoginAttempt createdLoginAttempt = loginAttemptService.saveLoginAttempt(loginAttempt);
        return ResponseEntity.ok(createdLoginAttempt);
    }

    @GetMapping("/login-attempts")
    public ResponseEntity<List<LoginAttempt>> getAllLoginAttempts() {
        List<LoginAttempt> loginAttempts = loginAttemptService.findAllLoginAttempts();
        return ResponseEntity.ok(loginAttempts);
    }

    @DeleteMapping("/login-attempts/{id}")
    public ResponseEntity<Void> deleteLoginAttempt(@PathVariable Long id) {
        loginAttemptService.deleteLoginAttempt(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sessions")
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session createdSession = sessionService.saveSession(session);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.findAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
