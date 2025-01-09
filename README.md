# Marvel App

## Overview

This project is  built with Kotlin and xml, following the MVI architecture pattern. The app fetches marver characters from Marvel API http://developer.marvel.com/ , displays them in a list, and allows users to view charachter details.

## Features

- Fetch and display characters.
- View charachter detalils Blog .

## Architecture

The app is built using the MVI (Model-View-Intent) architecture with clean architecture:
- **Model**: Manages data from the API
- **Intent**: Represents user actions or intents that are dispatched to the ViewModel
- **View**: draw UI that react to ViewModel state.

## Libraries Used

- **XML**: For UI.
- **Retrofit**: For network requests.
- **Paging3**: For supporting pagination of data .
- **Coroutines**: For asynchronous operations.
- **Hilt**: For dependency injection.

## Setup Instructions

1. **Clone the repository**:
    ```bash
    git clone https://github.com/mahmoudgimmy/marvel.git
 
    ```

2. **Open in Android Studio**:
    - Open Android Studio.
    - Select `Open an existing project` and choose the project directory.

3. **Configure API Key**:
    - Obtain an API key from Marvel App.
    - Open `gradle.properties` file in the root directory.
    - Add your API key:
      ```
      PUBLIC_KEY = your public key
      PRIVATE_KEY = your private key
      ```

4. **Build and Run**:
    - Connect an Android device or start an emulator.
    - Click on `Run` in Android Studio.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

