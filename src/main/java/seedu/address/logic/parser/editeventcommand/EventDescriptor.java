package seedu.address.logic.parser.editeventcommand;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.parser.editpersoncommandsparser.PersonDescriptor;
import seedu.address.model.event.fields.DateTime;
import seedu.address.model.event.fields.Description;
import seedu.address.model.event.fields.Recurrence;

/**
 * Stores the details of the event.
 */
public class EventDescriptor {

    private Optional<Index> index;
    private Description description;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Recurrence recurrence;

    public EventDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EventDescriptor(EventDescriptor toCopy) {
        setIndex(toCopy.index);
        setDescription(toCopy.description);
        setStartDateTime(toCopy.startDateTime);
        setEndDateTime(toCopy.endDateTime);
        setRecurrence(toCopy.recurrence);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil
                .isAnyNonNull(description, startDateTime, endDateTime, recurrence);
    }

    public void setIndex(Optional<Index> index) {
        this.index = index;
    }

    public Optional<Index> getIndex() {
        return this.index;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Optional<DateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime);
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Optional<DateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime);
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Optional<Recurrence> getRecurrence() {
        return Optional.ofNullable(recurrence);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDescriptor)) {
            return false;
        }

        // state check
        EventDescriptor e = (EventDescriptor) other;

        return getDescription().equals(e.getDescription())
                && getStartDateTime().equals(e.getStartDateTime())
                && getEndDateTime().equals(e.getEndDateTime())
                && getRecurrence().equals(e.getRecurrence());
    }
}
