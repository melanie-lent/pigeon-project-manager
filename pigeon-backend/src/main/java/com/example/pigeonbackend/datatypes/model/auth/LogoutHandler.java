package com.example.pigeonbackend.datatypes.model.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.stereotype.Service;

@Service
public class LogoutHandler {
//        private final UserCache userCache;

//        public LogoutHandler(UserCache userCache) {
//            this.userCache = userCache;
//        }
        public LogoutHandler() {

        }

//        public void logout(HttpServletRequest request, HttpServletResponse response,
//                           Authentication authentication) {
//            String userName = JwtUtils.getUsername();
//            userCache.removeUserFromCache(userName);
//        }
//    }
}