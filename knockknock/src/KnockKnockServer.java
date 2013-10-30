import java.net.*;
import java.io.*;
// ServerSocket is a java.net class that provides a system-indep. implementation of the server side of a client
// sever socket connection. The constructor for ServerSocket throws an exception if it can't listen on the specified port
// If the server successfully binds to its port, then the ServerSocket object is successfully created and the server continues
// to the next step -- accepting a connection from a client (clientSocket = serverSocket.accept();)
// The accept method waits until a client starts up and requests a connection on the host and port of this server
// When a connection is requested and successfully established the accept method returns a new Socket object which is bound to the
// same local port and has its remote address and remote port set to that of the client. The server can communicate with the client over
// this new Socket and continue to listen for client connection requests on the original ServerSocket.
public class KnockKnockServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        // After the server successfully establishes a connection with a client, it communicates with the client using this code
        // Step One: Gets the socket's input and output stream and opens readers and writers on them
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        )  {
            String inputLine, outputLine;
            // Step Two: Initiates communication with the client by writing to the socket
            // Creates a KnockKnockProtocol object -- the object that keeps track of the current joke, the current state within the joke
            // After the KKP is created, the code calls KKP's processInput method to get the first message that the server sends to the client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);


            // Step Three: Communicates with the client by reading from and writing to the socket
            // As long as the client and server still have something to say to each other, the server reads from and writes to the socket.
            // The server initiates the conversation with a "Knock! Knock!" so afterwards the server must wait for the client to say "Who's there?"
            // The while loop iterates on a read from the input stream. The readLine method waits until the client responds by writing something to its output stream ( the server's input stream)
            // When the client responds the server passes the clients response to the KnockKnockProtocol object and asks the KnockKnockProtocol object for a suitable reply
            // The server immediately sends the reply to the client via the output stream connected to the socket, using a call to println.
            // If the server's response generated from the KnockKnockServer is "Bye." this indicates that the client doesn't want anymore jokes and the loop quits.
            // The Java runtime automatically closed the input and output streams, the client socket, and the server socket because they have been created in the try-with-resources statement
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}