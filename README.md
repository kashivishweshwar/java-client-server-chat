# 💬 Java TCP Chat Application

A beginner-friendly, two-file TCP client-server chat application built with **Java Sockets**.  
No external libraries required — pure Java SE.

---

## 🗂 Project Structure

```
tcp-chat/
├── Server.java   ← Listens for a client, receives & replies to messages
├── Client.java   ← Connects to the server, sends messages from the console
└── README.md     ← You are here
```

---

## ⚡ Quick Start (Two Terminals)

### Step 1 — Compile both files

Open a terminal in the project folder and run:

```bash
javac Server.java Client.java
```

This produces `Server.class` and `Client.class`.

---

### Step 2 — Start the Server (Terminal 1)

```bash
java Server
```

Expected output:
```
╔══════════════════════════════════╗
║      TCP Chat Server started     ║
╚══════════════════════════════════╝
⏳ Waiting for a client on port 5000 ...
```

The server blocks here until a client connects.

---

### Step 3 — Start the Client (Terminal 2)

```bash
java Client
```

Expected output:
```
╔══════════════════════════════════╗
║      TCP Chat Client started     ║
╚══════════════════════════════════╝
🔗 Connecting to localhost:5000 ...

✅ Connected to server!
💬 Type a message and press Enter to send.
```

---

### Step 4 — Chat!

**In the Client terminal**, type a message and press Enter:
```
[You]   Hello from the client!
```

**In the Server terminal**, you'll see it arrive and can type a reply:
```
[Client] Hello from the client!
[You]   Hi there! Got your message.
```

**Back in the Client terminal**, the reply appears:
```
[Server] SERVER: Hi there! Got your message.
```

---

### Step 5 — Exit

Type `exit` in either terminal to end the session gracefully.

---

## 🔧 Configuration

| Setting | File | Default | How to change |
|---------|------|---------|---------------|
| Port | `Server.java` & `Client.java` | `5000` | Change `PORT` constant in both files |
| Host | `Client.java` | `localhost` | Change `HOST` to the server's IP for LAN chat |

---

## 🏗 How It Works

```
Client                          Server
  │                               │
  │── new Socket(host, port) ────►│  serverSocket.accept()
  │                               │
  │── out.println(message) ──────►│  in.readLine()
  │                               │  (server reads & replies)
  │◄─ in.readLine() ─────────────│  out.println(reply)
  │                               │
  │        ... repeats ...        │
  │                               │
  │── "exit" ────────────────────►│  closes connection
```

The two sides use `PrintWriter` (send) and `BufferedReader` (receive) layered on top of the socket's raw byte streams — a classic and readable Java pattern.

---

## 📋 Requirements

- **Java 8 or higher** (`java -version` to check)
- No external dependencies

---

## 💡 Ideas to Extend

- Support **multiple clients** using `Thread` per connection
- Add **timestamps** to each message
- Build a simple **GUI** with Java Swing
- Encrypt messages with **TLS / SSLSocket**
