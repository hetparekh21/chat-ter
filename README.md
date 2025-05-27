# Chat-ter 💬

A secure, anonymous, and multi-room chat application built for the terminal using pure Java.

## 🌟 Features

* **Anonymous Chatting:** Join conversations without revealing your identity.
* **Multi-Room Support:** Create and join different chat rooms.
* **Room Authentication:** Set passwords for private rooms.
* **Terminal-Based Interface:** Fully functional within your command-line environment.
* **Real-time Communication:** Instant messaging between connected users.
* **User Commands:** Built-in commands for changing rooms, listing members, viewing info, and more.
* **Cross-Platform Console Clear:** Clears the terminal for a cleaner chat experience on both Windows and Linux/macOS.

---

## 💡 How it Works (Under the Hood)

**Chat-ter** is a classic client-server application powered by Java Sockets and multi-threading.

* **Server (`server.java` & `user.java`):**
    * The `server.java` continuously listens for incoming client connections.
    * For each new client, a dedicated `user.java` thread is started. This thread is responsible for listening to that specific client's input and managing their chat session within various rooms.
    * All room data and user connections are managed centrally on the server.
* **Client (`client.java`):**
    * The `client.java` application connects to the server.
    * It uses two main threads: one continuously listens for incoming messages from the server (so you don't miss anything while typing), and the other handles your typed input, sending it to the server.

This architecture ensures responsiveness and efficient handling of multiple concurrent users, a design choice greatly informed by my recent experiments with Java's threading performance!

---

## 🛠 Technologies Used

* **Java (JDK 8 or higher recommended)**
* `java.net.Socket` and `java.net.ServerSocket` for network communication.
* Standard Java I/O streams (`DataInputStream`, `DataOutputStream`, `BufferedReader`, `InputStreamReader`).
* `java.util.HashMap` and `java.util.HashSet` for in-memory data storage (note: for high-concurrency, consider `ConcurrentHashMap` and `Collections.synchronizedSet`).

---

## 🚀 Getting Started

To run Chat-ter, you'll need a Java Development Kit (JDK) installed on your system.

### 1. Clone the Repository

```bash
git clone [https://github.com/hetparekh21/Chat-ter.git](https://github.com/hetparekh21/Chat-ter.git)
cd Chat-ter
```

### 2. Compile the Java Files

Navigate to the `Chat-ter` directory in your terminal and compile the source code:

```bash
javac *.java
```

### 3. Run the Server

Open a **new terminal window**, navigate to the `Chat-ter` directory, and start the server:

```bash
java server
```

### 4. Run the Client(s)

Open one or more **new terminal windows** for your clients. In each, navigate to the `Chat-ter` directory, and run:

```bash
java client
```

### Optional: You can provide a custom username as a command-line argument:

```bash
java client MyCustomName
```

---

## ⌨️ Usage & Commands

Once connected to the chatroom, you can type your messages directly into the terminal. Here are the available commands:

* `/info` : Lists all available commands and environment details.
* `/lrms` : Lists all available chat rooms.
* `/mbrs` : Lists all members in your current room.
* `/chrm` : Change your current chat room.
* `/mkrm` : Create a new public chat room.
* `/mkrms` : Create a new password-protected chat room.
* `/cls` : Clears your console screen.
* `/exit` : Exits the chat application and closes your connection.

---

## 🚧 Future Enhancements

* Implementing robust thread-safe collections (e.g., `ConcurrentHashMap`, `Collections.synchronizedSet`) to handle concurrent access more safely.
* More graceful error handling and client disconnection notifications.
* Private messaging between users.
* Admin commands for room management.

---
