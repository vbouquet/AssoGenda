package fr.paris10.projet.assogenda.assogenda.model;

import java.util.HashSet;
import java.util.Set;

public class Tag {
    private String name;
    private Set<Tag> tags;

    public Tag(String name, Set<Tag> tags) {
        this.name = name;
        this.tags = tags;
    }

    public Tag(String name) {
        this.name = name;
        tags = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean containsTag(Tag tag) {
        return tags.contains(tag);
    }
}
