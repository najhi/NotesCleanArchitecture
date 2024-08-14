
# Notes

## Overview

The Notes App is an Android application built using modern Android development tools and libraries such as Jetpack Compose, Koin, Room, and Clean Architecture. The app allows users to add, update, delete, search, and sort notes based on title, creation date, or modification date, with options for ascending and descending order.


### Features

	• Add Note: Create a new note with a title, content, and optional tags.
	• Update Note: Modify the content of an existing note.
	• Delete Note: Remove one or multiple notes.
	• Search Notes: Search notes by title or content.
	• Sort Notes: Sort notes by title, creation date, or modification date in both ascending and descending order.

### Tech Stack

	• Jetpack Compose: Used for building the UI in a declarative manner.
	• Room: Database library for handling data persistence with table migration support.
	• Koin: Dependency injection framework.
	• Clean Architecture: Ensures separation of concerns and testability.
	• LiveData: Observes and reacts to data changes.
	• State Management: Manages UI states and user interactions efficiently.

## Architecture

### Clean Architecture Layers

	1. Presentation Layer:
	    • ViewModel: Handles UI-related logic and state management.
	    • UI (Jetpack Compose): Renders the UI based on state provided by the ViewModel.
	2. Domain Layer:
	    • Use Cases: Encapsulates business logic and interacts with repositories.
	3. Data Layer:
	    • Repository: Interfaces with the data source (Room database).
	    • Local Data Source: Uses Room for data persistence.

### Room Database

The app uses Room for data persistence with the following entities:

	• Note Entity: Represents a note with fields like id, title, content, tags, created, modified, and starred.

#### Table Migration

Room handles database migrations to ensure data integrity when the schema changes. This is essential when adding new fields like modified and starred to the Note entity.

### DAO Methods

	• Insert: Inserts a new note into the database.
	• Update: Updates an existing note.
	• Delete: Deletes one or more notes.
	• Search: Searches for notes by title or content.
	• Get All Notes: Retrieves notes sorted by title, created date, or modified date.

### Dependency Injection with Koin

Koin is used to manage dependencies across the app. The app’s modules are defined in `di`, which include:

	• AppModule: Provides ViewModels and Usecases.
	• Repository Module: Provides repositories.
	• Datastore Module: Provides Room database and Shared preferences.

### State Management

The app uses Jetpack Compose’s remember, mutableStateOf, and derivedStateOf for state management. These help efficiently manage UI state and trigger recompositions when necessary.

### How to Build and Run

	1. Clone the Repository: 
		• git clone https://github.com/najhi/NotesCleanArchitecture.git

	2. Open the Project:
		• Open the project in Android Studio.
		
	3. Sync Gradle:
		• Let Android Studio sync the Gradle dependencies.

	4. Run the App:
		• Build and run the app on an emulator or a physical device.

## Testing

The app includes instrumented tests for:

	• DAO methods: Testing database operations.

## Future Enhancements

	• Backup & Restore: Enable cloud backup and restore functionality.
	• Reminders: Add reminders for notes.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.








