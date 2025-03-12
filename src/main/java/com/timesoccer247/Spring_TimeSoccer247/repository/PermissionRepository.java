package com.timesoccer247.Spring_TimeSoccer247.repository;

import com.timesoccer247.Spring_TimeSoccer247.entity.Permission;
import com.timesoccer247.Spring_TimeSoccer247.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    boolean existsByName(String name);

    Set<Permission> findAllByNameIn(Set<String> names);

    Set<Permission> findAllByIdIn(List<Long> ids);

    Set<Permission> findAllByRolesIn(List<Role> roleList);
}
