# Mini Compiler with GUI

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Files and Directories](#files-and-directories)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview

This project implements a mini compiler with a graphical user interface (GUI) in Java. The compiler is designed to demonstrate the key stages of compilation, such as lexical analysis, syntax analysis, and intermediate code generation. It is a simplified version aimed at educational purposes, providing an interactive way to understand how compilers work.

## Features

- **Lexical Analysis**: Identifies the tokens in the source code.
- **Syntax Analysis**: Parses the tokens according to the grammar rules.
- **Intermediate Code Generation**: Produces intermediate code as a step before generating machine code.
- **Graphical User Interface**: The compiler features a GUI for easy interaction, making it user-friendly for educational demonstrations.

## Files and Directories

- `IntermediateCodeGenerator.form` and `IntermediateCodeGenerator.java`: Handles the generation of intermediate code in the compilation process.
- `LexemeGenerator.java`: Manages the generation of lexemes during lexical analysis.
- `MainPanel.form` and `MainPanel.java`: The main interface panel for the compiler’s GUI.
- `PBar.java` and `ScanProgressBar.form`: Handles the progress bar functionality in the GUI.
- `SymbolTable.form` and `SymbolTable.java`: Manages the symbol table, which is used to store identifiers and their attributes.
- `ThreeAddressCode.java`: Manages the generation of three-address code, a type of intermediate code.
- `TokenTable.form` and `TokenTable.java`: Handles the display and management of tokens identified during lexical analysis.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher.
- A Java IDE (e.g., IntelliJ IDEA, Eclipse) or any text editor with Java support.

### Installation

1. Clone the repository to your local machine using:
   ```bash
   git clone https://github.com/ammarlodhi255/mini-compiler-with-gui.git
   ```

2. Navigate to the project directory:
   ```bash
   cd mini-compiler-with-gui
   ```

3. Open the project in your preferred Java IDE.

### Usage

1. **Compile the project**: Ensure all `.java` files are compiled. Most IDEs handle this automatically.
2. **Run the GUI**:
   - Execute `MainPanel.java` to launch the user interface.
   - Use the provided interface to input code and observe the stages of compilation.

## Contributing

If you'd like to contribute to this project, please fork the repository and use a feature branch. Pull requests are warmly welcome.

1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push to the branch.
5. Submit a pull request.

## License

This project is open-source and available under the [MIT License](LICENSE).
