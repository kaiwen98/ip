# Duke project template

This is a project template for a greenfield Java project. It's named after the Java mascot _Duke_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 11, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)
1. Set up the correct JDK version, as follows:
   1. Click `Configure` > `Structure for New Projects` and then `Project Settings` > `Project` > `Project SDK`
   1. If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11
   1. Click `OK`
1. Import the project into Intellij as follows:
   1. Click `Open or Import`.
   1. Select the project directory, and click `OK`
   1. If there are any further prompts, accept the defaults.
1. After the importing is complete, locate the `src/main/java/Duke.java` file, right-click it, and choose `Run Duke.main()`. If the setup is correct, you should see something like the below:
   ```
   Hello from
    ____        _        
   |  _ \ _   _| | _____ 
   | | | | | | | |/ / _ \
   | |_| | |_| |   <  __/
   |____/ \__,_|_|\_\___|
   ```
These are implemented commands that you can use.

1. bye
        < Exit the program >

2. list <parameter type> <parameter 1>
        < Show full list of appended tasks. >
        @ <parameter type>:      /format , this parameter is optional.
                # <parameter 1>:         {datetime, day, month, week, year}. You may string these keywords in a single entry for your viewing preferences.

3. commands
        < Show full list of commands >

4. done <integer>
        < Mark a task by number <integer> as done. >
        @ <integer>:     Task number on the list. Out-of-bounds and negative inputs are not allowed.

5. todo <string>
        < Will be interpreted as input tasks. Input task will then be added to the list. >
        <!> Tasks added this way are assumed to not be done and recorded accordingly.

6. event <string> <parameter type> <parameter 1> <parameter 2> to <parameter 3> <parameter 4>
        < Add a task which is happening in the future with specific date and time >
        @ <string>:      Task name.
        @ <parameter type>:      /at
                # <parameter 1>:         Date in this format: YYMMDD or YYYY/M/D.
                # <parameter 2>:         Start time
                # <parameter 3>:         Date in this format: YYMMDD or YYYY/M/D. Feel free to omit this if the event starts and ends on the same day.
                # <parameter 4>:         End time

7. deadline <string> <parameter type> <parameter 1> <parameter 2>
        < Add a task with a specific deadline>
        @ <string>:      Task name.
        @ <parameter type>:      /by
                # <parameter 1>:         Date in this format: YYMMDD or YYYY/M/D.
                # <parameter 2>:         Deadline time

   Note that commands 6 and 7 accepts the following date and time formats:
        @Date: YYYY*MM*DD or YYMMDD or YY/M/D or YY/MM/D
        @Time: HH*MM*SS or HH*MM or HHMM or H
        Note that * represents any non-numeric symbol.

8. remove <integer>
        < Remove task by number <integer> from list. >
   @ <integer>:  Task number on the list. Out-of-bounds and negative inputs are not allowed.

9. save <parameter type 1> <parameter 1> <parameter type 2> <parameter 1>
        < Saves current task to local disk. A default folder is: >
        [C:\Users\Looi Kai Wen\Desktop\NUS\Year2Sem1\CS2113\testjar\savestates\]
        @ <parameter type 1>:    /name
                # <parameter 1>:         File name, with or without extension. Only .txt files accepted.
        @ <parameter type 2>:    /dir
                # <parameter 1>:         Specify a custom save folder path.

10. load <parameter type 1> <parameter 1> <parameter type 2> <parameter 1>
        < Loads saved task from local disk. A default folder is: >
        [C:\Users\Looi Kai Wen\Desktop\NUS\Year2Sem1\CS2113\testjar\savestates\]
        @ <parameter type 1>:    /name
                # <parameter 1>:         File name, with or without extension. Only .txt files accepted.
        @ <parameter type 2>:    /dir
                # <parameter 1>:         Specify a custom save folder path.

11. saves
        < Show full list of save states in default directory >