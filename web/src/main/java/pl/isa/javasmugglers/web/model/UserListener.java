package pl.isa.javasmugglers.web.model;

import jakarta.persistence.PrePersist;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import pl.isa.javasmugglers.web.model.user.User;

import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
    public class UserListener {
        @PrePersist
        public void userPrePersist(User ob) {
            System.out.println("Listening User Pre Persist : " + ob.getStatus());
        }
        @PostPersist
        public void userPostPersist(User ob) {
            System.out.println("Listening User Post Persist : " + ob.getStatus());
        }
        @PostLoad
        public void userPostLoad(User ob) {
            System.out.println("Listening User Post Load : " + ob.getStatus());
        }
        @PreUpdate
        public void userPreUpdate(User ob) {
            System.out.println("Listening User Pre Update : " + ob.getStatus());
        }
        @PostUpdate
        public void userPostUpdate(User ob) {
            System.out.println("Listening User Post Update : " + ob.getStatus());
        }
        @PreRemove
        public void userPreRemove(User ob) {
            System.out.println("Listening User Pre Remove : " + ob.getStatus());
        }
        @PostRemove
        public void userPostRemove(User ob) {
            System.out.println("Listening User Post Remove : " + ob.getStatus());
        }
    }

