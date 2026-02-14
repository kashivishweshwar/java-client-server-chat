// ============================================================
//  Client.java  –  Simple TCP Chat Client
//  Connects to the server and sends messages from the console.
//  Run this AFTER the server is already running.
// ============================================================

import java.io.BufferedReader;       // reads text line-by-line
import java.io.IOException;
import java.io.InputStreamReader;    // bridges byte stream → character stream
import java.io.PrintWriter;          // sends text lines to the server
import java.net.Socket;              // TCP connection to the server
import java.net.ConnectException;    // thrown when server is not reachable

public class Client {

    // ── Configuration ────────────────────────────────────────
    private static final String HOST = "localhost";  // server address
    private static final int    PORT = 5000;         // must match Server.java

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      TCP Chat Client started     ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("🔗 Connecting to " + HOST + ":" + PORT + " ...\n");

        // try-with-resources: sockets are closed automatically on exit
        try (
            // 1. Open a TCP connection to the server
            Socket socket = new Socket(HOST, PORT);

            // 2. Writer to send messages TO the server (autoFlush=true)
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // 3. Reader to receive replies FROM the server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // 4. Reader for the user's keyboard input
            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
            System.out.println("✅ Connected to server!");
            System.out.println("💬 Type a message and press Enter to send.");
            System.out.println("   Type 'exit' to disconnect.\n");

            String userMessage;

            // ── Main chat loop ────────────────────────────────
            while (true) {
                // Prompt the user
                System.out.print("[You]   ");
                userMessage = userInput.readLine();

                // readLine() returns null on EOF (Ctrl+D / Ctrl+Z)
                if (userMessage == null) {
                    break;
                }

                // Send the message to the server
                out.println(userMessage);

                // If the user typed 'exit', stop after sending
                if (userMessage.equalsIgnoreCase("exit")) {
                    System.out.println("🔌 Disconnecting...");
                    break;
                }

                // Wait for and display the server's reply
                String serverReply = in.readLine();

                if (serverReply == null) {
                    // Server closed the connection unexpectedly
                    System.out.println("⚠️  Server disconnected.");
                    break;
                }

                System.out.println("[Server] " + serverReply);
                System.out.println();   // blank line for readability
            }

        } catch (ConnectException e) {
            // Server isn't running – give a helpful hint
            System.err.println("❌ Could not connect to server.");
            System.err.println("   Make sure Server.java is running on port " + PORT + ".");
        } catch (IOException e) {
            System.err.println("❌ Client error: " + e.getMessage());
        }

        System.out.println("\n👋 Client stopped.");
    }
}
