package com.natha.dev.Dao;

import com.natha.dev.Model.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPrivilegeDao extends JpaRepository<UserPrivilege, Long> {
    List<UserPrivilege> findByUserName(String userName);
    List<UserPrivilege> findByUserNameAndComponentName(String userName, String componentName);
    boolean existsByUserNameAndComponentNameAndElementNameAndPrivilegeName(
        String userName, String componentName, String elementName, String privilegeName);
    
    void deleteByUserNameAndComponentNameAndElementNameAndPrivilegeName(
        String userName, String componentName, String elementName, String privilegeName);
        
    @Query("SELECT DISTINCT up.privilegeName FROM UserPrivilege up WHERE up.userName = :userName AND up.componentName = :componentName")
    List<String> findPrivilegeNamesByUserNameAndComponentName(
        @Param("userName") String userName, 
        @Param("componentName") String componentName);
        
    List<UserPrivilege> findByUserNameAndPrivilegeName(String userName, String privilegeName);
    
    @Query("SELECT COUNT(up) > 0 FROM UserPrivilege up WHERE up.userName = :userName AND up.componentName = :componentName AND up.privilegeName = :privilegeName")
    boolean existsByUserNameAndComponentNameAndPrivilegeName(
        @Param("userName") String userName, 
        @Param("componentName") String componentName, 
        @Param("privilegeName") String privilegeName);
        
    // Jwenn tout privilèj yon itilizatè genyen sou yon konpozan
    @Query("SELECT up.privilegeName FROM UserPrivilege up WHERE up.userName = :userName AND up.componentName = :componentName")
    List<String> findPrivilegesForUserAndComponent(
        @Param("userName") String userName,
        @Param("componentName") String componentName);
        
    // Jwenn tout konpozan kote yon itilizatè gen privilèj
    @Query("SELECT DISTINCT up.componentName FROM UserPrivilege up WHERE up.userName = :userName")
    List<String> findComponentsForUser(@Param("userName") String userName);
    
    // Efase tout privilèj yon itilizatè genyen sou yon konpozan
    void deleteByUserNameAndComponentName(String userName, String componentName);
}
