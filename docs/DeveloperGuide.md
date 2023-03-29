---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `BodyPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person`, `Event`, and `User` objects residing in the `Model`.

The UI is styled using CSS, primarily via [`DarkTheme.css`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/master/src/main/resources/view/DarkTheme.css) and [`Extensions.css`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/master/src/main/resources/view/Extensions.css) that are imported by `MainWindow.fxml`. The `HelpWindow` has its own separate CSS file, [`HelpWindow.css`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/master/src/main/resources/view/HelpWindow.css).

In `DarkTheme.css`, there is a system of reuse. For example,

* Themes have their own defined variables:
  * `-fx-primary`: primary theme colour
  * `-fx-primary-foreground`: primary theme colour, usually for foreground and accented components
  * `-fx-primary-background`: primary theme colour, usually for background and muted components
  * `-fx-primary-text`: primary text colour
  * `-fx-secondary-text`: secondary text colour
  * `-fx-list-odd`: background colour of odd-indexed list cells
  * `-fx-list-even`: background colour of even-indexed list cells
  * `-fx-list-selected`: background colour of selected list cells

* Labels have defined font sizes: `label-h1`, `label-h2`, `label-h3`, `label-h4`, `label-h5`, `label-h6`, and `label-p`, corresponding to the different headings and paragraph font sizes.

* Paddings have been defined and standardised with a width of 10. For example, `pa` represents "all padding", `pt` for "top padding", and `pa-b` for "all padding except bottom".

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

Within the Model component holds the Person Class.

<img src="images/PersonDiagram.png" width="450" />

Each field in Person inherits from either the Field abstract class or the SuperField abstract class.

* The Field abstract class represents a field with a singular value. This is used for fields like e.g. Name, Gender
and Major.
* The SuperField abstract class represents a field that has multiple values. This is used for fields like e.g. Modules
and Tags. The SuperField class contains a set of values that are a subclass of Field.
* The Field and SuperField abstract classes are used to abstract our common logic between the various fields in Person, while
  also allowing for polymorphism.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data, user data and user preference data in json format, and read them back into
corresponding objects.
* inherits from `UserDataStorage`, `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one
(if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
that belong to the `Model`). In particular, the changes to the following 5 classes will require a change in their
respective classes in Storage.
  * Person
  * Event
  * User
  * NusMod
  * Tag

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Command for tab switching

Due to the limited space on most displays, the application uses tabs to switch between various panels, such as the address book, events, and personal information panels.

The tab switching mechanism is facilitated by [`TabUtil`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/docs/dg-tab-command/src/main/java/seedu/address/logic/ui/tab/TabUtil.java), which contains the list of all tabs relevant in the application. Tabs are represented by [`TabInfo`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/docs/dg-tab-command/src/main/java/seedu/address/logic/ui/tab/TabInfo.java), made up of an `Index` (which the user references in the `tab` command) and a [`TabType`](https://github.com/AY2223S2-CS2103T-F12-3/tp/blob/docs/dg-tab-command/src/main/java/seedu/address/logic/ui/tab/TabType.java) (which defines the possible tabs in the application). Their relationship is shown below:

![Structure of `TabUtil`](images/TabClassDiagram.png)

#### Design considerations:

There are two ways to switch between tabs:
* **Method 1:** Click on individual tabs in the tabs bar
* **Method 2:** Use the `tab` command

Consequently, the state of the selected tab needs to be shared between the two methods, so that the user can be correctly notified if they are already on a tab that they are trying to navigate to.

> **Example:** If the user has navigated from the 1st tab to the 3rd using the tab bar before trying to navigate back to the 1st tab using `tab 1`, they should not be warned that they are already on the 1st tab. In other words, both methods should have their states in sync from the perspective of the user, so as not to induce unexpected behaviour.

### Command for selecting

The `select` feature allows user to select which contact's details to display on the person details panel.

There is _selected person_ field in `AddressBook` that keep tracks of which person's details should be displayed.
This field is initialised to null at the start when no person is selected yet.
The `select` feature is facilitated through `SelectCommandParser` and `SelectCommand`.
The `LogicManager` executes the `SelectCommand`, which communicates with the `ModelManager` and updates the _selected person_ field in `AddressBook`.

The following sequence diagram illustrates the execution of a successful select command.
Low-level details of the parsing of select command in logic is omitted as it has been described [above](#logic-component)

![Sequence Diagram of successful `select`](images/SelectSequenceDiagram.png)

Upon execution, the `selectedPerson` field of AddressBook would be updated, allowing the GUI to access it
and display changes accordingly.

#### Design considerations:

There are two ways to select a contact:
* **Method 1:** Click on the contact in the current displayed contact list
* **Method 2:** Use the `select` command

Consequently, the state of the selected person needs to be shared between the two methods, so that the user can be correctly notified if they have already selected the contact that they are trying to access.
> **Example:**
> - If the user has selected index 1 by clicking on the contact, executing "select 1" should result in a warning that the contact is already selected.
> - If the user has selected index 1 by using `select 1`, clicking on the same contact at index 1 should "deselect" contact.

### Command for Favourite Contacts

For improved User Experience, we want users to be able to easily look up contacts they frequently contact.
This addresses the issue of convenience.

The Favourite Command works by having the User entering a "fav" command and specify the index of that particular contact. The index will be displayed on the UI of the AddressBook.
The Favourited Contact will have a Star Emoji displayed beside the Contact's name and will also be displayed under a "Favourite" List for easier convenience and lookup for the User.

### Command for Unfavourite Contacts

Following the Favourite Contacts Command, we want users to fully manage their favourite list. We added an Unfavourite Command to remove contacts they do not frequently contact.

The Unfavourite Command works by having the User entering a "unfav" command and specify the index of that particular contact that is currently in the Favourite List. The index will be displayed on the UI of the AddressBook.
The Unfavourited Contact will remove the Star Emoji displayed beside the Contact's name and remove the Contact from the Favourite List.

### Command for Add Event Command

AddressBook Neo implements an Event Calendar Interface for users to track any notable events. There are 2 types of Events that can be added by the Users. Firstly, a One Time event that occurs only once on the specified date and time.
Secondly, recurring events that occur periodically e.g. Weekly Lectures, Daily Reminders etc. There are multiple recurrences which can be specified by the users: Daily, Weekly, Monthly and Yearly.
Users have can choose to input these 4 different type of Recurring Events into AddressBook Neo.

The Add Event Command works by having the User entering the "addevent" command. The User will then specify the Description of the Event, Start Date and Time of the event,
End Date and Time of the Event, followed by the Recurrence type, whether it is Daily, Weekly, Monthly, Yearly, or a One-Time Event. All are required fields except the Recurrence Field.
If left unspecified, the Event will be added as a One-Time Event, the Success Message will prompt to the user, what type of Event will have be added to the Events Calendar UI of AddressBook Neo.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope (v1.1)

**Target user profile**:

* NUS students who want to connect with seniors/other students with common academic paths
* Freshmen looking to get advice from their seniors in the same course or taken the same modules
* Students who want to get internship advice from others who have worked in that company
* Students who want to know more about their friends and seniors' schedules to be able to find time to connect with them

**Value proposition**: Being able to find contacts with similar traits and being able to view their schedule to find an appropriate time to connect with them.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                 | I want to …​                                                                                                           | So that I can…​                                |
| -------- |---------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| `* * *`  | Student who often needs help with school work           | I want to be able to see who is likely to be available currently                                                       | Get help as soon as possible                   |
| `* * *`  | Student who often contacts external organisations       | Segregate my contacts into different categories (e.g. friends, family, t-shirt suppliers, bus drivers , sponsors etc.) | Organise my contacts effectively               |
| `* * *`  | Student sourcing for internships                        | I am able to see Students internship experience                                                                        | To find out more about their interview process |
| `* * *`  | Student who wants to go for a career fair with somebody | I want to find people who are available at a certain time                                                              | So that I can go to the career fair with them  |
| `* *`    | Student who made a new connection                       | I want to be able to add a new contact                                                                                 | So that I can save the person’s details        |
| `*`      | Student who is in to keeping everything in one place    | I like being able to keep everything I need to see in one place                                                        | So that I dont forget anything                 |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a person**

**MSS**

1.  User requests to add a person
2.  User enters person details and submits the command
3.  AddressBook saves the person

    Use case ends.

**Extensions**

* 2a. AddressBook is unable to save the person.

    * 2a1. AddressBook shows an error message.

      Use case resumes at step 1.

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list
4.  AddressBook deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

**Use case: Editing a person**

**MSS**

1.  User enters a command to edit an existing person by specifying their index number and new details
2.  AddressBook updates the person's details

    Use case ends.

**Extensions**

* 1a. User enters an invalid command or incorrect details.

    * 1a1. AddressBook shows an error message.

      Use case ends.

* 1b. User enters an invalid index number.

    * 1b1. AddressBook shows an error message.

      Use case ends.

* 2a. AddressBook is unable to update the person's details.

    * 2a1. AddressBook shows an error message.

      Use case ends.

**Use case: List all persons**

**MSS**

1.  User enters a command to list all persons
2.  AddressBook shows a list of all persons with their details

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. AddressBook shows an error message.

      Use case ends.

**Use case: Find persons by specified fields**

**MSS**

1.  User enters a command to search for persons by specifying one or more keywords for different fields
2.  AddressBook searches for persons who contain any of the given keywords for the fields specified
3.  AddressBook returns a list of persons matching the search criteria

    Use case ends.

**Extensions**

* 1a. User enters an invalid command or incorrect details.

    * 1a1. AddressBook shows an error message.

      Use case ends.

* 3a. No persons are found matching the search criteria.

    * 3a1. AddressBook shows an empty list message.

      Use case ends.

**Use case: Clearing all entries**

**MSS**

1.  User enters a command to clear all entries
2.  AddressBook clears all entries from the address book
3.  AddressBook shows a success message

    Use case ends.

**Extensions**

* 1a. User enters an invalid command or incorrect details.

    * 1a1. AddressBook shows an error message.

      Use case ends.

* 2a. AddressBook is unable to clear all entries.

    * 2a1. AddressBook shows an error message.

      Use case ends.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be easy to use and navigate, even for users who are not familiar with the NUS student community. This could include features such as clear labeling, intuitive interfaces, and helpful error messages.
5. Should be accessible to users with disabilities, including those who use screen readers or other assistive technologies. The user interface should be highly readable for all users.
6. Should provide fast response times and minimal resource usage, even when running on lower-end hardware.
7. Should be able to handle errors and exceptions gracefully, without crashing or losing user data. This could include features such as error logging, fault tolerance mechanisms, and data backup options.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
