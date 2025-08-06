package com.natha.dev.IService;

import com.natha.dev.Model.Privilege;
import com.natha.dev.Model.UserApplicationPrivilege;
import com.natha.dev.Model.UserPrivilege;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PrivilegeIService {
    
    // Kreye nouvo privilèj
    Privilege createNewPrivilege(Privilege privilege);

    // Ranmase tout privilèj ki egziste
    List<Privilege> getAllPrivileges();

    // Efase privilèj pa non li (ID)
    void deletePrivilege(String privilegeName);

    // Tcheke si li egziste, sinon kreye li
    Privilege getOrCreatePrivilege(String name, String description);
    
    // Bay yon privilèj a yon itilizatè pou yon aplikasyon
    UserApplicationPrivilege assignPrivilegeToUser(String userName, String applicationId, String privilegeName);
    
    // Jwenn tout privilèj yon itilizatè genyen pou yon konpozan
    Set<String> getUserPrivilegesForComponent(String userId, String componentName);
    
    // Tcheke si yon itilizatè gen yon privilèj espesifik
    boolean checkUserAccess(String userId, String componentName, String privilegeName);
    
    boolean assignReadPrivilege(String userName, String componentName, String elementName);
    boolean checkReadPrivilege(String userName, String componentName, String elementName);
    List<String> getUserReadableElements(String userName);
    
    // Nouvo metòd pou jere privilèj itilizatè yo
    UserPrivilege assignUserPrivilege(String userName, String componentName, String elementName, String privilegeName);
    void removeUserPrivilege(String userName, String componentName, String elementName, String privilegeName);
    boolean checkUserPrivilege(String userName, String componentName, String elementName, String privilegeName);
    List<UserPrivilege> getUserPrivileges(String userName, String componentName);
}
