
package wad.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class account extends AbstractPersistable<Long> {
    
    private String username;
    
    private String password;
    
}
