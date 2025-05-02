# AUEB AI Project Assignment

> **A*** search implementation in Java for the AUEB AI course

This repository contains a Java implementation of the A* (A‑star) search algorithm applied to the assignment problem described in the included report. You’ll find source code under `src/`, plus a detailed project write‑up in both PDF and ODT formats.

---

## Table of Contents

- [Project Structure](#project-structure)  
- [Prerequisites](#prerequisites)  
- [Build & Run](#build--run)  
- [Usage](#usage)  
- [Report](#report)  
- [Author](#author)  
- [License](#license)  

---

## Project Structure

```
AUEB-Ai-Project-Assigment/
├── report.pdf            # Final project report (PDF)
├── report.odt            # Final project report (ODT)
└── src/
    ├── A_star_algorithm.java  # Implements the A* search logic
    ├── State.java            # Defines the problem state and heuristic
    └── Main.java             # Entry point: sets up initial state & runs search
```

---

## Prerequisites

- Java 8 or higher  
- (Optional) An IDE such as IntelliJ IDEA or Eclipse, or simply the `javac`/`java` CLI  

---

## Build & Run

1. **Clone the repo**  
   ```bash
   git clone https://github.com/loukas-vetoulis/AUEB-Ai-Project-Assigment.git
   cd AUEB-Ai-Project-Assigment
   ```

2. **Compile**  
   ```bash
   javac src/*.java -d bin
   ```

3. **Run**  
   ```bash
   java -cp bin Main
   ```

   By default, `Main` constructs an initial `State(1000, 10, 10000)` and prints out:
   - The step‑by‑step path found by A*  
   - Total search time in seconds  

   Feel free to adjust the parameters in `Main.java` to experiment with different problem sizes or heuristics.

---

## Usage

- **Modify initial conditions**:  
  In `src/Main.java`, change the line  
  ```java
  State initialState = new State(1000, 10, 10000);
  ```  
  to your desired parameters.

- **Custom state logic**:  
  The `State` class encapsulates:
  - A representation of the current problem configuration  
  - Methods `getChildren()`, `isFinal()`, and a heuristic to guide A*  

  You can extend or replace `State.java` to solve other search problems.

---

## Report

A full write‑up—including problem definition, algorithm explanation, experiments, and results—is available as:

- **PDF**: `report.pdf`  
- **ODT**: `report.odt`

Please refer to it for background, mathematical derivations, and discussion of performance.

---

## Contact

Loukas Vetoulis - [GitHub Profile](https://github.com/loukas-vetoulis)

Project Link: [https://github.com/loukas-vetoulis/AUEB-Ai-Project-Assigment](https://github.com/loukas-vetoulis/AUEB-Ai-Project-Assigment)