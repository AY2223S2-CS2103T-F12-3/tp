package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.fields.Address;
import seedu.address.model.person.fields.CommunicationChannel;
import seedu.address.model.person.fields.Email;
import seedu.address.model.person.fields.Faculty;
import seedu.address.model.person.fields.Favorite;
import seedu.address.model.person.fields.Gender;
import seedu.address.model.person.fields.Major;
import seedu.address.model.person.fields.Modules;
import seedu.address.model.person.fields.Name;
import seedu.address.model.person.fields.Phone;
import seedu.address.model.person.fields.Race;
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

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    private final Favorite isFavorite;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Gender gender,
                  Major major, Modules modules, Race race, Set<Tag> tags, CommunicationChannel comms, Faculty faculty) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
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
                  Major major, Modules modules, Race race, Set<Tag> tags, CommunicationChannel comms,
                  Favorite favorite, Faculty faculty) {
        requireAllNonNull(name, favorite);
        this.name = name;
        this.isFavorite = favorite;

        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
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
        this.tags.addAll(new HashSet<>());
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
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Favorite getIsFavorite() {
        return isFavorite;
    }

    public Gender getGender() {
        return gender;
    }

    public Major getMajor() {
        return major;
    }

    public Race getRace() {
        return race;
    }

    public CommunicationChannel getComms() {
        return comms;
    }

    public Modules getModules() {
        return modules;
    }

    public Faculty getFaculty() {
        return faculty;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
    public boolean contains(HashMap<String, Set<String>> keywords) {
        if (keywords.containsKey(PersonContainsKeywordsPredicate.NAMEKEY)
                && !this.nameContains(keywords.get(PersonContainsKeywordsPredicate.NAMEKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.ADDRESSKEY)
                && !this.addressContains(keywords.get(PersonContainsKeywordsPredicate.ADDRESSKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.COMMSKEY)
                && !this.commsContains(keywords.get(PersonContainsKeywordsPredicate.COMMSKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.EMAILKEY)
                && !this.emailContains(keywords.get(PersonContainsKeywordsPredicate.EMAILKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.GENDERKEY)
                && !this.genderContains(keywords.get(PersonContainsKeywordsPredicate.GENDERKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.MAJORKEY)
                && !this.majorContains(keywords.get(PersonContainsKeywordsPredicate.MAJORKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.MODULESKEY)
                && !this.moduleContains(keywords.get(PersonContainsKeywordsPredicate.MODULESKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.PHONEKEY)
                && !this.phoneContains(keywords.get(PersonContainsKeywordsPredicate.PHONEKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.RACEKEY)
                && !this.raceContains(keywords.get(PersonContainsKeywordsPredicate.RACEKEY))) {
            return false;
        }
        if (keywords.containsKey(PersonContainsKeywordsPredicate.TAGKEY)
                && !this.tagsContains(keywords.get(PersonContainsKeywordsPredicate.TAGKEY))) {
            return false;
        }

        return true;
    }

    private boolean tagsContains(Set<String> test) {
        if (!test.isEmpty() && this.tags.isEmpty()) {
            return false;
        }
        for (String t: test) {
            for (Tag tag: this.tags) {
                if (tag.contains(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean nameContains(Set<String> test) {
        for (String t: test) {
            if (this.name.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean addressContains(Set<String> test) {
        for (String t: test) {
            if (this.address.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean commsContains(Set<String> test) {
        for (String t: test) {
            if (this.comms.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean emailContains(Set<String> test) {
        for (String t: test) {
            if (this.email.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean genderContains(Set<String> test) {
        for (String t: test) {
            if (this.gender.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean majorContains(Set<String> test) {
        for (String t: test) {
            if (this.major.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean moduleContains(Set<String> test) {
        for (String t: test) {
            if (this.modules.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean phoneContains(Set<String> test) {
        for (String t: test) {
            if (this.phone.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean raceContains(Set<String> test) {
        for (String t: test) {
            if (this.race.contains(t)) {
                return true;
            }
        }
        return false;
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
                && otherPerson.getTags().equals(this.getTags())
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

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
