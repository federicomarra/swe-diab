# swe-diab

![Java](https://img.shields.io/badge/Java-11.0-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![GitHub last commit](https://img.shields.io/github/last-commit/federicomarra/swe-diab?style=for-the-badge)
![GitHub repo size](https://img.shields.io/github/repo-size/federicomarra/swe-diab?style=for-the-badge)

Unifi Software Engineering project for diabetes management

## How to run & build

1. Install dependencies with `mvn install -f pom.xml` from the root of the project.
1. Run `mvn exec:java@main` to execute the random values example.
1. Run `mvn exec:java@cli` to start the interactive cli program.
1. Run `mvn exec:java@gui` to start the interactive gui program.
1. Run `mvn clean test` to execute all the available tests.
1. Run `mvn clean package` to build the jar file.

If you want to change the default database path (`db/data.db`) or the default method of backup (clone locally `data.db`), you can do so by creating a `.env` file in the root of the project with specifies a local path for the data file, an ftp string for the backup if desired and the remote location to clone the file in (defaults to `backup.db`). The file should look like this:

```env
DATABASE_FILE="path/to/database.db"
BACKUP_FTP_STRING="remote-server.com:21?user=your-username&password=your-password"
BACKUP_DB_PATH="path/to/backup.db"
```

You can use [SFTP Cloud](https://sftpcloud.io/tools/free-ftp-server) to create a temporary ftp server for testing purposes.

## Run the packaged GUI

1. Download the `swe-diab.jar` file from the [releases](<https://github.com/federicomarra/swe-diab/releases>) page.
1. Run `java -jar swe-diab.jar` to start the gui program.

## Authors

Federico Marra [@federicomarra](https://github.com/federicomarra) & Alberto Del Buono Paolini [@albbus-stack](https://github.com/albbus-stack)

## License

MIT License

Copyright (c) 2023 federicomarra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
