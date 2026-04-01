package DSA.Greedy;

import java.util.*;

class Activity {
    int start, end;
    Activity(int s, int e) {
        start = s;
        end = e;
    }
}
public class ActivitySelection {
    public static void main(String[] args) {
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end   = {2, 4, 6, 7, 9, 9};

        int result = maxActivities(start, end);
        System.out.println("Maximum activities: " + result);
    }
    public static int maxActivities(int[] start, int[] end) {
        int n = start.length;
        List<Activity> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Activity(start[i], end[i]));
        }
        // Sort by end time
        Collections.sort(list, (a, b) -> a.end - b.end);
        int count = 1; // pick first activity
        int lastEnd = list.get(0).end;

        for (int i = 1; i < n; i++) {
            if (list.get(i).start >= lastEnd) {
                count++;
                lastEnd = list.get(i).end;
            }
        }
        return count;
    }
}
