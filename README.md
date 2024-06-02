# Usage

## Setup

Add jar file to 'plugins' directory in server.

Plugin will create a file called 'config' in the server's default directory when first started
>Note: This may cause errors to be thrown when first running the plugin

### Example Config File

```
#Token of the discord bot that you want to sent the message
token=enter_discord_bot_token_here
#Delay in ms to allow for the discord bot and ngrok to both start. Configure as needed
delay=2000
#User ids of people to send the ip to, seperated by a comma
users=123456789,123456789
#A small message to add before the ip
message=Here is your ip!:
#Command that gets executed to start the ngrok client (commented by default). Use this option if your server runs on another port than 25565
#command=ngrok tcp 25565
```

## In-game 

The command 'sendip' can be used to redetect and send the ip again. User must be op to use it.

```
/sendip
```
