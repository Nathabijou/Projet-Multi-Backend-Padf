package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_privilege")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(name = "component_name", nullable = false)
    private String componentName;
    
    @Column(name = "element_name")
    private String elementName;
    
    @Column(name = "privilege_name", nullable = false)
    private String privilegeName;
    
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    
    // Optional: Add a constructor without ID for easier object creation
    public UserPrivilege(String userName, String componentName, String elementName, String privilegeName) {
        this.userName = userName;
        this.componentName = componentName;
        this.elementName = elementName;
        this.privilegeName = privilegeName;
    }
}
