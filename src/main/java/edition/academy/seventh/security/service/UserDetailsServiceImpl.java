package edition.academy.seventh.security.service;

import edition.academy.seventh.security.dao.UserRepository;
import edition.academy.seventh.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Responsible for exchanging information and returning the result of identification of
 * user in database {@link User}. Please see the {@link
 * org.springframework.security.core.userdetails.UserDetailsService} for true identity.
 *
 * @author Patryk Kucharski
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Creates {@link UserDetails} of user based on username provided.
   *
   * @param username of searched user.
   * @return {@link UserPrinciple} with all necessary information.
   * @throws UsernameNotFoundException when user wasn't found in database.
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                        "User Not Found with -> username or email : " + username));

    return UserPrinciple.build(user);
  }
}
