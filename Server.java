// ============================================================
//  Server.java  –  Simple TCP Chat Server
//  Listens on a port, accepts ONE client, exchanges messages.
//  Run this FIRST before starting the client.
// ============================================================

import java.io.BufferedReader;       // reads text line-by-line
import java.io.IOException;
import java.io.InputStreamReader;    // bridges byte stream → character stream
import java.io.PrintWriter;          // sends text lines to the client
import java.net.ServerSocket;        // listens for incoming connections
import java.net.Socket;              // represents the connected client

public class Server {

    // ── Configuration ────────────────────────────────────────
    private static final int PORT = 5000;   // port the server binds to

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      TCP Chat Server started     ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("⏳ Waiting for a client on port " + PORT + " ...\n");

        // try-with-resources: Java auto-closes every socket when done
        try (
            // 1. Create the server socket – binds to PORT and starts listening
            ServerSocket serverSocket = new ServerSocket(PORT);

            // 2. Block here until exactly ONE client connects
            Socket clientSocket = serverSocket.accept();

            // 3. Open a reader to receive text from the client
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // 4. Open a writer to send text back to the client
            //    autoFlush=true means each println() is sent immediately
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // 5. Reader for the server operator's own keyboard input
            BufferedReader serverInput = new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
            System.out.println("✅ Client connected: "
                    + clientSocket.getInetAddress().getHostAddress());
            System.out.println("💬 Type a message and press Enter to reply.");
            System.out.println("   Type 'exit' to shut down the server.\n");

            String messageFromClient;

            // ── Main chat loop ────────────────────────────────
            // readLine() returns null when the client closes the connection
            while ((messageFromClient = in.readLine()) != null) {

                System.out.println("[Client] " + messageFromClient);

                // Check if the client wants to end the session
                if (messageFromClient.equalsIgnoreCase("exit")) {
                    System.out.println("🔌 Client sent 'exit'. Closing connection.");
                    out.println("SERVER: Goodbye!");
                    break;
                }

                // Read a reply from the server operator's keyboard
                System.out.print("[You]   ");
                String reply = serverInput.readLine();

                // Send the reply to the client
                out.println("SERVER: " + reply);

                // Allow the server operator to exit too
                if (reply != null && reply.equalsIgnoreCase("exit")) {
                    System.out.println("🔌 Server shutting down.");
                    break;
                }
            }

        } catch (IOException e) {
            // Print a friendly error – most likely the port is already in use
            System.err.println("❌ Server error: " + e.getMessage());
        }

        System.out.println("\n👋 Server stopped.");
    }
}
