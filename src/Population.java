import java.util.PriorityQueue;
import java.util.Comparator;

import random;

public class Population extends PriorityQueue<Individual>{
    public int size;

    public Population(Comparator<Individual> comp) {
        super(comp);
    }

    public void breed(int size) {
        //Split the population into four classes based on fitness
        int k = size / 4;
        Array top = Population[0:k];
        Array topMid = Population[k + 1:(2 * k)];
        Array BotMid = Population[(2 * k):(3 * k)];
        Array Bottom = Population[(3 * k + 1):(4 * k)];

        //Mix up each class
        top.shuffle();
        topMid.shuffle();
        BotMid.shuffle();
        Bottom.shuffle();

        //Pair off partners within each class
        //Crossover chromosomes between partners
        //New creature is Made using new Chromosome structure formed
    }
}
