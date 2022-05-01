import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

//-----------------------------------------------
// Assignment 3
// © Anushka R Shetty
// Written by: Anushka R Shetty 40192371
//-----------------------------------------------

public class ProcessWishlist {
    private static Scanner scanner;
    public static ShowList showList;
    public static ArrayList<String> watchingListIDs;
    public static ArrayList<String> wishlistIDs;

    public static void main(String[] args) throws CloneNotSupportedException {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the TV guide");
        displayMenu();
        while(true){

                int userInput = Integer.parseInt(scanner.nextLine());
                if(userInput > 0 && userInput <= 7)
                    performAction(userInput);
                else
                    break;
        }
        scanner.close();
    }

    /**
     * Displays Menu for the user on the console
     */
    public static void displayMenu(){

        System.out.println("Please select one of options");
        System.out.println("1) Read TV Guide");
        System.out.println("2) Explore shows that you can watch");
        System.out.println("3) Find show");
        System.out.println("4) Add new shows to your list");
        System.out.println("5) Delete shows from your list");
        System.out.println("6) Test all methods");
        System.out.println("7) Quit");
    }
    /**
     * performs action from the menu based on the userInput
     * @param userInput takes user option
     */
    public static void performAction(int userInput) throws CloneNotSupportedException {
        readTVGuide(false);
        switch (userInput) {
            case 1:
                readTVGuide(true);
                displayMenu();
                break;
            case 2:
                canWatch();
                displayMenu();
                break;
            case 3:
                exploreShowList();
                displayMenu();
                break;
            case 4:
                System.out.println("Enter the position at which you want to insert");
                int position = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter show details");
                System.out.print("Show ID: ");
                String showID = scanner.nextLine();
                System.out.print("Show Name: ");
                String showName = scanner.nextLine();
                System.out.print("Show start time: ");
                Double showStartTime = Double.parseDouble(scanner.nextLine());
                System.out.print("Show end time: ");
                Double showEndTime = Double.parseDouble(scanner.nextLine());
                TVShow tvShow = new TVShow(showID,showName,showStartTime,showEndTime);
                showList.insertAtIndex(tvShow,position);
                displayMenu();
                break;
            case 5:
                System.out.println("Enter the position from which you want to delete");
                position = Integer.parseInt(scanner.nextLine());
                showList.deleteFromIndex(position);
                displayMenu();
                break;
            case 6:
                testAllMethods();
                displayMenu();
                break;
            case 7:
                System.out.println("Thank you for using the TV guide");
                System.exit(0);

        }
    }

    /**
     * process the TV Guide and add shows to the showList
     * @param displayMessage
     */
    public static void readTVGuide(boolean displayMessage){

        showList = new ShowList();
        Scanner tvGuideScanner = null;

        try{
            tvGuideScanner = new Scanner(new FileInputStream("TVGuide.txt"));

            //Reading through the TVguide file
            while(tvGuideScanner.hasNextLine()){
                String[] fileInputs = tvGuideScanner.nextLine().split(" ");
                String ID = fileInputs[0];
                String showName = fileInputs[1];
                fileInputs = tvGuideScanner.nextLine().split(" ");

                double startTime = Double.parseDouble(fileInputs[1]);
                fileInputs = tvGuideScanner.nextLine().split(" ");
                double endTime = Double.parseDouble(fileInputs[1]);
                if(tvGuideScanner.hasNextLine()) tvGuideScanner.nextLine();

                TVShow show = new TVShow(ID,showName,startTime,endTime);

                if(!showList.contains(show.getShowID())){
                    showList.addToStart(show);
                   if(displayMessage) System.out.println(show.getShowID()+" added to the show List");
                }else{
                   if(displayMessage) System.out.println("Duplicate show with show ID: " + show.getShowID() + " will not be added.");
                }

            }
        }catch(FileNotFoundException e){
            e.getMessage();
            System.exit(0);
        }finally {
            if(tvGuideScanner != null) tvGuideScanner.close();
        }
    }

    /**
     *  Process the interest files and check if the show can be watched
     */
    public static void canWatch(){

        System.out.println("Enter name of interests file:");
        String fileName = scanner.nextLine();
        watchingListIDs = new ArrayList<>();
        wishlistIDs = new ArrayList<>();
        Scanner interests = null;

        try{
            interests = new Scanner(new FileInputStream(fileName));
            interests.nextLine(); //To skip the watching line
            String ShowID = interests.nextLine();
            //Loop for watching list
            while(!ShowID.equalsIgnoreCase("Wishlist")){
                watchingListIDs.add(ShowID);
                ShowID = interests.nextLine();
            }
            //Loop for wishlist
            while(interests.hasNextLine()){
                ShowID = interests.nextLine();
                if(!ShowID.equals("\n") && !ShowID.equals(""))
                    wishlistIDs.add(ShowID);
            }
            HashSet<String> canWatch = new HashSet<>();
            HashSet<String> cantWatch = new HashSet<>();

            for(int i=0; i<watchingListIDs.size(); i++){
                for(int j=0; j<wishlistIDs.size(); j++){

                    TVShow show1 = showList.findShow(watchingListIDs.get(i),false);
                    TVShow show2 = showList.findShow(wishlistIDs.get(j),false);
                    if(show1.isOnSameTime((show2)).equalsIgnoreCase("Different time")){
                        canWatch.add(wishlistIDs.get(j));
                    }else{
                        cantWatch.add(wishlistIDs.get(j));
                    }
                }
            }
            canWatch.removeAll(cantWatch);

        for(String show : cantWatch)
            System.out.println("User can't watch show " + show + " as there is some overlap with a show he/she would rather be watching");
        for(String show : canWatch)
            System.out.println("User can watch show " + show + " as he/she is not watching anything else during that time");

        }
        catch (FileNotFoundException e){
            e.getMessage();
            System.exit(0);
        }
        finally {
            if(interests != null) interests.close();
        }
    }

    /**
     * find show details using the show ID
     */
    public static void exploreShowList(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter show IDs, enter \"0\" once done");
        String ID = in.nextLine();
        ArrayList<String> IDList = new ArrayList<>();

        while(!ID.equalsIgnoreCase("0")){
            IDList.add(ID);
            ID = in.nextLine();
        }
        for(int i=0; i<IDList.size();i++){
            TVShow tempShow = showList.findShow(IDList.get(i),true);
            if(tempShow == null)
                System.out.println("No show was found");
            else
                System.out.println(tempShow);
        }


    }

    /**
     * Tests all the constructors and all the methods of the TVShows, ShowList
     * @throws CloneNotSupportedException
     */
    public static void testAllMethods() throws CloneNotSupportedException {

        System.out.println("\n****** Testing all methods. *****");
        System.out.println("\n****** Testing TVShow Class ******");

        // Constructors
        System.out.println("\n****** Testing Constructors ******\n");
        TVShow testShows = new TVShow("ShowID","ShowName",9.30,11.30);
        TVShow test2Shows = new TVShow("ShowID","ShowName",9.00,12.00);
        TVShow test3Shows = new TVShow("ShowID","ShowName",9.00,12.00);
        System.out.println(testShows);
        System.out.println(test2Shows);
        TVShow testCopy = new TVShow(test2Shows,"ShowID2");
        System.out.println(testCopy);

        // Clone
        System.out.println("\n-*******- Clone Method -*******-");
        System.out.println(testShows.clone());

        // Same Time
        System.out.println("\n-*******- Same Time Method -*******-");
        System.out.println(testShows.isOnSameTime(test2Shows));
        System.out.println(testShows.isOnSameTime(testCopy));

        // Equals
        System.out.println("\n-*******- Equals Method -*******-");
        System.out.println(testShows.equals(test2Shows));
        System.out.println(test2Shows.equals(test3Shows));

        System.out.println("\n******* Testing ShowList Class *******");

        // Constructors
        System.out.println("\n-*******- Copy Constructor -*******-\n");
        ShowList showList2 = new ShowList(showList);
        System.out.println(showList2.toString());

        // insert at index
        System.out.println("\n-*******- Insert at Index -*******-");
        showList.insertAtIndex(testShows,1);
        System.out.println(showList.toString());
        System.out.println("Size of showlist = " + showList.getSize() + "\n");

        // delete at index
        System.out.println("\n-*******- Delete at Index -*******-");
        showList.deleteFromIndex(3);
        System.out.println(showList.toString());
        System.out.println("Size of showlist = " + showList.getSize() + "\n");

        // delete head
        System.out.println("\n-*******- Delete Head -*******-");
        showList.deleteFromStart();
        System.out.println(showList.toString());
        System.out.println("Size of showlist = " + showList.getSize() + "\n");

        // replace head
        System.out.println("\n-*******- Replace at Index -*******-");
        showList.replaceAtIndex(testShows,6);
        System.out.println(showList.toString());
        System.out.println("Size of showlist = " + showList.getSize());

        System.out.println("-*******- Get size -*******-\n");
        System.out.println(showList.toString());
        System.out.println("Size of showlist = " + showList.getSize());

        System.out.println("\n=*******= Testing ShowNode Class =*******=");

        System.out.println("\n-*******- Constructors -*******-\n");
        ShowList.ShowNode showNode0 = new ShowList.ShowNode();
        showNode0.displayNodeDetails();
        ShowList.ShowNode showNode1 = new ShowList.ShowNode(testShows,showNode0);
        showNode1.displayNodeDetails();
        ShowList.ShowNode showNodeCopy = new ShowList.ShowNode(showNode1);
        showNodeCopy.displayNodeDetails();
        ShowList.ShowNode showNode2 = new ShowList.ShowNode(showNode1);
        showNode2.displayNodeDetails();

        System.out.println("\n-*******- Clone Method -*******-\n");
        ShowList.ShowNode sn1Clone = (ShowList.ShowNode) showNode1.clone();
        sn1Clone.displayNodeDetails();

        System.out.println(showList2.equals(showList));

    }


}
