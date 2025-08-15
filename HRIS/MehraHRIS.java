// ====================================================================================
// MEHRA SOFTWARE COMPANY - HUMAN RESOURCE INFORMATION SYSTEM (HRIS)
// ====================================================================================
// This system demonstrates OOP principles and Design Patterns:
// 1. Singleton Pattern - HRISManager, DatabaseConnection, Logger
// 2. Factory Pattern - EmployeeFactory
// 3. Strategy Pattern - SalaryCalculationStrategy
// 4. Observer Pattern - NotificationSystem
// ====================================================================================

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ====================================================================================
// ABSTRACT BASE CLASSES AND INTERFACES (Demonstrating Abstraction)
// ====================================================================================

/**
 * Abstract Employee class demonstrating Abstraction and Encapsulation
 * Serves as base class for all employee types
 */
abstract class Employee {
    // Encapsulated fields (private access)
    private String employeeId;
    private String name;
    private String email;
    private String department;
    private LocalDate joiningDate;
    protected double baseSalary; // Protected for inheritance
    
    // Constructor
    public Employee(String employeeId, String name, String email, String department, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.baseSalary = baseSalary;
        this.joiningDate = LocalDate.now();
    }
    
    // Abstract method - must be implemented by subclasses (Abstraction)
    public abstract String getEmployeeType();
    public abstract double calculateBonus();
    
    // Concrete methods with proper encapsulation (Getters/Setters)
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public double getBaseSalary() { return baseSalary; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Type: %s, Department: %s, Salary: $%.2f", 
                           employeeId, name, getEmployeeType(), department, baseSalary);
    }
}

/**
 * Interface for notification observers (Observer Pattern)
 */
interface NotificationObserver {
    void update(String message);
}

/**
 * Interface for salary calculation strategies (Strategy Pattern)
 */
interface SalaryCalculationStrategy {
    double calculateTotalSalary(Employee employee);
    String getStrategyName();
}

// ====================================================================================
// CONCRETE EMPLOYEE CLASSES (Demonstrating Inheritance and Polymorphism)
// ====================================================================================

/**
 * Developer class extending Employee (Inheritance)
 */
class Developer extends Employee {
    private String programmingLanguage;
    private int experienceYears;
    
    public Developer(String employeeId, String name, String email, String department, 
                    double baseSalary, String programmingLanguage, int experienceYears) {
        super(employeeId, name, email, department, baseSalary);
        this.programmingLanguage = programmingLanguage;
        this.experienceYears = experienceYears;
    }
    
    // Polymorphism - overriding abstract methods
    @Override
    public String getEmployeeType() {
        return "Developer";
    }
    
    @Override
    public double calculateBonus() {
        // Developers get bonus based on experience
        return baseSalary * (experienceYears * 0.05);
    }
    
    // Specific methods for Developer
    public String getProgrammingLanguage() { return programmingLanguage; }
    public int getExperienceYears() { return experienceYears; }
    public void setProgrammingLanguage(String language) { this.programmingLanguage = language; }
    public void setExperienceYears(int years) { this.experienceYears = years; }
}

/**
 * Manager class extending Employee (Inheritance)
 */
class Manager extends Employee {
    private int teamSize;
    private List<String> managedProjects;
    
    public Manager(String employeeId, String name, String email, String department, 
                  double baseSalary, int teamSize) {
        super(employeeId, name, email, department, baseSalary);
        this.teamSize = teamSize;
        this.managedProjects = new ArrayList<>();
    }
    
    // Polymorphism - overriding abstract methods
    @Override
    public String getEmployeeType() {
        return "Manager";
    }
    
    @Override
    public double calculateBonus() {
        // Managers get bonus based on team size and projects
        return baseSalary * (teamSize * 0.08) + (managedProjects.size() * 1000);
    }
    
    // Specific methods for Manager
    public int getTeamSize() { return teamSize; }
    public List<String> getManagedProjects() { return managedProjects; }
    public void setTeamSize(int size) { this.teamSize = size; }
    public void addProject(String project) { this.managedProjects.add(project); }
}

/**
 * QA Engineer class extending Employee (Inheritance)
 */
class QAEngineer extends Employee {
    private String testingTools;
    private int bugsFound;
    
    public QAEngineer(String employeeId, String name, String email, String department, 
                     double baseSalary, String testingTools) {
        super(employeeId, name, email, department, baseSalary);
        this.testingTools = testingTools;
        this.bugsFound = 0;
    }
    
    // Polymorphism - overriding abstract methods
    @Override
    public String getEmployeeType() {
        return "QA Engineer";
    }
    
    @Override
    public double calculateBonus() {
        // QA Engineers get bonus based on bugs found
        return bugsFound * 50; // $50 per bug found
    }
    
    // Specific methods for QA Engineer
    public String getTestingTools() { return testingTools; }
    public int getBugsFound() { return bugsFound; }
    public void setTestingTools(String tools) { this.testingTools = tools; }
    public void reportBug() { this.bugsFound++; }
}

// ====================================================================================
// DESIGN PATTERN IMPLEMENTATIONS
// ====================================================================================

/**
 * SINGLETON PATTERN #1 - Logger
 * Ensures only one instance of logger exists throughout the application
 */
class Logger {
    private static Logger instance;
    private List<String> logs;
    
    // Private constructor prevents external instantiation
    private Logger() {
        logs = new ArrayList<>();
    }
    
    // Thread-safe singleton implementation
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        String timestamp = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String logEntry = "[" + timestamp + "] " + message;
        logs.add(logEntry);
        System.out.println("LOG: " + logEntry);
    }
    
    public List<String> getLogs() {
        return new ArrayList<>(logs); // Return copy to maintain encapsulation
    }
}

/**
 * SINGLETON PATTERN #2 - Database Connection
 * Simulates database connection management
 */
class DatabaseConnection {
    private static DatabaseConnection instance;
    private boolean connected;
    
    private DatabaseConnection() {
        connected = false;
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public void connect() {
        if (!connected) {
            connected = true;
            Logger.getInstance().log("Database connected successfully");
        }
    }
    
    public void disconnect() {
        if (connected) {
            connected = false;
            Logger.getInstance().log("Database disconnected");
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    // Simulate database operations
    public void saveEmployee(Employee employee) {
        if (connected) {
            Logger.getInstance().log("Employee saved to database: " + employee.getName());
        } else {
            Logger.getInstance().log("Cannot save employee - database not connected");
        }
    }
}

/**
 * FACTORY PATTERN - Employee Factory
 * Creates different types of employees based on type parameter
 */
class EmployeeFactory {
    
    // Factory method that demonstrates polymorphism
    public static Employee createEmployee(String type, String employeeId, String name, 
                                        String email, String department, double baseSalary, 
                                        Object... specificParams) {
        
        Logger.getInstance().log("Creating employee of type: " + type);
        
        switch (type.toLowerCase()) {
            case "developer":
                String language = (String) specificParams[0];
                int experience = (Integer) specificParams[1];
                return new Developer(employeeId, name, email, department, baseSalary, language, experience);
                
            case "manager":
                int teamSize = (Integer) specificParams[0];
                return new Manager(employeeId, name, email, department, baseSalary, teamSize);
                
            case "qa":
            case "qaengineer":
                String tools = (String) specificParams[0];
                return new QAEngineer(employeeId, name, email, department, baseSalary, tools);
                
            default:
                throw new IllegalArgumentException("Unknown employee type: " + type);
        }
    }
}

/**
 * STRATEGY PATTERN - Different salary calculation strategies
 */
class StandardSalaryStrategy implements SalaryCalculationStrategy {
    @Override
    public double calculateTotalSalary(Employee employee) {
        return employee.getBaseSalary() + employee.calculateBonus();
    }
    
    @Override
    public String getStrategyName() {
        return "Standard Salary Calculation";
    }
}

class TaxDeductedSalaryStrategy implements SalaryCalculationStrategy {
    private double taxRate;
    
    public TaxDeductedSalaryStrategy(double taxRate) {
        this.taxRate = taxRate;
    }
    
    @Override
    public double calculateTotalSalary(Employee employee) {
        double grossSalary = employee.getBaseSalary() + employee.calculateBonus();
        return grossSalary - (grossSalary * taxRate);
    }
    
    @Override
    public String getStrategyName() {
        return "Tax Deducted Salary Calculation (" + (taxRate * 100) + "% tax)";
    }
}

class BonusEnhancedSalaryStrategy implements SalaryCalculationStrategy {
    private double bonusMultiplier;
    
    public BonusEnhancedSalaryStrategy(double bonusMultiplier) {
        this.bonusMultiplier = bonusMultiplier;
    }
    
    @Override
    public double calculateTotalSalary(Employee employee) {
        return employee.getBaseSalary() + (employee.calculateBonus() * bonusMultiplier);
    }
    
    @Override
    public String getStrategyName() {
        return "Bonus Enhanced Salary Calculation (" + bonusMultiplier + "x bonus)";
    }
}

/**
 * OBSERVER PATTERN - Notification System
 */
class NotificationManager {
    private List<NotificationObserver> observers;
    
    public NotificationManager() {
        observers = new ArrayList<>();
    }
    
    public void addObserver(NotificationObserver observer) {
        observers.add(observer);
        Logger.getInstance().log("Observer added to notification system");
    }
    
    public void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
        Logger.getInstance().log("Observer removed from notification system");
    }
    
    public void notifyObservers(String message) {
        Logger.getInstance().log("Broadcasting notification: " + message);
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }
}

// Concrete observer implementations
class EmailNotifier implements NotificationObserver {
    private String email;
    
    public EmailNotifier(String email) {
        this.email = email;
    }
    
    @Override
    public void update(String message) {
        System.out.println("EMAIL to " + email + ": " + message);
    }
}

class SMSNotifier implements NotificationObserver {
    private String phoneNumber;
    
    public SMSNotifier(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public void update(String message) {
        System.out.println("SMS to " + phoneNumber + ": " + message);
    }
}

/**
 * SINGLETON PATTERN #3 - Main HRIS Manager
 * Central system that coordinates all HR operations
 */
class HRISManager {
    private static HRISManager instance;
    private Map<String, Employee> employees;
    private SalaryCalculationStrategy salaryStrategy;
    private NotificationManager notificationManager;
    private DatabaseConnection dbConnection;
    private Logger logger;
    
    // Private constructor
    private HRISManager() {
        employees = new HashMap<>();
        salaryStrategy = new StandardSalaryStrategy(); // Default strategy
        notificationManager = new NotificationManager();
        dbConnection = DatabaseConnection.getInstance();
        logger = Logger.getInstance();
        
        // Initialize database connection
        dbConnection.connect();
        logger.log("HRIS Manager initialized");
    }
    
    public static synchronized HRISManager getInstance() {
        if (instance == null) {
            instance = new HRISManager();
        }
        return instance;
    }
    
    // Employee management operations
    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
        dbConnection.saveEmployee(employee);
        notificationManager.notifyObservers("New employee added: " + employee.getName());
        logger.log("Employee added: " + employee.getEmployeeId());
    }
    
    public Employee getEmployee(String employeeId) {
        return employees.get(employeeId);
    }
    
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
    
    public boolean removeEmployee(String employeeId) {
        Employee removed = employees.remove(employeeId);
        if (removed != null) {
            notificationManager.notifyObservers("Employee removed: " + removed.getName());
            logger.log("Employee removed: " + employeeId);
            return true;
        }
        return false;
    }
    
    // Salary calculation using Strategy Pattern
    public void setSalaryCalculationStrategy(SalaryCalculationStrategy strategy) {
        this.salaryStrategy = strategy;
        logger.log("Salary calculation strategy changed to: " + strategy.getStrategyName());
    }
    
    public double calculateEmployeeSalary(String employeeId) {
        Employee employee = employees.get(employeeId);
        if (employee != null) {
            return salaryStrategy.calculateTotalSalary(employee);
        }
        return 0.0;
    }
    
    // Notification management
    public void addNotificationObserver(NotificationObserver observer) {
        notificationManager.addObserver(observer);
    }
    
    public void removeNotificationObserver(NotificationObserver observer) {
        notificationManager.removeObserver(observer);
    }
    
    // Department-wise employee listing
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> deptEmployees = new ArrayList<>();
        for (Employee emp : employees.values()) {
            if (emp.getDepartment().equalsIgnoreCase(department)) {
                deptEmployees.add(emp);
            }
        }
        return deptEmployees;
    }
    
    // Generate department report
    public void generateDepartmentReport(String department) {
        List<Employee> deptEmployees = getEmployeesByDepartment(department);
        System.out.println("\n=== DEPARTMENT REPORT: " + department.toUpperCase() + " ===");
        System.out.println("Total Employees: " + deptEmployees.size());
        
        double totalSalary = 0;
        for (Employee emp : deptEmployees) {
            double salary = salaryStrategy.calculateTotalSalary(emp);
            totalSalary += salary;
            System.out.printf("- %s (ID: %s): $%.2f%n", emp.getName(), emp.getEmployeeId(), salary);
        }
        System.out.printf("Total Department Salary Cost: $%.2f%n", totalSalary);
        System.out.printf("Average Salary: $%.2f%n", deptEmployees.size() > 0 ? totalSalary / deptEmployees.size() : 0);
    }
    
    // Cleanup method
    public void shutdown() {
        dbConnection.disconnect();
        logger.log("HRIS Manager shutdown completed");
    }
}

// ====================================================================================
// MAIN APPLICATION CLASS
// ====================================================================================

/**
 * Main class demonstrating the complete HRIS system
 * Shows all OOP principles and Design Patterns in action
 */
public class MehraHRIS {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("WELCOME TO MEHRA SOFTWARE COMPANY HRIS");
        System.out.println("=".repeat(80));
        
        // Get singleton instance of HRIS Manager
        HRISManager hris = HRISManager.getInstance();
        
        // Setup notification observers (Observer Pattern)
        EmailNotifier emailNotifier = new EmailNotifier("hr@mehrasoftware.com");
        SMSNotifier smsNotifier = new SMSNotifier("+1-555-0123");
        hris.addNotificationObserver(emailNotifier);
        hris.addNotificationObserver(smsNotifier);
        
        System.out.println("\n1. CREATING EMPLOYEES USING FACTORY PATTERN:");
        System.out.println("-".repeat(50));
        
        // Create employees using Factory Pattern (Polymorphism in action)
        try {
            Employee dev1 = EmployeeFactory.createEmployee("developer", "DEV001", "Alice Johnson", 
                "alice@mehrasoftware.com", "Engineering", 75000, "Java", 3);
            
            Employee dev2 = EmployeeFactory.createEmployee("developer", "DEV002", "Bob Smith", 
                "bob@mehrasoftware.com", "Engineering", 85000, "Python", 5);
                
            Employee mgr1 = EmployeeFactory.createEmployee("manager", "MGR001", "Carol Davis", 
                "carol@mehrasoftware.com", "Engineering", 120000, 8);
            
            Employee qa1 = EmployeeFactory.createEmployee("qa", "QA001", "David Wilson", 
                "david@mehrasoftware.com", "Quality", 60000, "Selenium");
                
            Employee qa2 = EmployeeFactory.createEmployee("qaengineer", "QA002", "Eva Brown", 
                "eva@mehrasoftware.com", "Quality", 65000, "TestNG");
            
            // Add employees to HRIS
            hris.addEmployee(dev1);
            hris.addEmployee(dev2);
            hris.addEmployee(mgr1);
            hris.addEmployee(qa1);
            hris.addEmployee(qa2);
            
            // Add some project data for manager
            ((Manager) mgr1).addProject("Project Alpha");
            ((Manager) mgr1).addProject("Project Beta");
            
            // Add some bug reports for QA engineers
            ((QAEngineer) qa1).reportBug();
            ((QAEngineer) qa1).reportBug();
            ((QAEngineer) qa1).reportBug();
            ((QAEngineer) qa2).reportBug();
            ((QAEngineer) qa2).reportBug();
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating employee: " + e.getMessage());
        }
        
        System.out.println("\n2. DISPLAYING ALL EMPLOYEES (Polymorphism Demo):");
        System.out.println("-".repeat(50));
        List<Employee> allEmployees = hris.getAllEmployees();
        for (Employee emp : allEmployees) {
            // Polymorphism: each employee type displays differently
            System.out.println(emp.toString());
            System.out.printf("   Bonus: $%.2f, Type-specific bonus calculation%n", emp.calculateBonus());
        }
        
        System.out.println("\n3. SALARY CALCULATION WITH DIFFERENT STRATEGIES:");
        System.out.println("-".repeat(50));
        
        // Demonstrate Strategy Pattern with different salary calculations
        String testEmployeeId = "DEV001";
        Employee testEmployee = hris.getEmployee(testEmployeeId);
        System.out.println("Employee: " + testEmployee.getName());
        
        // Standard salary calculation
        hris.setSalaryCalculationStrategy(new StandardSalaryStrategy());
        System.out.printf("Standard Salary: $%.2f%n", hris.calculateEmployeeSalary(testEmployeeId));
        
        // Tax deducted salary calculation (20% tax)
        hris.setSalaryCalculationStrategy(new TaxDeductedSalaryStrategy(0.20));
        System.out.printf("After Tax (20%%): $%.2f%n", hris.calculateEmployeeSalary(testEmployeeId));
        
        // Bonus enhanced salary calculation (1.5x bonus multiplier)
        hris.setSalaryCalculationStrategy(new BonusEnhancedSalaryStrategy(1.5));
        System.out.printf("Bonus Enhanced (1.5x): $%.2f%n", hris.calculateEmployeeSalary(testEmployeeId));
        
        System.out.println("\n4. DEPARTMENT-WISE REPORTS:");
        System.out.println("-".repeat(50));
        
        // Reset to standard strategy for reports
        hris.setSalaryCalculationStrategy(new StandardSalaryStrategy());
        
        // Generate department reports
        hris.generateDepartmentReport("Engineering");
        hris.generateDepartmentReport("Quality");
        
        System.out.println("\n5. EMPLOYEE SEARCH AND UPDATE:");
        System.out.println("-".repeat(50));
        
        // Demonstrate encapsulation - accessing employee data through getters/setters
        Employee searchEmployee = hris.getEmployee("DEV002");
        if (searchEmployee != null) {
            System.out.println("Found employee: " + searchEmployee.getName());
            System.out.println("Current salary: $" + searchEmployee.getBaseSalary());
            
            // Update salary (demonstrating encapsulation)
            searchEmployee.setBaseSalary(90000);
            System.out.println("Updated salary: $" + searchEmployee.getBaseSalary());
        }
        
        System.out.println("\n6. REMOVING EMPLOYEE:");
        System.out.println("-".repeat(50));
        boolean removed = hris.removeEmployee("QA002");
        System.out.println("Employee removal " + (removed ? "successful" : "failed"));
        
        System.out.println("\n7. FINAL EMPLOYEE COUNT:");
        System.out.println("-".repeat(50));
        System.out.println("Total employees remaining: " + hris.getAllEmployees().size());
        
        System.out.println("\n8. SYSTEM LOGS:");
        System.out.println("-".repeat(50));
        Logger logger = Logger.getInstance();
        List<String> logs = logger.getLogs();
        System.out.println("Total log entries: " + logs.size());
        System.out.println("Last 5 log entries:");
        int startIndex = Math.max(0, logs.size() - 5);
        for (int i = startIndex; i < logs.size(); i++) {
            System.out.println("  " + logs.get(i));
        }
        
        // Cleanup
        hris.shutdown();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("HRIS DEMONSTRATION COMPLETED SUCCESSFULLY");
        System.out.println("All OOP Principles and Design Patterns demonstrated:");
        System.out.println("✓ Encapsulation - Private fields with getters/setters");
        System.out.println("✓ Abstraction - Abstract Employee class and interfaces");
        System.out.println("✓ Inheritance - Developer, Manager, QAEngineer extend Employee");
        System.out.println("✓ Polymorphism - Method overriding and dynamic method dispatch");
        System.out.println("✓ Singleton Pattern - HRISManager, Logger, DatabaseConnection");
        System.out.println("✓ Factory Pattern - EmployeeFactory creates different employee types");
        System.out.println("✓ Strategy Pattern - Multiple salary calculation strategies");
        System.out.println("✓ Observer Pattern - Notification system with multiple observers");
        System.out.println("=".repeat(80));
    }
}