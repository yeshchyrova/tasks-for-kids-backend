//package com.github.yeshchyrova.taskstracker.security;
//
//import com.google.common.collect.Sets;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import static com.github.yeshchyrova.taskstracker.security.UserPermissions.*;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//@Getter
//public enum UserRole {
//  PARENT(Sets.newHashSet(TASKS_READ, TASKS_WRITE, MEMBER_WRITE)),
//  CHILD(Sets.newHashSet(TASKS_READ, TASKS_COMPLETE, MEMBER_WRITE));
//
//
////  permissions будет разным для каждого типа в enum (PARENT, CHILD)
////  то есть в коде мы вызываем UserRole.PARENT.getPermissions и получаем сет из
////  TASKS_READ, TASKS_WRITE, MEMBER_WRITE
//
//  //  also, all declared fields should be final to prevent updating a value inside enum constants
//  public final Set<UserPermissions> permissions;
//
//  //  этот метод тоже будет вызван только на одном из типов enum, например
//  //  UserRole.CHILD.getGrantedAuthorities()
//  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
//    Set<SimpleGrantedAuthority> permissionsSet =
//            getPermissions().stream()
//                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                    .collect(Collectors.toSet());
//    permissionsSet.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//    return permissionsSet;
//  }
//}
