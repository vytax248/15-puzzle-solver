import java.util.ArrayList;

public class Result
{
    ArrayList<State> path;
    int numberOfStates;

    Result(ArrayList<State> path, int numberOfStates)
    {
        this.path = path;
        this.numberOfStates = numberOfStates;
    }
}
