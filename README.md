# B-Tree Visualizer with Interactive GUI

[![Java](https://img.shields.io/badge/Java-22-orange.svg)](https://openjdk.java.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-22.0.1-blue.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.13.0-red.svg)](https://maven.apache.org/)
[![Graphviz](https://img.shields.io/badge/Graphviz-Library-brightgreen.svg)](https://graphviz.org/)

A sophisticated JavaFX application that implements and visualizes **B-Tree data structures** with real-time graphical representation. This project demonstrates advanced algorithms and data structures concepts through an intuitive, interactive interface.

## Key Features

### Complete B-Tree Operations
- **Insert** - Add keys with automatic node splitting
- **Delete** - Remove keys with intelligent rebalancing
- **Search** - Fast O(log n) key lookup
- **Traversal** - Inorder tree traversal
- **Min/Max** - Find minimum and maximum values
- **Predecessor/Successor** - Navigate tree relationships

### Real-Time Visualization
- **Graphviz Integration** - Automatic tree graph generation
- **Live Updates** - Visual representation updates after each operation
- **Node Structure Display** - Clear representation of keys within nodes
- **Relationship Arrows** - Parent-child connections

### Smart Features
- **Duplicate Prevention** - Automatic detection of existing keys
- **Configurable Branching Factor** - Set minimum degree (t) at startup
- **Error Handling** - Graceful handling of edge cases
- **User-Friendly Alerts** - Clear feedback for all operations

## Technical Highlights

### Advanced B-Tree Implementation
```java
// From-scratch implementation including:
- Node splitting when full (2t-1 keys)
- Key borrowing from siblings
- Node merging when underflow occurs
- Recursive deletion with rebalancing
- Efficient search with early termination
```

### Algorithm Complexity
| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Insert    | O(t log_t n)   | O(h)            |
| Delete    | O(t log_t n)   | O(h)            |
| Search    | O(t log_t n)   | O(h)            |
| Min/Max   | O(h)           | O(h)            |
| Traversal | O(n)           | O(h)            |

*where t = minimum degree, n = number of keys, h = tree height*

## Quick Start

### Prerequisites
- **Java Development Kit (JDK)** 22 or higher
- **Apache Maven** 3.8.0 or higher
- **Graphviz** installed on your system
  - macOS: `brew install graphviz`
  - Ubuntu/Debian: `sudo apt-get install graphviz`
  - Windows: Download from [graphviz.org](https://graphviz.org/download/)

### Installation & Running

```bash
# Clone the repository
git clone https://github.com/yourusername/btree-visualizer.git
cd btree-visualizer

# Compile and run with Maven
mvn clean javafx:run
```

### On First Launch
1. A dialog will prompt you to enter the branching factor (t ≥ 2)
2. Common values: t=3 (most balanced), t=2 (minimum), t=5 (wider nodes)
3. The main application window opens with an empty tree

### Using the Application

**Adding Keys:**
1. Enter a number in the text field
2. Click "Add" button
3. Watch the tree automatically reorganize and update

**Deleting Keys:**
1. Enter an existing key
2. Click "Delete" button
3. Observe rebalancing operations

**Searching:**
- Enter any number and click "Search"
- Get immediate feedback if found/not found

**Other Operations:**
- Click respective buttons for Min, Max, Predecessor, Successor
- View complete inorder traversal

## Complete Documentation

For in-depth technical details, algorithm explanations, and implementation insights:

**[View Complete Technical Documentation](docs/technical-documentation.pdf)**

The comprehensive documentation includes:
- Detailed B-Tree algorithm explanation
- Node splitting and merging logic
- Deletion algorithm with all cases
- Graphviz DOT format generation
- Mathematical complexity analysis
- Complete code walkthrough
- Troubleshooting guide

## Project Structure

```
btree-visualizer/
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java
│       │   └── org/example/adsproject/
│       │       ├── BTree.java              # Core B-Tree implementation
│       │       ├── BTreeNode.java          # Node structure
│       │       └── HelloApplication.java   # JavaFX GUI
│       └── resources/
│           └── org/example/adsproject/
│               └── hello-view.fxml
├── pom.xml                                 # Maven configuration
└── README.md
```

## B-Tree Properties Maintained

This implementation strictly maintains all B-Tree invariants:

1. **Balanced Tree** - All leaves at the same level
2. **Node Capacity** - Each node has [t-1, 2t-1] keys
3. **Child Count** - Internal nodes have [t, 2t] children
4. **Sorted Keys** - Keys within nodes are sorted
5. **Subtree Ordering** - All keys in left subtree < parent key < right subtree

## Technologies Used

- **Java 22** - Modern Java features and syntax
- **JavaFX 22.0.1** - Rich desktop application framework
- **Graphviz Java** (guru.nidi) - DOT graph visualization
- **Apache Maven** - Dependency management and build tool

## Educational Value

Perfect for learning and demonstrating:
- **Data Structures** - Self-balancing trees, multi-way search trees
- **Algorithms** - Recursive operations, tree balancing strategies
- **Complexity Analysis** - Understanding logarithmic operations
- **Software Design** - Clean separation between data structure and visualization
- **GUI Programming** - Event-driven architecture with JavaFX

## Use Cases

B-Trees are fundamental in:
- **Database Indexing** - MySQL, PostgreSQL use B+ Trees
- **File Systems** - NTFS, ext4, HFS+ use B-Tree variants
- **Operating Systems** - Memory management structures
- **Search Engines** - Inverted index implementations

## Contributing

Contributions welcome! Areas of interest:
- Algorithm optimizations
- Additional tree operations
- Enhanced visualization features
- Unit test coverage
- Documentation improvements

**Star this repository if you find it useful!**
