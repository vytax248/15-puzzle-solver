import java.util.ArrayList;
import java.util.Random;

public class Generator
{
    static boolean valid(int[] permutation)
{
    int inversions = 0;
    for(int i=0; i<15; i++)
    {
        for (int j=i+1; j<15; j++)
        {
            if(permutation[i]>permutation[j]) inversions++;
        }
    }
    return inversions % 2 == 0;
}
    public static State generateRandom()
    {
        int[] permutation;
        Random random = new Random();
        do
        {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i <= 15; i++)
            {
                list.add(i);
            }
            permutation = new int[16];
            for (int i = 0; i < 15; i++)
            {
                int rand = random.nextInt(list.size());
                permutation[i] = list.get(rand);
                list.remove(rand);
            }
            permutation[15] = 0;
        }
        while (!valid(permutation));

        return new State(permutation, 15);
    }

    public static State generateNear(int k)
    {
        State state = new State(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0}, 15);
        Random random = new Random();

        for(int i=0; i<k; i++)
        {
            State[] neighbors = state.getNeighbors();
            int rand;
            do
            {
                rand = random.nextInt(4);
            } while (neighbors[rand] == null);
            state = neighbors[rand];
        }

        return state;
    }
}
