package edition.academy.seventh.security;

import edition.academy.seventh.security.model.RoleName;
import edition.academy.seventh.security.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class RepositoryTestIT {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void should_findUser_when_addUserToDB() {
        //TODO przeniesc do userRepositoryTest
        //Given
        roleRepository.addRole(RoleName.ROLE_ADMIN);
        User user = new User("admin@admin.pl", "admin", "password", Set.of(roleRepository.findByName(RoleName.ROLE_ADMIN).get()));

        //When
        userRepository.saveUser(user);

        //Then
        assertEquals(user, userRepository.findByUsername("admin").get());
    }
}
