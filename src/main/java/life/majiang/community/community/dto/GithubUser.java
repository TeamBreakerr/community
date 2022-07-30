package life.majiang.community.community.dto;

import org.springframework.scheduling.support.SimpleTriggerContext;

public class GithubUser {
    private String name;
    private String id;
    private String bio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
