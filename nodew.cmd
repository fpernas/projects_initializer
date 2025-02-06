@ECHO OFF
:: get version from node-wrapper.properties
set NODE_VERSION=20.18.2
set NODE_DIR=.node\node-v%NODE_VERSION%-win-x64
set NODE_ZIP=.node\node.zip
set NODE_URL=https://nodejs.org/dist/v%NODE_VERSION%/node-v%NODE_VERSION%-win-x64.zip
set NPM_CMD=%NODE_DIR%\npm.cmd
setlocal enabledelayedexpansion

:: Always download and use the project-specific Node.js version
if not exist "%NODE_DIR%" (
    echo Downloading Node.js v%NODE_VERSION% using PowerShell...

    :: Download Node.js
    powershell -Command "& {$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '%NODE_URL%' -OutFile '%NODE_ZIP%'}"

    echo Node.js downloaded! Extracting...

    :: Extract Node.js
    powershell -Command "& {Expand-Archive -Path '%NODE_ZIP%' -DestinationPath '.node' -Force}"

    echo Extraction complete!
)

echo Using project-specific Node.js v%NODE_VERSION%

:: Set PATH to ensure we use the correct local Node.js and npm
set "NEW_PATH=%CD%\%NODE_DIR%;%CD%\%NODE_DIR%\node_modules\npm\bin;%CD%\%NODE_DIR%\node_global;%PATH%"
set PATH=%NEW_PATH%

:: Ensure npm is installed inside the local Node.js environment
if not exist "%NODE_DIR%\node_modules\npm\bin\npm-cli.js" (
    echo ⏳ Installing npm locally...
    "%NODE_DIR%\node.exe" "%NODE_DIR%\npm-cli.js" install -g npm
    echo npm installed!
)

:: find a better way to get attributes without having to add 'nq' clauses
set argCount=0
for %%x in (%*) do (
    if "%%x" neq "npm" (
        if "%%x" neq "npx" (
            if "%%x" neq "ng" (
                set /A argCount+=1
                set "argVec[!argCount!]=%%~x"
            )
        )
    )
)

for /L %%i in (1,1,%argCount%) do (
    :: echo %%i- "!argVec[%%i]!"
    call set "npmArgs=%%npmArgs%% !argVec[%%i]!"
)

echo %npmArgs%

:: Handle npm and npx correctly (without duplicates)
if /I "%1"=="npm" (
    "%NODE_DIR%\npm.cmd" %npmArgs%
    exit /b %ERRORLEVEL%
)

if /I "%1"=="ng" (
    "%NODE_DIR%\ng.cmd" %npmArgs%
    exit /b %ERRORLEVEL%
)

:: Check if the command is "npx"
if /I "%1"=="npx" (
    shift
    "%NODE_DIR%\node.exe" "%NODE_DIR%\node_modules\npx\bin\npx-cli.js" %*
    exit /b %ERRORLEVEL%
)

:: Otherwise, run the given command with Node.js
"%NODE_DIR%\node.exe" %*
exit /b %ERRORLEVEL%

:: in the way it's written, it would only run npm/npx commands.
:: once angular its installed, you cannot run ng command
:: TODO: make the command running more generic
:: TODO: for Angular, avoid running npm after project creation -> Read more: https://angular.dev/cli/new
