package com.userRed.redesigned.auth;

import java.util.Optional;

public interface RedesignedApplicationUserDAO {

    Optional<RedsignedApplicationUser> selecAppUser(String username);
}
