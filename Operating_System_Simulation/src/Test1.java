import java.util.*;

// Main Application Class
public class Test1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("================================================================================================================================================================");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t********** OPERATING SYSTEM  PROJECT *********");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t DEVELOPED BY: \n \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  TAHA SHAHID \n");
        System.out.println("PROJECT TOPIC:OS SIMULATION CONSOLE BASED");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("================================================================================================================================================================");
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Process Management");
            System.out.println("2. File Management");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = getValidIntegerInput(scanner);

            switch (choice) {
                case 1:
                    new ProcessmanagmentConsole(scanner);
                    break;
                case 2:
                    new FileSystemConsole(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getValidIntegerInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }
}

// Process Management Class
class ProcessmanagmentConsole {

    private ArrayList<ProcessmanagmentConsole.Process> processes = new ArrayList<>();

    private int ids = 1; // Process ID counter
    private Scanner scanner = new Scanner(System.in);

    // Process class with ID
    private static class Process {
        private int id;
        private final int arrivalTime;
        private final int burstTime;
        private String status; // "Ready", "Running", "Blocked", "Suspended"
        private int priority;
        private int completionTime; // Completion time
        private int waitingTime; // Waiting time
        private int turnaroundTime; // Turnaround time
        private int remainingTime; // Remaining time
        private int startTime; // Start time
        private int finishTime; // Finish time

        public Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime; // Initialize remaining time
            this.status = "Ready";
            this.priority = priority;
            this.completionTime = 0; // Initialize completion time
            this.waitingTime = 0; // Initialize waiting time
            this.turnaroundTime = 0; // Initialize turnaround time
            this.startTime = -1; // Initialize start time
            this.finishTime = -1; // Initialize finish time
        }
    }

    public ProcessmanagmentConsole(Scanner scanner) {
        this.scanner = scanner;
        initialize();
        runConsole();
    }

    private void runConsole() {
        while (true) {
            System.out.println("1. Create a process");
            System.out.println("2. Destroy a process");
            System.out.println("3. Suspend a process");
            System.out.println("4. Process Scheduling");
            System.out.println("5. Resume a process");
            System.out.println("6. Block a process");
            System.out.println("7. Change process priority");
            System.out.println("8. Dispatch a process");
            System.out.println("9. Wakeup a process");
            System.out.println("10. Display processes");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    createProcess();
                    break;
                case 2:
                    displayProcesses(); // Show processes before destroying
                    destroyProcess();
                    break;
                case 3:
                    displayProcesses(); // Show processes before suspending
                    suspendProcess();
                    break;
                case 4:
                    processScheduling();
                    break;
                case 5:
                    displayProcesses(); // Show processes before resuming
                    resumeProcess();
                    break;
                case 6:
                    displayProcesses(); // Show processes before blocking
                    blockProcess();
                    break;
                case 7:
                    displayProcesses(); // Show processes before changing priority
                    changeProcessPriority();
                    break;
                case 8:
                    displayProcesses(); // Show processes before dispatching
                    dispatchProcess();
                    break;
                case 9:
                    displayProcesses(); // Show processes before waking up
                    wakeupProcess();
                    break;
                case 10:
                    displayProcesses();
                    break;
                case 11:
                    System.out.println("Exiting...");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createProcess() {
        System.out.print("Enter the number of processes you want to create: ");
        int processesInput = getValidIntegerInput(); // Get the number of processes

        for (int i = 0; i < processesInput; i++) {
            System.out.println("Creating Process " + (i + 1) + ":");

            System.out.print("Enter Arrival Time: ");
            int arrivalTime = getValidIntegerInput();

            System.out.print("Enter Burst Time: ");
            int burstTime = getValidIntegerInput();

            System.out.print("Enter Priority: ");
            int priority = getValidIntegerInput();

            processes.add(new ProcessmanagmentConsole.Process(ids++, arrivalTime, burstTime, priority));
            System.out.println("Process " + (i + 1) + " created successfully.");
        }
    }

    private void destroyProcess() {
        System.out.print("Enter Process ID to destroy: ");
        int processId = getValidIntegerInput();
        boolean processFound = false; // Flag to check if the process was found

        // Iterate through the processes to find the one to remove
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                processes.remove(process); // Remove the process
                System.out.println("Process " + processId + " destroyed.");
                processFound = true; // Set the flag to true
                break; // Exit the loop since we found and removed the process
            }
        }

        // If no process was found, print the error message
        if (!processFound) {
            System.out.println("Cannot destroy process, It does not exist.");
        }
    }

    private void suspendProcess() {
        System.out.print("Enter Process ID to suspend: ");
        int processId = getValidIntegerInput();
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                if (process.status.equals("Ready")) {
                    process.status = "Suspended";
                    System.out.println("Process " + processId + " suspended.");
                } else if (process.status.equals("Blocked")) {
                    process.status = "Suspended"; // Move from Blocked to Suspended
                    System.out.println("Process " + processId + " moved to suspended from blocked.");
                } else {
                    System.out.println("Process " + processId + " is already suspended.");
                }
                return;
            }
        }
        System.out.println("Process not found.");
    }

    private void resumeProcess() {
        System.out.print("Enter Process ID to resume: ");
        int processId = getValidIntegerInput();
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                if (process.status.equals("Suspended")) {
                    process.status = "Ready";
                    System.out.println("Process " + processId + " resumed.");
                } else if (process.status.equals("Blocked")) {
                    System.out.println("Process " + processId + " is blocked. Please wake it up first.");
                } else {
                    System.out.println("Process " + processId + " is not in a suspended state.");
                }
                return;
            }
        }
        System.out.println("Process not found.");
    }

    private void blockProcess() {
        System.out.print("Enter Process ID to block: ");
        int processId = getValidIntegerInput();
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                if (process.status.equals("Blocked")) {
                    System.out.println("Process " + processId + " is already blocked.");
                } else {
                    process.status = "Blocked";
                    System.out.println("Process " + processId + " blocked.");
                }
                return;
            }
        }
        System.out.println("Process not found.");
    }

    private void wakeupProcess() {
        System.out.print("Enter Process ID to wake up: ");
        int processId = getValidIntegerInput();
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                if (process.status.equals("Blocked")) {
                    process.status = "Ready";
                    System.out.println("Process " + processId + " woken up.");
                } else if (process.status.equals("Suspended")) {
                    System.out.println("Process " + processId + " is suspended. Please resume it first.");
                } else {
                    System.out.println("Process " + processId + " is not blocked.");
                }
                return;
            }
        }
        System.out.println("Process not found.");
    }

    private void dispatchProcess() {
        System.out.print("Enter Process ID to dispatch: ");
        int processId = getValidIntegerInput();
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                if (process.status.equals("Ready")) {
                    process.status = "Running";
                    System.out.println("Process " + processId + " dispatched.");
                } else {
                    System.out.println("Process " + processId + " is not in a ready state.");
                }
                return;
            }
        }
        System.out.println("Process not found.");
    }

    private void changeProcessPriority() {
        System.out.print("Enter Process ID to change priority: ");
        int processId = getValidIntegerInput();
        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.id == processId) {
                if (process.status.equals("Ready")) {
                    System.out.print("Enter new priority: ");
                    int newPriority = getValidIntegerInput();
                    process.priority = newPriority;
                    System.out.println("Process " + processId + " priority changed to " + newPriority + ".");
                } else {
                    System.out.println("Process " + processId + " is not in a ready state. Cannot change priority.");
                }
                return;
            }
        }
        System.out.println("Process not found.");
    }

    private void displayProcesses() {
        System.out.println("Process ID | Arrival Time | Burst Time | Status | Priority |");

        for (ProcessmanagmentConsole.Process process : processes) {
            System.out.printf("P%-7d | %-12d | %-10d | %-8s | %-10d |\n",
                    process.id, process.arrivalTime, process.burstTime,
                    process.status, process.priority);
        }
    }

    private void processScheduling() {
        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. FCFS");
        System.out.println("2. SJF");
        System.out.println("3. Round Robin");
        System.out.println("4. Non-Preemptive Priority Scheduling");
        System.out.println("5. Preemptive Priority Scheduling");
        System.out.println("6. SRTF");
        System.out.print("Choose an option: ");
        int choice = getValidIntegerInput();

        switch (choice) {
            case 1:
                performFCFS();
                break;
            case 2:
                performSJF();
                break;
            case 3:
                performRoundRobin();
                break;
            case 4:
                performNonPreemptivePriorityScheduling();
                break;
            case 5:
                performPreemptivePriorityScheduling();
                break;
            case 6:
                performSRTFScheduling();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void performFCFS() {
        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));
        int currentTime = 0;

        for (ProcessmanagmentConsole.Process process : processes) {
            if (process.arrivalTime > currentTime) {
                currentTime = process.arrivalTime;
            }
            process.completionTime = currentTime + process.burstTime;
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
            currentTime = process.completionTime;
        }
        printResults();
    }

    private void performSJF() {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int completedCount = 0;

        // Initialize remaining time for each process (Each process is assigned a remaining time, initialized to its burst time)
        for (ProcessmanagmentConsole.Process process : processes) {
            process.remainingTime = process.burstTime; // Reset remaining time
        }

        // List to hold executed processes for output
        List<ProcessmanagmentConsole.Process> executedProcesses = new ArrayList<>();
        // The loop runs until all processes are completed.
        while (completedCount < processes.size()) {
            int shortest = -1;
            int shortestBurst = Integer.MAX_VALUE;
           /* Logic: For each process:

Check if it has arrived (arrivalTime <= currentTime) and has remaining execution time (remainingTime > 0).
If true, compare its remainingTime with the current shortest burst time (shortestBurst).
The process with the shortest remainingTime is selected for execution.
If no process is ready (i.e., shortest == -1), the CPU remains idle, and currentTime is incremented.  */
            for (int i = 0; i < processes.size(); i++) {
                ProcessmanagmentConsole.Process process = processes.get(i);
                if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                    if (process.remainingTime < shortestBurst) {
                        shortestBurst = process.remainingTime;
                        shortest = i;
                    }
                }
            }


            if (shortest == -1) {
                // No process is ready, increment time
                currentTime++;
            } else {
                // Execute the shortest process
                //Its remainingTime is decremented.
                ProcessmanagmentConsole.Process selectedProcess = processes.get(shortest);
                selectedProcess.remainingTime--;
                //If the remainingTime of a process becomes 0, it has completed execution

                if (selectedProcess.remainingTime == 0) {
                    // Process has completed
                    selectedProcess.completionTime = currentTime + 1;
                    selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;
                    selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;

                    totalWaitingTime += selectedProcess.waitingTime;
                    totalTurnaroundTime += selectedProcess.turnaroundTime;
                    //Update totals for waiting and turnaround times, increment completedCount, and add the process to the list of executed processes.
                    completedCount++;
                    executedProcesses.add(selectedProcess); // Add to executed processes
                }

                currentTime++; // Increment time after processing
            }
        }
        printResults();
    }

    private void performRoundRobin() {
        // takes quantum time
        System.out.print("Enter Time Quantum: ");
        int quantum = getValidIntegerInput();
        List<ProcessmanagmentConsole.Process> executedProcesses = new LinkedList<>();
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Initialize remaining burst times
        List<Integer> remainingBurstTimes = new ArrayList<>();
        //Each process's remaining execution time (remainingTime) is initialized to its burst time.
        for (ProcessmanagmentConsole.Process process : processes) {
            remainingBurstTimes.add(process.burstTime); // Initialize remaining burst times
            process.remainingTime = process.burstTime; // Reset remaining time
        }

        boolean[] isInQueue = new boolean[processes.size()];

        while (true) {
            boolean allProcessesCompleted = true; // Flag to check if all processes are completed

            for (int i = 0; i < processes.size(); i++) {
                ProcessmanagmentConsole.Process process = processes.get(i);
                //It checks if the process has arrived (arrivalTime <= currentTime) and if it still has execution time left (remainingTime > 0).
                if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                    allProcessesCompleted = false; // At least one process is still running

                    // the CPU executes the process for either the full quantum or the remaining burst time, whichever is smaller.
                    int executeTime = Math.min(quantum, process.remainingTime);
                    if (process.startTime == -1) {
                        process.startTime = currentTime; // Set start time
                    }
                    currentTime += executeTime;
                    process.remainingTime -= executeTime;
                    //Handling Process Completion:
                    // If process completes
                    //If a process’s remainingTime becomes 0, it has completed execution
                    if (process.remainingTime == 0) {
                        process.finishTime = currentTime;
                        process.turnaroundTime = process.finishTime - process.arrivalTime;
                        process.waitingTime = process.turnaroundTime - process.burstTime;

                        totalWaitingTime += process.waitingTime;
                        totalTurnaroundTime += process.turnaroundTime;

                        executedProcesses.add(process);
                    }
                }
            }

            if (allProcessesCompleted) {
                break; // Exit the loop if all processes are completed
            }
        }

        printResults(); // Print results after scheduling
    }

    private void performNonPreemptivePriorityScheduling() {
        ArrayList<ProcessmanagmentConsole.Process> copiedList = new ArrayList<>(processes);
        // Sort processes by arrival time and then by priority
        copiedList.sort((p1, p2) -> {
            if (p1.arrivalTime != p2.arrivalTime) {
                return Integer.compare(p1.arrivalTime, p2.arrivalTime);
            }
            return Integer.compare(p1.priority, p2.priority);
        });

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        List<ProcessmanagmentConsole.Process> executedProcesses = new ArrayList<>();

        while (!copiedList.isEmpty()) {
            // Find the highest priority process that has arrived
            ProcessmanagmentConsole.Process highestPriorityProcess = null;

            for (ProcessmanagmentConsole.Process process : copiedList) {
                if (process.arrivalTime <= currentTime) {
                    if (highestPriorityProcess == null || process.priority < highestPriorityProcess.priority) {
                        highestPriorityProcess = process;
                    }
                }
            }

            if (highestPriorityProcess == null) {
                // If no process is ready, move time forward to the next process arrival
                currentTime = copiedList.get(0).arrivalTime;
                continue;
            }

            // Execute the highest priority process
            currentTime += highestPriorityProcess.burstTime;
            highestPriorityProcess.completionTime = currentTime;
            highestPriorityProcess.turnaroundTime = highestPriorityProcess.completionTime - highestPriorityProcess.arrivalTime;
            highestPriorityProcess.waitingTime = highestPriorityProcess.turnaroundTime - highestPriorityProcess.burstTime;

            totalWaitingTime += highestPriorityProcess.waitingTime;
            totalTurnaroundTime += highestPriorityProcess.turnaroundTime;

            executedProcesses.add(highestPriorityProcess);
            copiedList.remove(highestPriorityProcess); // Remove the completed process
        }
        printResults();

        // Output Results
        System.out.println("Non-Preemptive Priority Scheduling Results:");
        System.out.println("Process | Arrival Time | Burst Time | Priority | Start Time | Finish Time | Waiting Time | Turnaround Time");
        for (ProcessmanagmentConsole.Process process : executedProcesses) {
            System.out.printf("P%-7d | %-12d | %-10d | %-8d | %-10d | %-11d | %-12d | %-15d\n",
                    process.id, process.arrivalTime, process.burstTime, process.priority,
                    process.startTime, process.completionTime, process.waitingTime, process.turnaroundTime);
        }

        double avgWaitingTime = (double) totalWaitingTime / executedProcesses.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / executedProcesses.size();

        System.out.printf("\nAverage Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
    }

    private void performPreemptivePriorityScheduling() {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        List<ProcessmanagmentConsole.Process> executedProcesses = new ArrayList<>();

        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        // Initialize remaining time for each process
        for (ProcessmanagmentConsole.Process process : processes) {
            process.remainingTime = process.burstTime; // Reset remaining time
        }

        while (true) {
            boolean allCompleted = true; // Reset flag for each iteration
            ProcessmanagmentConsole.Process currentProcess = null;

            // Find the process with the highest priority that has arrived
            for (ProcessmanagmentConsole.Process process : processes) {
                if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                    allCompleted = false; // At least one process is still running
                    if (currentProcess == null || process.priority < currentProcess.priority) {
                        currentProcess = process;
                    }
                }
            }

            if (allCompleted) {
                break; // Exit if all processes are completed
            }

            if (currentProcess != null) {
                // Execute the current process for one time unit
                if (currentProcess.startTime == -1) {
                    currentProcess.startTime = currentTime; // Set start time
                }
                currentProcess.remainingTime--;
                currentTime++;

                // If the process is completed
                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.finishTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;

                    executedProcesses.add(currentProcess);
                }
            }
        }

        printResults();


    }

    private void performSRTFScheduling() {
        ArrayList<ProcessmanagmentConsole.Process> temp = new ArrayList<>(processes);
        temp.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime)); // Sort by arrival time

        int currentTime = 0;
        boolean[] completed = new boolean[temp.size()];
        int completedCount = 0;

        // Initialize remaining times
        for (ProcessmanagmentConsole.Process process : temp) {
            process.remainingTime = process.burstTime; // Assuming remainingTime is a field in Process
        }

        while (completedCount < temp.size()) {
            int shortestIndex = -1;
            int shortestRemainingTime = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time that has arrived
            for (int i = 0; i < temp.size(); i++) {
                ProcessmanagmentConsole.Process process = temp.get(i);
                if (!completed[i] && process.arrivalTime <= currentTime && process.remainingTime < shortestRemainingTime) {
                    shortestRemainingTime = process.remainingTime;
                    shortestIndex = i;
                }
            }

            if (shortestIndex == -1) {
                // No process is ready, increment time to the next process arrival
                currentTime++;
            } else {
                // Execute the process with the shortest remaining time
                ProcessmanagmentConsole.Process process = temp.get(shortestIndex);
                process.remainingTime--; // Decrement remaining time

                // If the process has completed its execution
                if (process.remainingTime == 0) {
                    process.completionTime = currentTime + 1;
                    process.turnaroundTime = process.completionTime - process.arrivalTime;
                    process.waitingTime = process.turnaroundTime - process.burstTime;
                    completed[shortestIndex] = true; // Mark process as completed
                    completedCount++;
                }
                currentTime++; // Increment time after processing
            }
        }
        printResults(); // Print results after scheduling
    }
    private void printResults() {
        System.out.println("| Process ID |\tArrival Time |\tBurst Time |\tCompletion Time |\tTurnaround Time |\tWaiting Time |");
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        for (ProcessmanagmentConsole.Process process : processes) {
            System.out.printf("\t%d\t\t\t\t%d\t\t\t\t%d\t\t\t\t%d\t\t\t\t\t%d\t\t\t\t\t%d%n",
                    process.id, process.arrivalTime, process.burstTime,
                    process.completionTime, process.turnaroundTime, process.waitingTime);

            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
        }

        int numberOfProcesses = processes.size(); // Assuming processes is an array or a collection with a size method
        double avgTurnaroundTime = (double) totalTurnaroundTime / numberOfProcesses;
        double avgWaitingTime = (double) totalWaitingTime / numberOfProcesses;

        System.out.printf("Average Turnaround Time: %.2f%n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f%n", avgWaitingTime);


    }

    private int getValidIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }

    private void initialize() {
        processes.add(new ProcessmanagmentConsole.Process(ids++, 0, 5, 3)); // Process 1: Arrival 0, Burst 5
        processes.add(new ProcessmanagmentConsole.Process(ids++, 1, 8, 1)); // Process 2: Arrival 1, Burst 8
        processes.add(new ProcessmanagmentConsole.Process(ids++, 2, 3, 4)); // Process 3: Arrival 2, Burst 3
        processes.add(new ProcessmanagmentConsole.Process(ids++, 3, 6, 2)); // Process 4: Arrival 3, Burst 6
        processes.add(new ProcessmanagmentConsole.Process(ids++, 5, 2, 5)); // Process 3: Arrival 2, Burst 3

    }
}

// File Management Class


// File Class
class File {
    private String name;
    private String content;
    private String permissions;
    private long size;

    public File(String name, String permissions) {
        this.name = name;
        this.content = "";
        this.permissions = permissions;
        this.size = 0;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void writeContent(String content) {
        this.content = content;
        this.size = content.length();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public long getSize() {
        return size;
    }
}

// Directory Class
class Directory {
    private String name;
    private Map<String, File> files;
    private Map<String, Directory> subDirectories;

    public Directory(String name) {
        this.name = name;
        this.files = new HashMap<>();
        this.subDirectories = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public Map<String, Directory> getSubDirectories() {
        return subDirectories;
    }

    public void addFile(String name, String permissions) {
        if (files.containsKey(name)) {
            throw new IllegalArgumentException("File already exists!");
        }
        files.put(name, new File(name, permissions));
    }

    public void removeFile(String name) {
        if (!files.containsKey(name)) {
            throw new IllegalArgumentException("File does not exist!");
        }
        files.remove(name);
    }

    public void addDirectory(String name) {
        if (subDirectories.containsKey(name)) {
            throw new IllegalArgumentException("Directory already exists!");
        }
        subDirectories.put(name, new Directory(name));
    }

    public void removeDirectory(String name) {
        if (!subDirectories.containsKey(name)) {
            throw new IllegalArgumentException("Directory does not exist!");
        }
        subDirectories.remove(name);
    }
}

// FileSystem Class
class FileSystem {
    private Directory root;

    public FileSystem() {
        root = new Directory("root");
    }

    public Directory getRoot() {
        return root;
    }

    public void createFile(String path, String name, String permissions) {
        Directory directory = navigateToDirectory(path);
        directory.addFile(name, permissions);
    }

    public void deleteFile(String path, String name) {
        Directory directory = navigateToDirectory(path);
        directory.removeFile(name);
    }

    public void writeFile(String path, String name, String content) {
        Directory directory = navigateToDirectory(path);
        if (!directory.getFiles().containsKey(name)) {
            throw new IllegalArgumentException("File does not exist!");
        }
        File file = directory.getFiles().get(name);
        file.writeContent(content);
    }

    public String readFile(String path, String name) {
        Directory directory = navigateToDirectory(path);
        if (!directory.getFiles().containsKey(name)) {
            throw new IllegalArgumentException("File does not exist!");
        }
        return directory.getFiles().get(name).getContent();
    }

    public void createDirectory(String path, String name) {
        Directory directory = navigateToDirectory(path);
        directory.addDirectory(name);
    }

    public void deleteDirectory(String path, String name) {
        Directory directory = navigateToDirectory(path);
        directory.removeDirectory(name);
    }

    public void moveFile(String sourcePath, String destinationPath, String fileName) {
        Directory sourceDirectory = navigateToDirectory(sourcePath);
        Directory destinationDirectory = navigateToDirectory(destinationPath);
        if (!sourceDirectory.getFiles().containsKey(fileName)) {
            throw new IllegalArgumentException("File does not exist in the source directory!");
        }
        File file = sourceDirectory.getFiles().remove(fileName); // Remove from source
        destinationDirectory.getFiles().put(fileName, file); // Add to destination
    }

    // Show files in the given directory
    public List<String> showFiles(String path) {
        Directory directory = navigateToDirectory(path);
        List<String> fileNames = new ArrayList<>();
        for (File file : directory.getFiles().values()) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    private Directory navigateToDirectory(String path) {
        String[] parts = path.split("/");
        Directory current = root;
        for (String part : parts) {
            if (part.isEmpty()) continue;
            if (!current.getSubDirectories().containsKey(part)) {
                throw new IllegalArgumentException("Invalid path: " + path);
            }
            current = current.getSubDirectories().get(part);
        }
        return current;
    }
}

// FileSystemConsole Class
class FileSystemConsole {
    private FileSystem fileSystem;
    private Scanner scanner;

    public FileSystemConsole(Scanner scanner) {
        this.scanner = scanner;
        this.fileSystem = new FileSystem();
        runConsole();
    }

    private void runConsole() {
        while (true) {
            System.out.println("File Management Menu:");
            System.out.println("1. Create File");
            System.out.println("2. Delete File");
            System.out.println("3. Write to File");
            System.out.println("4. Read File");
            System.out.println("5. Create Directory");
            System.out.println("6. Delete Directory");
            System.out.println("7. Show Files in Directory");
            System.out.println("8. Move File");
            System.out.println("9. Exit to Main Menu");
            System.out.print("Choose an option: ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    createFile();
                    break;
                case 2:
                    deleteFile();
                    break;
                case 3:
                    writeFile();
                    break;
                case 4:
                    readFile();
                    break;
                case 5:
                    createDirectory();
                    break;
                case 6:
                    deleteDirectory();
                    break;
                case 7:
                    showFiles();
                    break;
                case 8:
                    moveFile();
                    break;
                case 9:
                    System.out.println("Exiting to Main Menu...");
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createFile() {
        System.out.print("Enter directory path: ");
        String path = scanner.nextLine();
        System.out.print("Enter file name: ");
        String name = scanner.nextLine();
        System.out.print("Enter file permissions: ");
        String permissions = scanner.nextLine();
        try {
            fileSystem.createFile(path, name, permissions);
            System.out.println("File " + name + " created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteFile() {
        System.out.print("Enter directory path: ");
        String path = scanner.nextLine();
        System.out.print("Enter file name: ");
        String name = scanner.nextLine();
        try {
            fileSystem.deleteFile(path, name);
            System.out.println("File " + name + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeFile() {
        System.out.print("Enter directory path: ");
        String path = scanner.nextLine();
        System.out.print("Enter file name: ");
        String name = scanner.nextLine();
        System.out.print("Enter content to write: ");
        String content = scanner.nextLine();
        try {
            fileSystem.writeFile(path, name, content);
            System.out.println("Content written to file " + name + " successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFile() {
        System.out.print("Enter directory path: ");
        String path = scanner.nextLine();
        System.out.print("Enter file name: ");
        String name = scanner.nextLine();
        try {
            String content = fileSystem.readFile(path, name);
            System.out.println("Content of file " + name + ": " + content);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDirectory() {
        System.out.print("Enter parent directory path: ");
        String path = scanner.nextLine();
        System.out.print("Enter directory name: ");
        String name = scanner.nextLine();
        try {
            fileSystem.createDirectory(path, name);
            System.out.println("Directory " + name + " created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteDirectory() {
        System.out.print("Enter parent directory path: ");
        String path = scanner.nextLine();
        System.out.print("Enter directory name: ");
        String name = scanner.nextLine();
        try {
            fileSystem.deleteDirectory(path, name);
            System.out.println("Directory " + name + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showFiles() {
        System.out.print("Enter directory path:");
        String path = scanner.nextLine();
        try {
            List<String> files = fileSystem.showFiles(path);
            System.out.println("Files in directory " + path + ": " + files);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void moveFile() {
        System.out.print("Enter source directory path: ");
        String sourcePath = scanner.nextLine();
        System.out.print("Enter destination directory path: ");
        String destinationPath = scanner.nextLine();
        System.out.print("Enter file name to move: ");
        String fileName = scanner.nextLine();
        try {
            fileSystem.moveFile(sourcePath, destinationPath, fileName);
            System.out.println("File " + fileName + " moved from " + sourcePath + " to " + destinationPath + " successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private int getValidIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }
}