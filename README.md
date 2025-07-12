# DineVibes Android App

## Description

DineVibes is an Android app designed to efficiently manage the **LIET Pantry** and associated services. This app allows students and administrators to create and track maintenance requests, manage pantry items, and provide essential services like booking halls and managing the pickup of items. Users can sign up, create requests, and monitor request statuses, while admins have the ability to manage users, order history, and other tasks. All data interactions are performed through API calls, without the use of Firebase. The app is built using the **MVVM (Model-View-ViewModel) architecture** for a clean separation of concerns and better scalability.

## Features

* **User Sign-Up & Login**: Students and staff can create an account or sign in using their college email and password. The app supports password recovery and account creation.
* **Maintenance Requests**: Users can create maintenance requests for issues like cleanliness, technical support, and more. Categories include Cleanliness, Furniture, Technical, and Other.
* **Request Status**: Track the status of all requests, such as **Approved**, **Pending**, and **Rejected**.
* **Admin Dashboard**: Admins can manage user profiles, monitor the status of requests, and view detailed information about order history, including items from the pantry.
* **Pantry Management**: Users can check the available items in the pantry and place orders.
* **Profile Management**: Users can view and update their profiles, including contact details like phone number and gender.
* **Hall Booking & Item Pickup**: Admins and users can manage hall bookings and coordinate the pickup of items.

## Screenshots

Here are some key screenshots of the app:

* **Request Status**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image1.jpeg" width="400" />

  * Request Status page showing the submission details, including description and location, with statuses such as **Approved**, **Pending**, and **Rejected**.

* **Maintenance Request**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image2.jpeg" width="400" />

  * Maintenance Request page where users can select a category, describe the issue, set a priority level, and submit their requests.

* **Sign-Up Page**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image3.jpeg" width="400" />

  * Sign-up page for new users to create their account.

* **Login Page**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image4.jpeg" width="400" />

  * Login page for returning users to sign in.

* **Forgot Password**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image6.jpeg" width="400" />

  * Forgot Password page where users can reset their account password.

* **Admin Profile**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image7.jpeg" width="400" />

  * Admin profile page, showing contact details and order history, as well as user profile management.

* **Main Screen**
  * <img src="https://raw.githubusercontent.com/UxHarshit/DineVibes/refs/heads/master/images/image8.jpeg" width="400" />

  * The home page of the app with options for **Items**, **Pickup**, **Book Hall**, and **Maintenance**.

## Build from Source

To build the app from source, follow these steps:

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/UxHarshit/DineVibes.git
   ```

2. **Open in Android Studio**:

   * Launch **Android Studio**.
   * Select **Open an Existing Project** and navigate to the cloned repository folder.
   * Open the project.

3. **Install Dependencies**:

   * Sync the project with Gradle by clicking **File** > **Sync Project with Gradle Files**.
   * Ensure all dependencies are resolved.

4. **Configure API Endpoints**:

   * Configure the base API URLs in the project. These URLs will be used to interact with your backend services. Replace the placeholders in the configuration files with actual API endpoints.

   Example (in your `NetworkConfig` or similar class):

   ```kotlin
   const val BASE_URL = "https://your-api-url.com/"
   ```

5. **Build the Project**:

   * Select the device or emulator to run the app.
   * Click **Run** (the green play button) to build and run the app.

6. **API Integration**:

   * Ensure that your backend APIs are properly configured to handle the required requests (sign-up, login, request status, etc.). This app interacts with the backend using standard HTTP requests (e.g., **GET**, **POST**, **PUT**).

## MVVM Architecture

The app is built using the **MVVM (Model-View-ViewModel) architecture** to separate concerns and ensure a cleaner, more maintainable codebase:

* **Model**: Represents the data layer, including data models, network calls, and local database interactions. The app uses **Retrofit** for making network API calls to fetch and update data.
* **View**: Represents the UI layer, which handles the display of data and user interactions. The app uses **Fragments** and **Activities** to display data to the user.
* **ViewModel**: Acts as a bridge between the View and Model, holding the UI-related data and managing the logic for fetching and updating data. The ViewModel exposes **LiveData** to the View, allowing for automatic UI updates when the data changes.

### Benefits of MVVM:

* **Separation of Concerns**: The architecture allows for better code organization and easier maintenance by separating the data logic from the UI logic.
* **Testability**: By decoupling the View and Model, the ViewModel can be unit tested without worrying about UI components.
* **Scalability**: The app can be scaled more easily due to the separation of logic, making it easier to add new features in the future.

## Usage Instructions

1. **Sign Up**: New users can sign up by providing their full name, college email, password, and phone number.
2. **Sign In**: Existing users can log in using their credentials.
3. **Create a Request**: Once logged in, users can create a maintenance request by selecting the relevant category, entering a description, setting a location, and submitting the request.
4. **Check Request Status**: Users can track the status of their maintenance requests under the **Request Status** section.
5. **Pantry Management**: Users can check available pantry items and place orders. Admins have the ability to monitor and manage inventory.
6. **Hall Booking**: Admins and users can book halls for various purposes, such as events and meetings.
7. **Item Pickup**: Users can schedule item pickups from the pantry or other designated locations.

## Technologies Used

* **Android Studio**: Used to develop and build the app.
* **Kotlin**: The programming language used for app development.
* **Retrofit/Volley**: For making API calls to the backend.
* **Room**: Local database to manage persistent data.
* **Material Design**: For intuitive and user-friendly UI components.
* **MVVM Architecture**: Ensures a clean separation between UI and business logic.
* **LiveData**: For observing data changes and updating the UI automatically.

## License

This app is open-source and available under the MIT License.

