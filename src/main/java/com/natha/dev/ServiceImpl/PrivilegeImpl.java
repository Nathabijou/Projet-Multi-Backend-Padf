package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PrivilegeDao;
import com.natha.dev.Dao.UserPrivilegeDao;
import com.natha.dev.IService.PrivilegeIService;
import com.natha.dev.Model.Privilege;
import com.natha.dev.Model.UserApplicationPrivilege;
import com.natha.dev.Model.UserPrivilege;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class PrivilegeImpl implements PrivilegeIService {

    @Autowired
    private PrivilegeDao privilegeDao;
    
    @Autowired
    private UserPrivilegeDao userPrivilegeDao;

    // Kreye nouvo privilèj
    public Privilege createNewPrivilege(Privilege privilege) {
        return privilegeDao.save(privilege);
    }

    // Ranmase tout privilèj ki egziste
    public List<Privilege> getAllPrivileges() {
        return privilegeDao.findAll();
    }

    // Efase privilèj pa non li (ID)
    public void deletePrivilege(String privilegeName) {
        privilegeDao.deleteById(privilegeName);
    }

    // Tcheke si li egziste, sinon kreye li
    public Privilege getOrCreatePrivilege(String name, String description) {
        Optional<Privilege> existing = privilegeDao.findById(name);
        if (existing.isPresent()) {
            return existing.get();
        } else {
            Privilege privilege = new Privilege(name, description);
            return privilegeDao.save(privilege);
        }
    }
    
    @Override
    public UserPrivilege assignUserPrivilege(String userName, String componentName, String elementName, String privilegeName) {
        // Tcheke si privilèj la deja egziste
        boolean exists = userPrivilegeDao.existsByUserNameAndComponentNameAndElementNameAndPrivilegeName(
            userName, componentName, elementName, privilegeName);
            
        if (!exists) {
            UserPrivilege userPrivilege = new UserPrivilege(userName, componentName, elementName, privilegeName);
            return userPrivilegeDao.save(userPrivilege);
        }
        
        // Si privilèj la deja egziste, retounen li
        return userPrivilegeDao.findByUserNameAndComponentName(userName, componentName).stream()
            .filter(up -> privilegeName.equals(up.getPrivilegeName()) && 
                         (elementName == null ? up.getElementName() == null : elementName.equals(up.getElementName())))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    @Transactional
    public void removeUserPrivilege(String userName, String componentName, String elementName, String privilegeName) {
        userPrivilegeDao.deleteByUserNameAndComponentNameAndElementNameAndPrivilegeName(
            userName, componentName, elementName, privilegeName);
    }
    
    @Override
    public boolean checkUserPrivilege(String userName, String componentName, String elementName, String privilegeName) {
        return userPrivilegeDao.existsByUserNameAndComponentNameAndElementNameAndPrivilegeName(
            userName, componentName, elementName, privilegeName);
    }
    
    @Override
    public List<UserPrivilege> getUserPrivileges(String userName, String componentName) {
        if (componentName != null && !componentName.isEmpty()) {
            return userPrivilegeDao.findByUserNameAndComponentName(userName, componentName);
        } else {
            return userPrivilegeDao.findByUserName(userName);
        }
    }
    
    @Override
    public UserApplicationPrivilege assignPrivilegeToUser(String userName, String applicationId, String privilegeName) {
        // Implementasyon sa a ta dwe itilize yon UserApplicationPrivilegeDao
        // Men pou kounye a, nou pral retounen null paske li pa nesesè pou aplikasyon an kounye a
        // epi li ta bezwen plis enfòmasyon sou jan pou kreye yon UserApplicationPrivilege
        return null;
    }
    
    @Override
    public Set<String> getUserPrivilegesForComponent(String userId, String componentName) {
        // Retounen yon seri privilèj pou yon itilizatè ak yon konpozan espesifik
        return Set.copyOf(userPrivilegeDao.findPrivilegeNamesByUserNameAndComponentName(userId, componentName));
    }
    
    @Override
    public boolean checkUserAccess(String userId, String componentName, String privilegeName) {
        // Tcheke si yon itilizatè gen aksè nan yon privilèj espesifik pou yon konpozan
        return userPrivilegeDao.existsByUserNameAndComponentNameAndPrivilegeName(
            userId, componentName, privilegeName);
    }
    
    @Override
    public boolean assignReadPrivilege(String userName, String componentName, String elementName) {
        // Bay yon itilizatè privilèj lekti sou yon eleman
        UserPrivilege privilege = assignUserPrivilege(userName, componentName, elementName, "READ");
        return privilege != null;
    }
    
    @Override
    public boolean checkReadPrivilege(String userName, String componentName, String elementName) {
        // Tcheke si yon itilizatè gen privilèj lekti sou yon eleman
        return checkUserPrivilege(userName, componentName, elementName, "READ");
    }
    
    @Override
    public List<String> getUserReadableElements(String userName) {
        // Jwenn tout eleman ki disponib pou lekti pou yon itilizatè
        return userPrivilegeDao.findByUserNameAndPrivilegeName(userName, "READ")
            .stream()
            .map(UserPrivilege::getElementName)
            .filter(elementName -> elementName != null && !elementName.isEmpty())
            .distinct()
            .toList();
    }
}
