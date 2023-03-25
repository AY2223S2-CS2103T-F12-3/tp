package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.fields.Address;
import seedu.address.model.person.fields.CommunicationChannel;
import seedu.address.model.person.fields.Email;
import seedu.address.model.person.fields.Faculty;
import seedu.address.model.person.fields.Favorite;
import seedu.address.model.person.fields.Field;
import seedu.address.model.person.fields.Gender;
import seedu.address.model.person.fields.Major;
import seedu.address.model.person.fields.Modules;
import seedu.address.model.person.fields.Name;
import seedu.address.model.person.fields.Phone;
import seedu.address.model.person.fields.Race;
import seedu.address.model.person.fields.SuperField;
import seedu.address.model.person.fields.Tags;
import seedu.address.model.person.fields.subfields.NusMod;
import seedu.address.model.person.fields.subfields.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Gender gender;
    private final Major major;
    private final Modules modules;
    private final Race race;
    private final CommunicationChannel comms;
    private final Faculty faculty;
    private final Address address;
    private final Tags tags;

    private final Favorite isFavorite;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Gender gender,
                  Major major, Modules modules, Race race, Tags tags, CommunicationChannel comms, Faculty faculty) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
        this.gender = gender;
        this.major = major;
        this.modules = modules;
        this.race = race;
        this.comms = comms;
        this.isFavorite = new Favorite(false);
        this.faculty = faculty;
    }

    /**
     * Returns a new Person who is Favourited.
     * Require all fields to be present and not null
     */
    public Person(Name name, Phone phone, Email email, Address address, Gender gender,
                  Major major, Modules modules, Race race, Tags tags, CommunicationChannel comms,
                  Favorite favorite, Faculty faculty) {
        requireAllNonNull(name, favorite);
        this.name = name;
        this.isFavorite = favorite;

        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
        this.gender = gender;
        this.major = major;
        this.modules = modules;
        this.race = race;
        this.comms = comms;
        this.faculty = faculty;
    }

    /**
     * Constructor to create a Person with only a name. Will assign the rest of the fields as blank.
     */
    public Person(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.isFavorite = new Favorite(false);
        this.phone = new Phone("");
        this.email = new Email("");
        this.address = new Address("");
        this.tags = new Tags(new HashSet<>());
        this.gender = new Gender("");
        this.major = new Major("");
        this.modules = new Modules(new HashSet<>());
        this.race = new Race("");
        this.comms = new CommunicationChannel("");
        this.faculty = new Faculty("");
    }

    /**
     * Factory method to create a Person with no fields. Will assign the rest of the fields as blank.
     * Only for use for UserData. Should not be used anywhere else. Todo: Deprecate
     */
    public static Person ofDefaultUser() {
        return new Person(new Name("Neo"));
    }



    public Name getName() {
        return this.name;
    }

    public Phone getPhone() {
        return this.phone;
    }

    public Email getEmail() {
        return this.email;
    }

    public Address getAddress() {
        return this.address;
    }

    public Favorite getIsFavorite() {
        return this.isFavorite;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Major getMajor() {
        return this.major;
    }

    public Race getRace() {
        return this.race;
    }

    public CommunicationChannel getComms() {
        return this.comms;
    }

    public Modules getModules() {
        return this.modules;
    }

    public Faculty getFaculty() {
        return this.faculty;
    }

    public Tags getTags() {
        return this.tags;
    }

    /**
     * Returns an immutable NusMod set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<NusMod> getSetOfMods() {
        return this.modules.getValues();
    }

    /**
     * Returns an immutable Tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getSetOfTags() {
        return this.tags.getValues();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns same Person who is Favorited.
     */
    public Person favorite() {
        Favorite newFavorite = new Favorite(true);
        return new Person(name, phone, email, address, gender,
                major, modules, race, tags, comms, newFavorite, faculty);
    }

    /**
     * Returns same Person who is Unfavorited.
     */
    public Person unfavorite() {
        Favorite newFavorite = new Favorite(false);
        return new Person(name, phone, email, address, gender,
                major, modules, race, tags, comms, newFavorite, faculty);
    }

    /**
     * Returns true if the person contains one of the keywords in all the fields specified, returns false otherwise.
     */
    public boolean contains(HashMap<PredicateKey, Set<String>> keywords) {

        HashMap<PredicateKey, Field> fieldMap = new HashMap<>();
        fieldMap.put(PredicateKey.NAME, this.name);
        fieldMap.put(PredicateKey.ADDRESS, this.address);
        fieldMap.put(PredicateKey.COMMS, this.comms);
        fieldMap.put(PredicateKey.EMAIL, this.email);
        fieldMap.put(PredicateKey.GENDER, this.gender);
        fieldMap.put(PredicateKey.MAJOR, this.major);
        fieldMap.put(PredicateKey.PHONE, this.phone);
        fieldMap.put(PredicateKey.RACE, this.race);
        fieldMap.put(PredicateKey.FACULTY, this.faculty);

        for (PredicateKey key : keywords.keySet()) {
            Field field = fieldMap.get(key);
            if (field == null) {
                continue;
            }
            if (!field.contains(keywords.get(key))) {
                return false;
            }
        }

        HashMap<PredicateKey, SuperField<? extends Field>> superFieldMap = new HashMap<>();
        superFieldMap.put(PredicateKey.TAG, this.tags);
        superFieldMap.put(PredicateKey.MODULES, this.modules);

        for (PredicateKey key : keywords.keySet()) {
            SuperField<? extends Field> field = superFieldMap.get(key);
            if (field == null) {
                continue;
            }
            if (!field.contains(keywords.get(key))) {
                return false;
            }
        }
        return true;
    }



    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getSetOfTags().equals(this.getSetOfTags())
                && otherPerson.getGender().equals(this.getGender())
                && otherPerson.getMajor().equals(this.getMajor())
                && otherPerson.getRace().equals(this.getRace())
                && otherPerson.getModules().equals(this.getModules())
                && otherPerson.getComms().equals(this.getComms());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Gender: ")
                .append(this.getGender())
                .append("; Major: ")
                .append(this.getMajor())
                .append("; Race: ")
                .append(this.getRace())
                .append("; Preferred Communication: ")
                .append(this.getComms())
                .append("; Faculty: ")
                .append(this.getFaculty());

        Set<Tag> tags = getSetOfTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
