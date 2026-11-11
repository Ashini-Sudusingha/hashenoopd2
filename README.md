# SmartCity360 - Enterprise Urban Infrastructure Operations Platform

**Academic Assessment Submission**  
- **Candidate Name:** Hashen Liyanaarachchi  
- **Unit Title:** Object Oriented Design Patterns II (`JIAT/OODP II`)  
- **Assessment Reference:** `JIAT/OODP II /EX/01`  
- **Institution:** Java Institute for Advanced Technology  

---

## 1. Project Overview

**SmartCity360** is an enterprise-grade municipal infrastructure management platform designed for the Greater Colombo metropolitan area. The system orchestrates power grid telemetry security, hierarchical energy distribution, citizen defect escalation, massive streetlight fleet memory optimization, and dynamic municipal bylaw interpretation by leveraging five fundamental Gang of Four (GoF) object-oriented design patterns.

---

## 2. Gang of Four (GoF) Design Pattern Implementations

| Subsystem Part | Municipal Scenario | Selected GoF Pattern | Pattern Category | Core Architectural Benefit |
| :--- | :--- | :--- | :--- | :--- |
| **Part A** | Dynamic Security Wrappers for Data Streams | **Decorator Pattern** | Structural | Dynamic, stackable security layering (Token Auth, AES-256 Cipher, GZIP Compression) without subclass explosion or socket alteration. |
| **Part B** | Hierarchical Structure of Smart Zones | **Composite Pattern** | Structural | Uniform recursive tree hierarchy allowing transparent traversal, demand aggregation, and emergency power cuts across cities, districts, streets, and facilities. |
| **Part C** | Escalation of Citizen Complaints | **Chain of Responsibility** | Behavioral | Decoupled sequential escalation pipeline (Front Desk &rarr; Zonal Supervisor &rarr; Chief Municipal Officer) with full audit logging. |
| **Part D** | Efficient Management of Smart Street Lights | **Flyweight Pattern** | Structural | Separation of intrinsic shared specifications from extrinsic coordinates/state, reducing heap memory consumption by 75%. |
| **Part E** | Interpreting City Policies Defined in DSL | **Interpreter Pattern** | Behavioral | Runtime evaluation of dynamic municipal bylaws (quiet hours, noise limits, parking duration) using an Abstract Syntax Tree (AST). |

---

## 3. Technology Stack & Prerequisites

- **Language:** Java SE (JDK 17+)
- **Server:** Embedded `com.sun.net.httpserver.HttpServer` (Port 8081)
- **JSON Engine:** Google Gson 2.11.0 (`lib/gson-2.11.0.jar`)
- **Frontend:** Pure HTML5, Modern Responsive CSS3 (Glassmorphism & Theme Toggle), Vanilla JavaScript (Asynchronous Fetch REST APIs)
- **Zero External Database Dependency:** In-memory high-performance data model for instant deployment and demonstration.

---

## 4. How to Execute the Project

### Option A: Web Operations Dashboard (Recommended)
1. Double-click `compile_and_run_web.bat` (or execute from terminal):
   ```cmd
   .\compile_and_run_web.bat
   ```
2. Open your web browser and navigate to:
   ```
   http://localhost:8081
   ```
3. Test all 5 interactive modules (Stream Security Studio, Power Grid Tree, Complaint Escalation Dispatcher, Streetlight Fleet Simulator, and Municipal Bylaw Policy Engine).

### Option B: Standalone Console Demonstration
1. Double-click `compile_and_run.bat` (or execute from terminal):
   ```cmd
   .\compile_and_run.bat
   ```
2. The console CLI runner executes each of the 5 GoF design patterns in sequence and prints formatted diagnostics, execution traces, and memory benchmarks.

### Option C: Generate Submission ZIP Archive
1. Double-click `package_submission_zip.bat`.
2. A submission ZIP (`SmartCity360_HashenLiyanaarachchi_Submission.zip`) will be packaged automatically in the parent directory.

---

## 5. Directory Structure

```
SmartCity360_HashenLiyanaarachchi/
├── bin/                                # Compiled Java bytecode (.class)
├── lib/                                # External libraries (Gson 2.11.0)
├── src/
│   └── com/smartcity360/
│       ├── controller/                 # Central Singleton Facade (CityOperationsManager)
│       ├── main/                       # Standalone CLI entrypoint (SmartCity360App)
│       ├── partA_decorator/            # GoF Decorator Pattern classes
│       ├── partB_composite/            # GoF Composite Pattern classes
│       ├── partC_chainofresponsibility/# GoF Chain of Responsibility classes
│       ├── partD_flyweight/            # GoF Flyweight Pattern classes
│       ├── partE_interpreter/          # GoF Interpreter Pattern classes
│       └── web/                        # Embedded HTTP Server engine (MetroWebServer:8081)
├── web/
│   ├── css/
│   │   └── metro.css                   # Responsive UI stylesheet
│   ├── js/
│   │   └── metro.js                    # REST API client script
│   ├── WEB-INF/
│   │   └── web.xml                     # Web descriptor
│   └── index.html                      # Interactive web command center
├── compile_and_run.bat                 # Compile & launch console application
├── compile_and_run_web.bat             # Compile & launch web server on 8081
├── package_submission_zip.bat          # Package submission ZIP
├── run_project.bat                     # Run precompiled console app
├── run_web_server.bat                  # Run precompiled web server
├── README.md                           # Quickstart guide
└── PROJECT_DOCUMENTATION.md            # Comprehensive 10-Chapter Academic Report
```
