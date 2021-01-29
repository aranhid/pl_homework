import java.io.File
import java.net.ServerSocket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class Server(private val port: Int) {
    private val serverSocket = ServerSocket(port)

    fun start() {

        Logger.attach(ConsoleHandler())
        Logger.attach(FileHandler(File("serverLog.txt")))

        while (true) {
            Logger.info("Wait for new connection")
            val clientSocket = serverSocket.accept()
            clientSocket.soTimeout = 30000
            Logger.info("Process new client ${clientSocket.localAddress}")
            val inStream = clientSocket.getInputStream().bufferedReader()
            val outStream = clientSocket.getOutputStream().bufferedWriter()
            while (!clientSocket.isOutputShutdown) {
                try {
                    Logger.info("Wait for new message")
                    val data = inStream.readLine()
                    if (data != null) {
                        Logger.info(data)
                        val interpreter = Interpreter(Parser(Lexer((data))))
//                        val result = interpreter.interpret()
//                        outStream.write("Result = $result\n")
                        outStream.flush()
                    }
                    else {
                        Logger.info("Client closed connection")
                        break
                    }
                }
                catch (e: SocketTimeoutException) {
                    Logger.warning("Disconnect client due timeout")
                    outStream.write("error\n");
                    outStream.flush()
                    clientSocket.close()
                    break
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    Server(5555).start()
//    thread {
//        Server(5555).start()
//    }
//    val client = Client("127.0.0.1", 5555)
//    client.connect()
//    client.send("2 + 2")
//    println(client.recv())
//    Thread.sleep(30000)
//    client.send("2 + 2")
//    println(client.recv())
}