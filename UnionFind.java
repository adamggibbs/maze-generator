public class UnionFind{

    //makeSet(Cell) -- takes in a cell and creates a set which is an LLAddOnly object containing that cell
    //make it static so it can be accessed without an instance of UnionFind
    public static void makeSet(Cell cell){

        //create a new LLAddOnly object
        LLAddOnly set = new LLAddOnly();

        //add the cell to the new LLAddOnly
        set.add(cell);

        //return the set
        //return set;

    } //makeSet()


    //find(Cell) -- takes in a cell and returns the set it's a part of
    //make it static so it can be accessed without an instance of UnionFind
    public static LLAddOnly find(Cell cell){

        //if the cell is not part of a set, create a set for it
        if(cell.head == null){
            makeSet(cell);
        }

        //access the cell's set via its header pointer and return it
        LLAddOnly header = cell.head;
        return header;

    } //find()


    //union(Cell,Cell) -- takes in two cells and joins the cells' groups
    //make it static so it can be accessed without an instance of UnionFind
    public static void union(Cell one, Cell two){

        //store the cell one's set as 'set'
        LLAddOnly set = find(one);

        //get the set that cell two is a part of and store the 'first' cell as the currentCell 
        Cell currentCell = find(two).first;

        //loop through cell two's set and add cells to cell one's set
        while(currentCell != null){
            
            //store currentCell as a temp so that you can access the correct '.next' and add the correct cell to cell one's set
            Cell temp = currentCell;
            currentCell = temp.next;
            set.add(temp); 

        } //while there are cells in set 2
        
    } //union()

}