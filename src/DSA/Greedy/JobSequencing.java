package DSA.Greedy;

import java.util.*;

class Job {
    int id, deadline, profit;
    Job(int id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}
public class JobSequencing {
    public static void main(String[] args) {
        Job[] jobs = {
                new Job(1, 2, 100),
                new Job(2, 1, 19),
                new Job(3, 2, 27),
                new Job(4, 1, 25),
                new Job(5, 3, 15)
        };
        scheduleJobs(jobs);
    }
    public static void scheduleJobs(Job[] jobs) {
        // Sort jobs by profit (descending)
        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);
        int maxDeadline = 0;
        for (Job job : jobs) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        int[] slot = new int[maxDeadline + 1];
        Arrays.fill(slot, -1);

        int totalProfit = 0;
        for (Job job : jobs) {
            // Try to schedule at latest possible slot
            for (int j = job.deadline; j > 0; j--) {
                if (slot[j] == -1) {
                    slot[j] = job.id;
                    totalProfit += job.profit;
                    break;
                }
            }
        }
        System.out.println("Scheduled Jobs:");
        for (int i = 1; i <= maxDeadline; i++) {
            if (slot[i] != -1) {
                System.out.print("Job" + slot[i] + " ");
            }
        }
        System.out.println("\nTotal Profit: " + totalProfit);
    }
}
