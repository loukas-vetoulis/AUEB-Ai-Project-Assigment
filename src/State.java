import java.util.ArrayList;

public class State implements Comparable<State>
{
    private int priests_left;
    private int priests_right;
    private int cannibals_left;
    private int cannibals_right;
    private Boolean boat_position; // False = Left, True = Right.
    
    private int boat_priests;      
    private int boat_cannibals;

    // Counter gia diadromes 
    private int boat_counter;
    // max boat trips->K
    static int boat_counter_MAX;

    // Xwretuikothta Barkas->M
    static int boat_space;

    //heuristic score
    private int score;

    //total score =g(n)+h(n)
    private int total_score;

    private State father = null;

    // constructot arxikhs katastashs 
    State(int population, int boat_space, int boat_counter_MAX)
    {
        this.priests_left = population;
        this.cannibals_left = population;
        this.boat_space = boat_space;
        this.boat_counter_MAX = boat_counter_MAX;
        this.boat_cannibals = this.boat_priests = this.priests_right = this.cannibals_right = 0;
        this.boat_position = false;
    }

    // constructor for creating copy of the state.
    State(int priests_left, int priests_right, int boat_priests, int cannibals_left, int cannibals_right, int boat_cannibals, Boolean boat_position, int boat_counter)
    {
        this.setPriestsLeft(priests_left);
        this.setPriestsRight(priests_right);
        this.setCannibalsLeft(cannibals_left);
        this.setCannibalsRight(cannibals_right);
        this.setBoatPosition(boat_position);
        this.setBoatCounter(boat_counter);
        this.setBoatPriests(boat_priests);
        this.setBoatCannibbals(boat_cannibals);
    }

// Prints everything the user needs to know about each  state
    public String print()
    {
        String string_pos;
        if (boat_position == false) // Turn boat_position into string to use bellow
        {
            string_pos = "Left";
        }
        else
        {
            string_pos = "Right";
        }
    
        return String.format(
            "-------------------------------------\n" +
            "Priests Left: %d\n" +
            "Priests Right: %d\n" +
            "Cannibals Left: %d\n" +
            "Cannibals Right: %d\n" +
            "Boat Position : %s\n" +
            "Boat Counter: %d\n" +
            "Boat cannibals: %d\n" +
            "Boat priests: %d\n" +
            "-------------------------------------",
            priests_left, 
            priests_right, 
            cannibals_left, 
            cannibals_right,
            string_pos,  
            boat_counter,
            boat_cannibals,
            boat_priests
        );
    }

    // Getter and Setter for priests_left

    public int getPriestsLeft() {
        return priests_left;
    }

    public void setPriestsLeft(int priests_left) {
        this.priests_left = priests_left;
    }

    // Getter and Setter for priests_right
    public int getPriestsRight() {
        return priests_right;
    }

    public void setPriestsRight(int priests_right) {
        this.priests_right = priests_right;
    }

    // Getter and Setter for boat_priests
    public int getBoatPriests() {
        return boat_priests;
    }
    
    public void setBoatPriests(int boat_priests) {
        this.boat_priests = boat_priests;
    }

    // Getter and Setter for cannibals_left
    public int getCannibalsLeft() {
        return cannibals_left;
    }

    public void setCannibalsLeft(int cannibals_left) {
        this.cannibals_left = cannibals_left;
    }

    // Getter and Setter for cannibals_right
    public int getCannibalsRight() {
        return cannibals_right;
    }

    public void setCannibalsRight(int cannibals_right) {
        this.cannibals_right = cannibals_right;
    }

    // Getter and Setter for boat_cannibals
    public int getBoatCannibals() {
        return boat_cannibals;
    }
    
    public void setBoatCannibbals(int boat_cannibals) {
        this.boat_cannibals = boat_cannibals;
    }

    // Getter and Setter for boat_position
    public Boolean isBoatPosition() {
        return boat_position;
    }

    public void setBoatPosition(Boolean boat_position) {
        this.boat_position = boat_position;
    }

    // Getter and Setter for boat_counter
    public int getBoatCounter() {
        return boat_counter;
    }
    
    public void setBoatCounter(int boat_counter) {
        this.boat_counter = boat_counter;
    }

    // Getter and Setter for score
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    // Getter and Setter for total score
    public int getTotalScore()
    {
        return total_score;
    }

    public void setTotalScore(int heuristic,int boat_counter)
    {
        total_score=heuristic+boat_counter;
    }

    public State getFather()
	{
        return this.father;
    }

    public void setFather(State father)
	{
        this.father = father;
    }

    // Heuristic function (η ίδια απο την άσκηση μελέτης 4.3)
    private void evaluate()
    {
        if((boat_position == true) & ((priests_left+cannibals_left) > 0)) 
        {
            setScore(2*(priests_left+cannibals_left));
        }else if((boat_position == false) & ((priests_left+cannibals_left) == 1))
        {
            setScore(1);
        }else if((boat_position == false) & ((priests_left+cannibals_left) > 1)) 
        {
            setScore(2*(priests_left+cannibals_left)-3);
        }else
        {
            setScore(0);
        }
    }

    //  Is valid function checks if a state is acceptaptable if we follow the problems rules
    private boolean isValid() {
        if (this.isFinal()) return true;
    
        // Validate left side
        if (priests_left < cannibals_left && priests_left > 0) return false;
    
        // Validate right side
        if (priests_right < cannibals_right && priests_right > 0) return false;
    
        if(boat_cannibals>boat_priests & boat_priests>0) return false;

        // Ensure that boat is not empty and that there arent more passengers than boat_space
        int totalInBoat = boat_priests + boat_cannibals;
        if (totalInBoat > boat_space || totalInBoat <= 0) return false; 
    
        // Make sure we havent surpassed max allowed trips
        return boat_counter <= boat_counter_MAX;
    }

    // Checks if a state is final
    public boolean isFinal()
	{
        // If left side is empty we are done
		if((priests_left==0) & (cannibals_left==0))
        {
            return true;
        }else
        {
            return false;
        }
	}
    
    //  Moves passengers from left side to right 
    private void FromLeftToRight(int priests, int cannibals)
    {
        //Move priests
        priests_left -= priests;
        boat_priests = priests;
        priests_right += priests;

        //Move cannibals
        cannibals_left -= cannibals;
        boat_cannibals = cannibals;
        cannibals_right += cannibals;
    }

    // Moves passengers from right to left
    private void FromRightToLeft(int priests, int cannibals)
    {
        // Moves priests
        priests_left += priests;
        boat_priests = priests;
        priests_right-= priests;

        // Moves cannibals
        cannibals_left += cannibals;
        boat_cannibals = cannibals;
        cannibals_right -= cannibals;
    }

    //Moves the boat to the other side
    private void MoveBoat()
    {
        boat_position = !boat_position;
        boat_counter+=1;
    }

    

    // Produces all possible child states from our current one and decides which ones are valid so that we can consider them for the problem
    ArrayList<State> getChildren() {
        ArrayList<State> children = new ArrayList<>();
        
        if (!boat_position) { // Boat is on the left
            for (int priests = 0; priests <= Math.min(boat_space, priests_left); priests++) {
                for (int cannibals = 0; cannibals <= Math.min(boat_space - priests, cannibals_left); cannibals++) { // Take all the possible combinations of priests and cannibals
                    if (priests + cannibals > 0) { // Must move at least one person
                        State child = new State(this.priests_left, this.priests_right, this.boat_priests, this.cannibals_left, this.cannibals_right, this.boat_cannibals, this.boat_position, this.boat_counter); // Make copy state
                        child.FromLeftToRight(priests, cannibals);
                        child.MoveBoat(); // Move boat with passengets
                        if (child.isValid()) { // check if child is valid (if not discard it)
                            child.evaluate();
                            child.setTotalScore(child.getScore(), child.getBoatCounter());
                            child.setFather(this);
                            children.add(child);
                        }
                    }
                }
            }
        } else { // Boat is on the right
            for (int priests = 0; priests <= Math.min(boat_space, priests_right); priests++) {
                for (int cannibals = 0; cannibals <= Math.min(boat_space - priests, cannibals_right); cannibals++) { // Take all the possible combinations of priests and cannibals
                    if (priests + cannibals > 0) { // Must move at least one person
                        State child = new State(this.priests_left, this.priests_right, this.boat_priests, this.cannibals_left, this.cannibals_right, this.boat_cannibals, this.boat_position, this.boat_counter); // Make copy state
                        child.FromRightToLeft(priests, cannibals);
                        child.MoveBoat(); // Move boat with passengets
                        if (child.isValid()) { // check if child is valid (if not discard it)
                            child.evaluate();
                            child.setTotalScore(child.getScore(), child.getBoatCounter());
                            child.setFather(this);
                            children.add(child);
                        }
                    }
                }
            }
        }
        return children;
    }

    @Override
    public int compareTo(State other) {
        // Compare the states based on totalScore
        return Integer.compare(this.total_score, other.total_score);
    }

    @Override
    public int hashCode() {
        // Use a prime number (like 31) to multiply each field to reduce the likelihood of collisions
        int result = 17; // Arbitrary non-zero constant
        result = 31 * result + priests_left;
        result = 31 * result + priests_right;
        result = 31 * result + cannibals_left;
        result = 31 * result + cannibals_right;
        result = 31 * result + (boat_position ? 1 : 0); // true becomes 1, false becomes 0
        result = 31 * result + boat_counter;
        return result;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;  // Check if it's the same object reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;  // Check if obj is null or not the same class
        }

        State other = (State) obj;  // Typecast to State to compare fields

        // Compare all relevant fields for equality
        return this.priests_left == other.priests_left &&
               this.priests_right == other.priests_right &&
               this.cannibals_left == other.cannibals_left &&
               this.cannibals_right == other.cannibals_right &&
               this.boat_position.equals(other.boat_position) &&  // Compare Boolean field
               this.boat_counter == other.boat_counter;
    }

}
