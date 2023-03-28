package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.event.fields.DateTime;
import seedu.address.model.event.fields.Description;
import seedu.address.model.event.fields.Recurrence;
import seedu.address.model.person.Person;

/**
 * Represents an Event in the Calendar.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Event {
    protected final Description description;
    protected final DateTime startDateTime;
    protected final DateTime endDateTime;
    protected final Recurrence recurrence;
    protected Set<Person> taggedPeople = new HashSet<>();

    /**
     * Every field must be present and not null.
     * One Time Events will be handled by a None Recurrence Enum.
     */
    public Event(Description description, DateTime startDateTime, DateTime endDateTime, Recurrence recurrence,
                 Set<Person> taggedPeople) {
        requireAllNonNull(description, startDateTime, endDateTime, recurrence, taggedPeople);
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.recurrence = recurrence;
        this.taggedPeople.addAll(taggedPeople);
    }

    public Description getDescription() {
        return description;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void addTaggedPerson(Person p) {
        this.taggedPeople.add(p);
    }

    public void deleteTaggedPerson(Person p) {
        this.taggedPeople.remove(p);
    }

    /**
     * Edits {@code personToEdit} into {@code editedPerson} if {@code personToEdit} exists.
     */
    public void editTaggedPerson(Person personToEdit, Person editedPerson) {
        this.taggedPeople = this.taggedPeople.stream().map(
                person -> person.equals(personToEdit) ? editedPerson : person).collect(Collectors.toSet());
    }

    /**
     * Returns true if there is a person in the addressbook that is equal to the person {@code p}.
     */
    public boolean hasTaggedPerson(Person p) {
        for (Person p2: this.taggedPeople) {
            if (p.equals(p2)) {
                return true;
            }
        }
        return false;
    }

    public Set<Person> getTaggedPeople() {
        return this.taggedPeople;
    }

    //Check if this is a recurring event.
    public boolean isRecurring() {
        return recurrence.isRecurring();
    }

    public DateTime getEffectiveStartDateTime() {
        return startDateTime;
    }

    public DateTime getEffectiveEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns true if both Events have same Description.
     * This defines a weaker notion of equality between two Events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both Events have same Description, and data fields.
     * This defines a Stronger notion of equality between two Events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getRecurrence().equals(getRecurrence());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(
                this.description + "; " + this.startDateTime + "; " + this.endDateTime + "; " + this.recurrence);
        for (Person p: taggedPeople) {
            str.append(p.getName());
        }
        return str.toString();
    }
}
