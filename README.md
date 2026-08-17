# Android Alarm Application

## AIM

Create an Android Alarm application using **Service** and **BroadcastReceiver**.

## Technologies Used

- Android Studio
- Kotlin
- XML
- Android SDK
- Service
- BroadcastReceiver
- AlarmManager

## Project Features

- Display the current time.
- Set an alarm for a selected time.
- Trigger the alarm using `AlarmManager`.
- Use `AlarmBroadcastReceiver` to receive the alarm event.
- Use `AlarmService` to perform the alarm operation.
- Schedule exact alarms using the `SCHEDULE_EXACT_ALARM` permission.

## Project Structure

```text
app/
└── src/
    └── main/
        ├── java/com/example/alarmapp/
        │   ├── MainActivity.kt
        │   ├── AlarmBroadcastReceiver.kt
        │   └── AlarmService.kt
        │
        ├── res/
        │   ├── layout/
        │   │   └── activity_main.xml
        │   └── drawable/
        │       └── img.png
        │
        └── AndroidManifest.xml
```

## Main Components

### 1. MainActivity

`MainActivity.kt` is responsible for:

- Displaying the alarm application UI.
- Showing the current time.
- Selecting the alarm time.
- Setting the alarm.
- Creating a `PendingIntent` for `AlarmBroadcastReceiver`.

### 2. AlarmBroadcastReceiver

`AlarmBroadcastReceiver.kt` receives the broadcast when the scheduled alarm time is reached.

Its main responsibility is to start `AlarmService`.

### 3. AlarmService

`AlarmService.kt` runs the alarm operation as an Android Service.

It can be used to:

- Start alarm sound.
- Show an alarm notification.
- Perform alarm-related tasks in the background.

### 4. AndroidManifest.xml

The application requires the following permission:

```xml
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

The `AlarmBroadcastReceiver` must also be registered inside the `<application>` section.

## Working Flow

```text
User opens application
        ↓
MainActivity
        ↓
User selects alarm time
        ↓
AlarmManager schedules alarm
        ↓
Selected time is reached
        ↓
AlarmBroadcastReceiver
        ↓
AlarmService
        ↓
Alarm starts
```

## Important Android Components

### AlarmManager

`AlarmManager` schedules an operation to happen at a specific time, even when the application is not currently open.

### BroadcastReceiver

`BroadcastReceiver` receives system or application broadcast messages. In this project, it receives the alarm event.

### Service

A `Service` performs operations in the background without requiring a user interface.

## Permission

Add this permission to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

## How to Run

1. Open the project in Android Studio.
2. Wait for Gradle synchronization to complete.
3. Connect an Android device or start an emulator.
4. Run the application.
5. Select the required alarm time.
6. Set the alarm.
7. Wait until the selected time.
8. The `AlarmBroadcastReceiver` receives the alarm event.
9. `AlarmService` starts the alarm operation.

## Learning Outcome

After completing this practical, students will understand:

- How `AlarmManager` works.
- How to create and use a `BroadcastReceiver`.
- How to create and use an Android `Service`.
- How to schedule an exact alarm.
- How Android components communicate with each other.
- How to use permissions in the Android Manifest.

## Conclusion

The Android Alarm Application demonstrates the use of **AlarmManager, BroadcastReceiver, and Service** to create a simple alarm system. The application schedules an alarm from `MainActivity`, receives the alarm event through `AlarmBroadcastReceiver`, and performs the alarm operation using `AlarmService`.
