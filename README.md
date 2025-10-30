# Dependencies
- Java 21

# Usage
If using an IDE, good luck. Otherwise, here is a guide for using Java's command-line interface.

### If using Windows, replace `.sh` with `.bat` in the following commands.

First compile the project:
```bash
./compile.sh
```

Then execute the script without arguments to interactively choose to start a client/server:
```bash
./run.sh
```

Or start a client/server directly:
```bash
# start a client:
./run.sh c
# or
./run.sh client

# start a server:
./run.sh s
# or
./run.sh server
```

To disable logging, change `App.SHOW_LOGS` to false and recompile.
