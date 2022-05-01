import java.util.NoSuchElementException;

//-----------------------------------------------
// Assignment 3
// © Anushka R Shetty
// Written by: Anushka R Shetty 40192371
//-----------------------------------------------

public class ShowList {
    private ShowNode head;
    private int size;
    public ShowList(){ // default constructor
        head = null;
        size = 0;
    }

    public ShowList(ShowList o){
        size = o.size;
        head = new ShowNode(o.head);
        ShowNode currentNode = head;
        ShowList cloneList = new ShowList();

        for(int i=0; i<o.size; i++) {
            ShowNode newNode = new ShowNode(currentNode);
            cloneList.addToStart(newNode.getShow());
            currentNode = currentNode.getNext();
        }
    }

    /**
     * gets size of list
     * @return size of list
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return ShowNode properties in string format
     */
    @Override
    public String toString() {
        String result = "";
        ShowNode current = head;
        while(current!= null){
            result += current.tvShow.getShowID()+"-"+current.tvShow.getShowName();
            if(current.getNext() != null){
                result += ", ";
            }
            current = current.getNext();
        }
        return "List: " + result+"\nSize is "+size;
    }

    /**
     * Add TVShow to the start of the list
     * @param t TVShow
     */
    public void addToStart(TVShow t){
        head = new ShowNode(t,head);
        size++;
    }

    /**
     * Insert the TVShow at the given index
     * @param data
     * @param index
     */
    public void insertAtIndex(TVShow data, int index) {
        try {
            ShowNode newNode = new ShowNode(); //create a new node
            newNode.tvShow = data;
            newNode.next = null;

            if (this.head == null && index != 0) {
                System.out.println("Empty List cannot be inserted at "+ index + " position");
                return;
            } else if (this.head == null && index == 0) {
                head = newNode;
            }

            if (index == 0) {
                newNode.next = this.head;
                this.head = newNode;
            }

            ShowNode current = this.head;
            ShowNode previous = null;
            int i = 0;

            while (i < index) {
                previous = current;
                current = current.next;
                if (current == null)
                    break;
                i++;
            }

            newNode.next = current;
            previous.next = newNode;
            size++;
            System.out.println("Show successfully added at "+ index + " position");

        } catch (NoSuchElementException e){
            e.getMessage();
            System.exit(0);
        }

    }

    /**
     * Delete the TVShow at the given index from the list
     * @param index
     */
    public void deleteFromIndex(int index){
        try {
            if (index < 0 || index > (this.size - 1)) {
                throw new NoSuchElementException();
            } else {
                if (head == null) {
                    return;
                }

                ShowNode temp = head;


                if (index == 0) {
                    head = temp.next;
                    return;
                }


                for (int i = 0; i < index - 1; i++) {
                    temp = temp.next;
                }


                if (temp == null || temp.next == null) {
                    return;
                }


                ShowNode next = temp.next.next;


                temp.next = next;
                size--;
                System.out.println("Successfully deleted from "+ index+ " position");
            }
        } catch (NoSuchElementException e){
            e.getMessage();
            System.out.println("\nTerminating the program...");
            System.exit(0);
        }

    }

    /**
     * Delete the TVShow from the start of the List
     */
    public void deleteFromStart() {
        try {
            if (head == null) {
                throw new NullPointerException();
            } else {
                ShowNode temp = head;
                head = head.next;
                temp.next = null;
                size--;
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    /**
     * Replace the TVShow at the given index
     * @param t
     * @param index
     */
    public void replaceAtIndex(TVShow t, int index) {
        if (index < 0 || index > (this.size - 1)) {
            return;
        } else {

            if (head == null) {
                return;
            }

            // Find the node
            int count = 1;
            ShowNode current = head;
            if (index >= 1) {
                while(current.getNext() != null) {
                    ++count;
                    current = current.getNext();
                    if (index == count) {

                        break;
                    }
                }
            }


            if (index == count) {
                current.setShow(t);
            }
        }
    }

    /**
     * searches for a given show and returns the show details if found
     * @param newID
     * @return
     */
    //Because this returns a pointer to a shownode object, there are potential privacy leaks here
    public ShowNode find(String newID){

        ShowNode current = head;
        int loopCounter = 1;

        while (current != null) {
            if (current.tvShow.getShowID().equalsIgnoreCase(newID)) {
               // System.out.println(loopCounter + " iterations were completed.");
                return current;
            } else {
                current = current.next;
                loopCounter++;
            }
        }

    //    System.out.println(loopCounter + " iterations were completed.");
        return null;
    }

    /**
     * searches for a given show and returns the show details if found
     * @param newID
     * @return
     */
    //Because this returns a pointer to a shownode object, there are potential privacy leaks here
     public TVShow findShow(String newID, boolean showIterations){

        ShowNode current = head;
        int loopCounter = 1;

        while (current != null) {
            if (current.tvShow.getShowID().equalsIgnoreCase(newID)) {
                if(showIterations)System.out.println(loopCounter + " iterations were completed.");
                return current.getShow();
            } else {
                current = current.next;
                loopCounter++;
            }
        }

        if(showIterations){
        System.out.println(loopCounter + " iterations were completed.");
        }
        return null;
    }

    public boolean equals(ShowList s){

        ShowNode s1 = this.head;
        ShowNode s2 = s.head;
        while (s1 != null && s2 != null)
        {
            if (s1.tvShow.getEndTime() != s2.tvShow.getEndTime() &&
                    s1.tvShow.getStartTime() != s2.tvShow.getStartTime() &&
                    s1.tvShow.getShowName() != s2.tvShow.getShowName()) {
                return false;
            }
            s1 = s1.next;
            s2 = s2.next;
        }

        return (s1 == null && s2 == null);
    }
    public boolean contains(String showID){
        return(find(showID) != null);
    }

    public static class ShowNode implements Cloneable{
        public TVShow tvShow;
        public ShowNode next; // Pointer to next node in list.


        public ShowNode() { // default constructor
            this.tvShow = null;
            this.next = null;
        }

        public ShowNode(TVShow show, ShowNode next) { //parameterised constructor
            this.tvShow = show;
            this.next = next;
        }


        public ShowNode(ShowNode o) {  //copy constructor
            this(o.tvShow, o.next);
        }

@Override
        protected Object clone() throws CloneNotSupportedException {
            ShowNode clone = (ShowNode) super.clone();

            ShowNode showNode = new ShowNode();

            TVShow tvShowToBeAdded = new TVShow();
            tvShowToBeAdded.setShowID(clone.tvShow.getShowID());
            tvShowToBeAdded.setShowName(clone.tvShow.getShowName());
            tvShowToBeAdded.setStartTime(clone.tvShow.getStartTime());
            tvShowToBeAdded.setEndTime(clone.tvShow.getEndTime());

            showNode.tvShow=tvShowToBeAdded;
            showNode.next=clone.next;

            return showNode;
        }

        //Because ths returns a pointer to a TVShow object, there are potential privacy leaks here
        public TVShow getShow() {
            return tvShow;
        }

        public void setShow(TVShow show) {
            this.tvShow = show;
        }

        //Because this returns a pointer to a shownode object, there are potential privacy leaks here

        public ShowNode getNext() {
            return next;
        }

        public void setNext(ShowNode next) {
            this.next = next;
        }

        /**
         * Prints showNode details and info of the next node
         */
        public void displayNodeDetails() {
                System.out.println("TVShow Details(Current)\n" +
                        this.getShow() +
                        "\nNext node: " + this.getNext());
        }

    }

}