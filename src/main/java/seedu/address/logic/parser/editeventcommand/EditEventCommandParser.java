package seedu.address.logic.parser.editeventcommand;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRENCE;

public class EditEventCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);

        EventDescriptor editEventDescriptor = this.parseForTags(args);

        return new EditEventCommand(editEventDescriptor);
    }

    public EventDescriptor parseForTags(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_DESCRIPTION,  PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME, PREFIX_RECURRENCE);

        Optional<Index> index;
        EventDescriptor editEventDescriptor = new EventDescriptor();

        try {
            index = this.parseIndex(argMultimap.getPreamble());
            editEventDescriptor.setIndex(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, this.getMessageUsage()), pe
            );
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE_TIME).isPresent()) {
            editEventDescriptor.setStartDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE_TIME).isPresent()) {
            editEventDescriptor.setEndDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_RECURRENCE).isPresent()) {
            editEventDescriptor.setRecurrence(ParserUtil.parseRecurrence(argMultimap.getValue(PREFIX_RECURRENCE).get()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return editEventDescriptor;
    }

    public Optional<Index> parseIndex(String index) throws ParseException {
        return Optional.of(ParserUtil.parseIndex(index));
    }
    protected String getMessageUsage() {
        return EditEventCommand.MESSAGE_USAGE;
    }
}
