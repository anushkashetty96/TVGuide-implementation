The purpose of this program is to allow users to check if they can watch a specific TV show based on the shows they are currently watching. The program reads information from two files, TVGuide.txt containing information about various TV shows, and Interests.txt which contains information about the shows a user is interested in. The program then determines if the user can watch a specific show based on the options below:

User can watch show X as they are not watching anything else during that time.
User can’t watch show X as they are not finished with a show they are watching.
User can’t watch show X as they will begin another show at the same time.
The program assumes that every show will be a minimum of half an hour long and a maximum of one hour long. It also assumes that the shows will be listed in a sorted manner based on their start time. Interests.txt will contain a word “Watching” on the first line, followed by the showID of the shows the user is currently watching. Another word “Wishlist” after the above information followed by the respective showIDs. TVGuide.txt will contain showID along with show name (one word separated by _). The next two lines will have S and E indicating start time and end time respectively for this show. This set of information is repeated for all the available shows.

# Requirements
1. Java SE Development Kit 8 or higher
2. Eclipse IDE or any other Java IDE
3. JUnit for testing

# Installation
1. Clone the repository to your local machine.
2. Import the project into your Java IDE.
3. Build the project.
4. Run the TVGuide.java file to start the program.

# Program Design
# Watchable interface
The Watchable interface has a method isOnSameTime(Show S) where S is an object of type TVShow.

# TVShow class
The TVShow class has the following attributes:

* showID (String type)
* showName (String type)
* startTime (double type)
* endTime (double type)

It is assumed that the show name is always recorded as a single word (_ is used to combine multiple words). It is also assumed that no two TV shows can have the exact same showID.

The class has the following methods:

* Parameterized constructor that accepts four values and initializes showID, showName, startTime, and endTime to these passed values.
* Copy constructor, which takes in two parameters, a show object and a String value. The newly created object will be assigned all the attributes of the passed object, with the exception of the showID. showID is assigned the value passed as the second parameter to the constructor. It is always assumed that this value will correspond to the unique showID rule.
* clone() method. This method will prompt the user to enter a new showID, then creates and returns a clone of the calling object with the exception of the showID, which is assigned the value entered by the user.
* toString() method, which returns a string representation of the object.
* equals() method, which checks if two shows are equal. Two shows are equal if they have the same attributes, with the exception of the showID, which could be different.
* isOnSameTime(Show S) method, which takes in another TVShow object S and should return “Same time”, “Different time”, or “Some Overlap” depending on the times of two TVShow objects.

# Running the program

1. Open a terminal or command prompt.
2. Navigate to the directory containing the TVGuide.jar file.
3. Run the following command: java -jar TVGuide.jar
4. Follow the on-screen instructions.

When you run the TVGuide.java file, you will see a menu with the following options:

1. Add a TV show
2. Delete a TV show
3. Search for a TV show
4. Display all TV shows
5. Exit

Adding a TV show
To add a TV show, select option 1 from the menu. You will be prompted to enter the title and schedule for the show. The schedule should be in the format "day start-time end-time", e.g. "Monday 8:00 PM 9:00 PM". Once you enter the information, the show will be added to the guide.

Deleting a TV show
To delete a TV show, select option 2 from the menu. You will be prompted to enter the title of the show you want to delete. If the show is in the guide, it will be deleted.

Searching for a TV show
To search for a TV show, select option 3 from the menu. You will be prompted to enter the title of the show you want to search for. If the show is in the guide, its information will be displayed. If the show is not in the guide, a message will be displayed indicating that the show was not found.

Displaying all TV shows
To display all TV shows in the guide, select option 4 from the menu. The title and schedule for each show will be displayed.

Exiting the program
To exit the program, select option 5 from the menu.
