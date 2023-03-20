package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;



/**
 * Selects person in address book to display details
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the details of the selected contacted "
            + "by the index of the contact in current displayed list.\n "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_INDEX = "The provided contact index "
            + "should be in the range of %d to %d (inclusive).";
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected User: %1$s";

    private final Index index;

    /**
     * @param index Index of the contact to display.
     */
    public SelectCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.setSelectedPerson(index);
        Person selectedPerson = model.getSelectedPerson().get();
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, selectedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SelectCommand)) {
            return false;
        }

        // state check
        SelectCommand e = (SelectCommand) other;
        return index.equals(e.index);
    }
}
