<pre>
oooooooooo.        .oooooo.       oooooooooo.       ooo        ooooo 
`888'   `Y8b      d8P'  `Y8b      `888'   `Y8b      `88.       .888' 
 888     888     888               888      888      888b     d'888  
 888oooo888'     888               888      888      8 Y88. .P  888  
 888    `88b     888               888      888      8  `888'   888  
 888    .88P     `88b    ooo       888     d88'      8    Y     888  
o888bood8P'       `Y8bood8P'      o888bood8P'       o8o        o888o
</pre>
# Concurrent Download Manager
___

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Usage](#usage)
- [Installation](#installation)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Overview

Concurrent Download Manager (CDM) is a Java-based application that enables concurrent downloading of files from multiple URLs. The application provides a user-friendly interface for managing and monitoring download tasks.
I built it to prove my proficiency in Java and will continue to improve upon it as time goes on.

## Features

- **Concurrent Downloads:** Download multiple files concurrently, enhancing download speed.
- **Pause and Resume:** Pause and resume downloads at any time.
- **Progress Monitoring:** Track the progress of individual downloads.
- **User Interface:** Simple and intuitive user interface for easy navigation.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine.

### Compilation and Execution

1. Clone the repository to your local machine.

    ```bash
    git clone git@github.com:akibeulah/ConcurrentDownloadManager.git
    ```

2. Navigate to the project directory.

    ```bash
    cd concurrent-download-manager
    ```

3. Compile the main class (e.g., `MainWindow`) using `javac`.

    ```bash
    javac src/ui/windows/MainWindow.java
    ```

4. Run the compiled class using `java`.

    ```bash
    java src.ui.windows.MainWindow
    ```
## Usage

1. **Running the Application:**
    - Compile and run the application's main class, such as the `MainWindow` class.
    - Upon execution, the main window of the Concurrent Download Manager will be displayed.

2. **Adding a New Download:**
    - Click on the "Add" icon to open the `NewDownloadWindow`.
    - Enter the desired URL into the text field and click the "Add" button.
    - The new download will be added to the list of downloads.

3. **Managing Downloads:**
    - The main window provides options to pause or resume downloads and view additional details.
    - Pausing or resuming a download is achieved by clicking the respective icons.
    - Additional functionalities can be added based on the placeholder button.

4. **Saving and Loading State:**
    - The application state is automatically saved to a binary file (`./tmp/CDM_BIN`) whenever there is a change in the state.
    - The state is loaded from the file during application startup.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Open-Source and Contributions

Feel free to fork this project and build upon it. You are welcome to use, modify, and distribute the code as per the terms of the [MIT License](LICENSE.md).

If you'd like to contribute or have suggestions, reach out on [Twitter](https://twitter.com/for_luhh) or create a pull request. I appreciate your interest and support!
