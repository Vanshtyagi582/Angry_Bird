# AngryBird

This project is a simplified version of an "Angry Birds"-style game built using the LibGDX framework, a Java-based game development framework for creating cross-platform games. This README provides an overview of the project’s structure, flow, setup, run instructions, and any additional sources referenced.

## Project Structure

The game consists of several main classes and screens that define the user interface and gameplay experience:

### 1. **`AngryBird.java`** (Main Class)

- Entry point of the game. Initializes the game, sets the initial screen to `SplashScreen`, and manages the game's primary state. Holds a `SpriteBatch`, which is used to render textures on each screen.

### 2. **Screens**

- **`SplashScreen.java`**: The introductory screen that briefly displays a splash image or animation, then transitions to the main menu.
- **`MainMenuScreen.java`**: The main menu where players can start the game or access other options.
- **`HomeScreen.java`**: The level selection screen, allowing users to choose from multiple game levels.
- **`LevelScreen.java`** and future levels: Represents individual levels of gameplay, where game logic is implemented for user interaction.

## Flow of Execution

The game's flow begins with initialization in `AngryBird.java`, where `SplashScreen` is set as the first screen. The screens are navigated based on user inputs, and each screen has a specific role in guiding the player through the game. Below is a breakdown of each screen's role and flow:

1. **Game Initialization** (`AngryBird.java`):
    - The game starts with `AngryBird.java`, which initializes `SpriteBatch` and sets `SplashScreen` as the first screen to display.
2. **Splash Screen** (`SplashScreen.java`):
    - Displays a splash image or animation and, after a brief delay, transitions to `MainMenuScreen`.
    - **Key Methods**:
        - **show()**: Loads and displays the splash image.
        - **render()**: Maintains the display period, then transitions to `MainMenuScreen`.
        - **dispose()**: Releases resources when the screen is no longer active.
3. **Main Menu Screen** (`MainMenuScreen.java`):
    - Provides options for the player to start the game or explore additional features.
    - **Key Methods**:
        - **render()**: Checks for user interaction on menu options.
        - **dispose()**: Frees up resources once the screen is exited.
4. **Home Screen** (`HomeScreen.java`):
    - Displays level options that the player can select.
    - **Key Methods**:
        - **render()**: Handles touch input for level selection and the back button.
        - **dispose()**: Releases resources associated with the home screen.
5. **Level Screens** (`LevelScreen.java`):
    - Represents individual levels, handling the gameplay mechanics.
    - **Key Methods**:
        - **render()**: Runs the level’s game logic and captures user interactions.
        - **dispose()**: Cleans up resources once the level is exited.

### Flow Diagram

![ap.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d87485b0-6f18-496d-8cce-05cc5806aed5/0b982839-cc97-42e5-8667-f016516408a5/ap.png)

## Setup and Execution Instructions

To set up, run, and test the project, follow these steps:

1. **Clone the Repository**:
    - In your IDE (e.g., IntelliJ IDEA), go to `File > New > Project from Version Control > Git`.
    - Enter the repository URL, select the directory to clone to, and click **Clone**.
2. **Build the Project**:
    - Ensure you have [LibGDX](https://libgdx.com/) and [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) installed.
    - Configure the project to include LibGDX dependencies.
3. **Run the Project**:
    - Set `AngryBird` as the main class.
    - Run the project through the IDE.
4. **Testing**:
    - Launch the game to navigate through the various screens and test interactions.
    - Use debugging tools to monitor object behaviors during gameplay.

### Prerequisites

1. **Java Development Kit (JDK)**: Java JDK version 8 or above.
2. **LibGDX Framework**: The project uses LibGDX, which should be configured in your IDE.
3. **Gradle**: Gradle is required to build and run the project.

---

## Additional Resources

- **LibGDX Documentation**: [LibGDX Documentation](https://libgdx.com/documentation/)
- **LibGDX Wiki**: Offers tutorials and example code to help understand the framework better.
- **Stack Overflow**: Useful for troubleshooting issues and getting specific answers about LibGDX functionality.
- Libgdx youtube tutorials
- Google for assets

---
Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
