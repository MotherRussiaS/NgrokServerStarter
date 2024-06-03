# Usage

## Dependencies

Ngrok must be installed with the authtoken already set up. Windows users should place their ngrok.exe file into the server directory. Plugin natively supports Spigot 1.20.6, but feel free to use the source code to build the plugin for whatever Minecraft version necessary.

## Setup

If you haven't already created a discord bot, make one, and note its token for use in the plugin
https://discord.com/developers/docs/quick-start/getting-started

Add jar file to 'plugins' directory in server.

**Windows users must also add their ngrok executable to the server directory.**

Plugin will create a file called 'config.cfg' in the server's default directory when first started.
>Note: You will have to restart/reload the server for changes in the config to take effect.

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
