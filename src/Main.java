import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class Main extends JavaPlugin {


    String token;
    int delay;
    String[] userStrings;
    String message;
    String command = "ngrok tcp 25565";

    JDA jda;
    ArrayList<User> users = new ArrayList<>();

    @Override
    public void onLoad() {
        try {
            this.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void run() throws InterruptedException {

        File configFile = new File("config");

        if (!configFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Scanner scanner = new Scanner(configFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.contains("#"))
                    break;

                String prefix = line.split("=")[0];
                String contents = line.split("=")[1];

                switch (prefix) {
                    case "token":
                        token = contents;
                        break;
                    case "delay":
                        delay = Integer.parseInt(contents);
                        break;
                    case "users":
                        userStrings = contents.split(",");
                        break;
                    case "message":
                        message = contents;
                        break;
                    case "command":
                        command = contents;
                }

            }
        } catch (FileNotFoundException e) {
            this.getLogger().log(Level.SEVERE, "Config file couldn't be found");
        }

        try {
            startNgrok();
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, e.toString());
            this.getLogger().log(Level.SEVERE, "Ngrok failed to load");
        }

        this.getLogger().log(Level.INFO, token);


        JDA jda = JDABuilder.createDefault(token).build();
        addUsers(jda);


        Thread.sleep(delay);

        boolean success = false;
        String ip = "";

        while (!success) {
            try {
                ip = getIp();
                success = true;
            } catch (IOException e) {
                this.getLogger().log(Level.INFO, "Failed to get ip from ngrok, trying again...");
                Thread.sleep(1000);
            }
        }


        System.out.printf(ip);

        sendMessages(ip);

    }


    void startNgrok() throws IOException {
        Runtime.getRuntime().exec(command);
    }

    void addUsers(JDA jda) {
        for (String userString : userStrings) {
            System.out.println(userString);

            jda.retrieveUserById(userString).queue(user -> users.add(user));
        }
    }

    String getIp() throws IOException{

        URL uri= new URL("http://localhost:4040/api/tunnels");
        URLConnection ec = uri.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(ec.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();


        return a.toString().split("tcp://")[1].split("\"")[0];
    }

    void sendMessages(String ip) {
        for (User user : users) {

            if (user == null) {
                this.getLogger().log(Level.SEVERE, "User is null");
                return;
            }
            user.openPrivateChannel().queue((channel) -> channel.sendMessage(message + " " + ip).queue());
        }
    }



}