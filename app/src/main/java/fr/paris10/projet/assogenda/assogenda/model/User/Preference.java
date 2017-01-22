package fr.paris10.projet.assogenda.assogenda.model.User;

import java.util.Map;
import java.util.Set;

import fr.paris10.projet.assogenda.assogenda.model.Tag;
import fr.paris10.projet.assogenda.assogenda.util.Day;
import fr.paris10.projet.assogenda.assogenda.util.Period;

public class Preference {
    // Represent user's disponibility for a weak
    private Map<Day, Period> timeSlots;
    private Set<Tag> tags;

    public Preference(Map<Day, Period> timeSlots, Set<Tag> tags) {
        this.timeSlots = timeSlots;
        this.tags = tags;
    }

    public Map<Day, Period> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Map<Day, Period> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        if(!tags.contains(tag))
            this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        if(tags.contains(tag))
            tags.remove(tag);
    }

    public void clearTags() {
        tags.clear();
    }
}
