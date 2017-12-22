# Code Loader Installation Steps
This README.md file is intended to provide steps to install code loader as a single deployment unit.

## Installation
### Windows
1. The package would be delivered as a zip file naming as codeloaders_**version**
2. Unzip the package under **INSTALL_DIR** (*INSTALL_DIR* can be any folder of choice)

### Linux
1. The package would be delivered as a zip file naming as codeloaders_**version**
2. Unzip the package under **INSTALL_DIR** (*INSTALL_DIR* can be any folder of choice)

## Configuration
Navigate to **INSTALL_DIR**/conf folder.
* Open *application.properties* file
  * Edit the code loader database details.
    * spring.datasource.url=<codeloader> db to connect to (eg: jdbc:oracle:thin:@//localhost:1521/DB12C)
    * spring.datasource.username=<username>
    * spring.datasource.password=<password>
  * Edit input folder directories
    * basedata.path=<input raw data> (having all input data file)
    * listener.current.data.path=<folder where new files will be placed> (ftp folder or local folder)
    * listener.inprogress.data.path=<folder where new files will be copied for processing)
  * Edit **log.file.path** property as **INSTALL_DIR**/logs
