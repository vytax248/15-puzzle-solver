import java.util.ArrayList;
import java.util.Objects;

public class State
{
    int[] permutation;
    public int index;

    public long code = 0;

    public State(int[] permutation, int indexOfEmpty)
    {
        this.permutation = permutation;
        this.index = indexOfEmpty;
        for(int i=0; i<16; i++)
        {
            code <<= 4;
            code |= permutation[i];
        }
    }

    public State(long code)
    {
        this.code = code;
        int[] permutation = new int[16];
        int indexOfEmpty = 0;
        for(int i=15; i>=0; i--)
        {
            permutation[i] = (int) (code & 15);
            if(permutation[i] == 0)
                indexOfEmpty = i;
            code >>= 4;
        }
        this.permutation = permutation;
        this.index = indexOfEmpty;
    }

    public void print()
    {
        for(int i=0; i<16; i++)
        {
            if(permutation[i] == 0)
            {
                System.out.print("   ");
                if((i+1)%4 == 0) System.out.println();
                continue;
            }
            if(permutation[i] < 10) System.out.print(" ");
            System.out.print(permutation[i] + " ");
            if((i+1)%4 == 0) System.out.println();
        }
        System.out.println();System.out.println();
    }

    public State[] getNeighbors()
    {
        State[] neighbors = new State[4];

        // left
        if(index % 4 != 3)
        {
            int[] newPermutation = permutation.clone();
            newPermutation[index] = newPermutation[index+1];
            newPermutation[index+1] = 0;
            neighbors[0] = new State(newPermutation, index+1);
        }

        // right
        if(index % 4 != 0)
        {
            int[] newPermutation = permutation.clone();
            newPermutation[index] = newPermutation[index-1];
            newPermutation[index-1] = 0;
            neighbors[1] = new State(newPermutation, index-1);
        }

        // up
        if(index < 12)
        {
            int[] newPermutation = permutation.clone();
            newPermutation[index] = newPermutation[index+4];
            newPermutation[index+4] = 0;
            neighbors[2] = new State(newPermutation, index+4);
        }

        // down
        if(index > 3)
        {
            int[] newPermutation = permutation.clone();
            newPermutation[index] = newPermutation[index-4];
            newPermutation[index-4] = 0;
            neighbors[3] = new State(newPermutation, index-4);
        }

        return neighbors;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        State state = (State) obj;
        return code == state.code;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(code);
    }
}
