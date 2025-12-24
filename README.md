# B-Tree Visualizer

An interactive JavaFX application for visualizing B-Tree data structures with real-time graphical updates.

![Java](https://img.shields.io/badge/Java-22-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-22.0.1-blue.svg)
![Maven](https://img.shields.io/badge/Maven-Build-red.svg)
> **Note**: This project was developed with AI assistance as a learning and development tool, alongside traditional programming methods.


## Overview

This project implements a complete B-Tree data structure from scratch with an intuitive GUI that visualizes tree operations in real-time using Graphviz. Built to demonstrate understanding of advanced data structures and algorithms.


## Features

- **Full B-Tree Operations**: Insert, Delete, Search, Min/Max, Predecessor/Successor
- **Live Visualization**: Automatic graph rendering after each operation using Graphviz
- **Configurable**: Set custom branching factor (minimum degree) at startup
- **Smart Validation**: Duplicate key prevention and comprehensive error handling

## Algorithm Complexity

| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Insert/Delete/Search | O(t log_t n) | O(h) |
| Min/Max | O(h) | O(h) |
| Traversal | O(n) | O(h) |

## Technologies

- Java 22
- JavaFX 22.0.1
- Graphviz (guru.nidi library)
- Maven

## Prerequisites

- JDK 22+
- Maven 3.8+
- Graphviz installed on your system
  - macOS: `brew install graphviz`
  - Ubuntu: `sudo apt-get install graphviz`
  - Windows: [Download from graphviz.org](https://graphviz.org/download/)

## Quick Start
```bash
git clone https://github.com/bughi04/BTrees.git
cd BTrees
mvn clean javafx:run
```

On launch, you'll be prompted to enter a branching factor (t ≥ 2). The application opens with an empty tree ready for operations.

## Screenshots (To add soon)

![B-Tree Visualization Example]

## What I Learned

- Implementing complex self-balancing tree algorithms (node splitting, merging, rebalancing)
- JavaFX GUI development and event handling
- Integrating third-party visualization libraries
- Managing Maven dependencies and build configuration

## Future Enhancements

- Animation of insert/delete operations
- Step-by-step operation breakdown
- Export tree to various formats
- Comparison with other tree structures (AVL, Red-Black)

**If you find this project interesting, please consider giving it a star!**
