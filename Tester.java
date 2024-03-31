public class Tester
{
    public static void main(String[] args)
    {
        final int k = 50;
        int numberOfStates = 0, pathLength = 0;
        for(int i=0; i<k; i++)
        {
            Result result = Main.solve(Generator.generateRandom());
            numberOfStates += result.numberOfStates;
            pathLength += result.path.size();
            System.out.println(i);
        }
        System.out.println("średnia liczba odwiedzonych stanów: " + numberOfStates / k);
        System.out.println("średnia długość ścieżki: " + pathLength / k);
    }
}
