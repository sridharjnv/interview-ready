package DSA.Greedy;

import java.util.*;

class Item {
    int value, weight;
    Item(int v, int w) {
        value = v;
        weight = w;
    }
}
public class FractionalKnapsack {
    public static void main(String[] args) {
        int[] value = {60, 100, 120};
        int[] weight = {10, 20, 30};
        int capacity = 50;
        double maxValue = getMaxValue(value, weight, capacity);
        System.out.println("Maximum value: " + maxValue);
    }
    public static double getMaxValue(int[] value, int[] weight, int capacity) {
        int n = value.length;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            items.add(new Item(value[i], weight[i]));
        }
        // Sort by value/weight ratio (descending)
        Collections.sort(items, (a, b) ->
                Double.compare((double)b.value / b.weight, (double)a.value / a.weight)
        );
        double totalValue = 0.0;
        for (Item item : items) {
            if (capacity >= item.weight) {
                // take full item
                capacity -= item.weight;
                totalValue += item.value;
            } else {
                // take fraction
                totalValue += ((double)item.value / item.weight) * capacity;
                break;
            }
        }
        return totalValue;
    }
}
