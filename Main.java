import java.util.*;

public class Main {
    static int calculateInversionDistance(int[] puzzle) {
        int inversionCount = 0;
        for (int i = 0; i < puzzle.length - 1; i++) {
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[i] != 0 && puzzle[j] != 0) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    static int manhattanInversionDistance(State state) {
        int inversionDistance = calculateInversionDistance(state.permutation);
        int manhattanDistance = manhattan(state);
        return inversionDistance + manhattanDistance;
    }
    static int manhattan(State state)
    {
        int sum = 0;
        for(int i=0; i<16; i++)
        {
            if(state.permutation[i] == 0)
                continue;
            sum += Math.abs(((state.permutation[i]-1)%4) - (i%4));
            sum += Math.abs(((state.permutation[i]-1)/4) - (i/4));
        }
        return sum;
    }

    static int misplaced(State state)
    {
        int count = 0;
        for(int i=0; i<16; i++)
        {
            if((state.permutation[i] != (i+1)%16) && (state.permutation[i] != 0))
                count++;
        }
        return count;
    }

    static int manhattanLinearConflict(State state)
    {
        int sum = manhattan(state);
        for(int i=0; i<16; i+=4)
        {
            if(state.permutation[i] > state.permutation[i+1] && state.permutation[i+1] > 0) sum += 2;
            if(state.permutation[i] > state.permutation[i+2] && state.permutation[i+2] > 0) sum += 2;
            if(state.permutation[i] > state.permutation[i+3] && state.permutation[i+3] > 0) sum += 2;
            if(state.permutation[i+1] > state.permutation[i+2] && state.permutation[i+2] > 0) sum += 2;
            if(state.permutation[i+1] > state.permutation[i+3] && state.permutation[i+3] > 0) sum += 2;
            if(state.permutation[i+2] > state.permutation[i+3] && state.permutation[i+3] > 0) sum += 2;
        }
        for(int i=0; i<4; i++)
        {
            if(state.permutation[i] > state.permutation[i+4] && state.permutation[i+4] > 0) sum += 2;
            if(state.permutation[i] > state.permutation[i+8] && state.permutation[i+8] > 0) sum += 2;
            if(state.permutation[i] > state.permutation[i+12] && state.permutation[i+12] > 0) sum += 2;
            if(state.permutation[i+4] > state.permutation[i+8] && state.permutation[i+8] > 0) sum += 2;
            if(state.permutation[i+4] > state.permutation[i+12] && state.permutation[i+12] > 0) sum += 2;
            if(state.permutation[i+8] > state.permutation[i+12] && state.permutation[i+12] > 0) sum += 2;
        }
        return sum;
    }

    static int h(State state)
    {
        return manhattanLinearConflict(state);
    }

    static Result solve(State start)
    {
        State goal = new State(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0}, 15);

        ArrayList<State> path = null;

        int numberOfStates = 0;

        Map<State, State> cameFrom = new HashMap<>();

        Map<Long, Integer> gScore = new HashMap<>();
        gScore.put(start.code, 0);

        Map<Long, Integer> fScore = new HashMap<>();
        fScore.put(start.code, h(start));

        PriorityQueue<Long> openSet = new PriorityQueue<>(Comparator.comparingInt(s -> fScore.getOrDefault(s, 1000000)));
        openSet.add(start.code);

        while (!openSet.isEmpty())
        {
            State current = new State(openSet.poll());
            numberOfStates++;
            //System.out.println(numberOfStates + " " + h(current));
            if(current.equals(goal))
            {
                path = new ArrayList<>();
                path.add(current);
                while(cameFrom.containsKey(current))
                {
                    current = cameFrom.get(current);
                    path.add(0, current);
                }
                break;
            }
            State[] neighbors = current.getNeighbors();
            for(int i=0; i<4; i++)
            {
                State neighbor = neighbors[i];
                if(neighbor == null)
                    continue;
                int tentative_gScore = gScore.get(current.code) + 1;
                if(!gScore.containsKey(neighbor.code) || tentative_gScore < gScore.get(neighbor.code))
                {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor.code, tentative_gScore);
                    fScore.put(neighbor.code, tentative_gScore + h(neighbor));
                    //if(!openSet.contains(neighbor.code))
                    {
                        //openSet.remove(neighbor);
                        openSet.add(neighbor.code);
                    }
                }
            }
        }
        return new Result(path, numberOfStates);
    }

    public static void main(String[] args)
    {
        State start = Generator.generateRandom();
        //State start = Generator.generateNear(100);
        /*State start = new State(new int[]{11, 4, 7,  3,
                13, 5, 6,  2,
                12, 9, 11, 10,
                8, 14, 15, 0}, 15);*/

        Result result = solve(start);
        ArrayList<State> path = result.path;

        if(path == null)
            System.out.println("error");
        else
        {
            for(State state : path)
            {
                state.print();
            }
            System.out.println("liczba odwiedzonych stanów: " + result.numberOfStates);
            System.out.println("liczba kroków: " + path.size());
        }
    }
}
