# ai-game

Implements protocol on https://github.com/samuelchvez/open-1v1-board-game-coordinator.git

To compile dist:
from main folder
```
./gradlew compileKotlin distZip 
```

To run:

First unzip the compiled dist
```
unzip ai-2020-0.1.zip 
```


Run passing the args:
```
./ai-2020-0.1/bin/ai-2020  -s <server address> -t <tournament id> -u <user name>
```
