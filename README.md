# Duke project

This is a project template for a greenfield Java project. It's named after the Java mascot _Duke_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 11, update Intellij to the most recent version.
1. Download the latest release from the release page.
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
   
   ........................................ROFL:ROFL:ROFL:ROFL:ROFL:ROFL
   ..................................................___|___.........
   ...........................................L..../.......[`\........
   ..........................................LOL===........[__|.......
   ...........................................L....\.8======D..|.......
   .................................................\=========/.......
   ...................................................|...|...........
   ..................................................==========/........
   ```
   
# Features
These are implemented commands that you can use.

## 1. Bye
Exits from the program. If you have an outstanding list, it will be saved automatically as lastSave.txt in
the saveStates folder. This folder will be automatically created when you first run the program.
>Syntax

    bye

> Example: 
   ```
Hello from
____        _
|  _ \ _   _| | _____
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|


........................................ROFL:ROFL:ROFL:ROFL:ROFL:ROFL
..................................................___|___.........
...........................................L..../.......[`\........
..........................................LOL===........[__|.......
...........................................L....\.8======D..|.......
.................................................\=========/.......
...................................................|...|...........
..................................................==========/........


Hello! I'm Duke-man. My superpower is that I am invincible to all manners of test cases
Type "command" to check available commands to try out!
What can I do for you?
____________________________________________________________________________________________________
   >>> bye
~\savestates\lastSave.txt
____________________________________________________________________________________________________
Noted! I've saved the list to the following directory: [~\savestates\]

1. [T][O] run the dog
2. [D][O] walk the park (by: FRIDAY SEPTEMBER 2020)
3. [E][X] run the cat (at: FRIDAY SEPTEMBER 2020 to FRIDAY SEPTEMBER 2020)
4. [T][X] asad
____________________________________________________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________________________________________________
   ```

## 2. List
Shows full list of appended tasks. You may modify the output format for each task with the appropriate arguements. 
>Syntax

    list <parameter type> <parameter 1> 
        @ <parameter type>:     /format , this parameter is optional.
            # <parameter 1>:        {datetime, day, month, week, year}. 
                                    (You may string these keywords in a single entry for your viewing preferences.)
>Example:
```
>>> list
1. [T][X] run the dog
2. [D][O] walk the park (by: FRIDAY SEPTEMBER 2020)
3. [E][X] run the cat (at: FRIDAY SEPTEMBER 2020 to FRIDAY SEPTEMBER 2020)
4. [T][X] asad
____________________________________________________________________________________________________
>>> list /format datetime
1. [T][X] run the dog
2. [D][O] walk the park (by: 2020/09/18 20:00:00)
3. [E][X] run the cat (at: 2020/09/18 15:00:00 to 2020/09/18 16:00:00)
4. [T][X] asad
____________________________________________________________________________________________________
>>> list /format day year
1. [T][X] run the dog
2. [D][O] walk the park (by: FRIDAY 2020)
3. [E][X] run the cat (at: FRIDAY 2020 to FRIDAY 2020)
4. [T][X] asad
____________________________________________________________________________________________________
>>>
```


## 3. Commands/ Command
Displays all outstanding commands executable by the user, with guidance on acceptable syntaxes.
>Syntax

    command
    commands

>Example
```
>>> command

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

12. find <string>
        < Conducts 1-to-1 search over all tasks for the string match. >
```


## 4. Done
Marks a task with index number specified as done. The index number starts from 1.
>Syntax

    done <integer>
        @ <integer>:     Task number on the list. Out-of-bounds and negative inputs are not allowed.
>Example
```
>>> list
1. [T][X] run the dog
2. [D][O] walk the park (by: FRIDAY 2020)
3. [E][X] run the cat (at: FRIDAY 2020 to FRIDAY 2020)
4. [T][X] asad
____________________________________________________________________________________________________
>>> done 1
Nice! I've marked this task as done:
  [T][O] run the dog
____________________________________________________________________________________________________
```
## 5. Todo
Adds a Todo task to the list. 
* Contains only the task name. 
* Does not take in datetime inputs.   
>Syntax
 
    todo <string>
        @ <string>:      Task name.
        <!> Tasks added this way are assumed to not be done and recorded accordingly.

>Example
```
>>> todo this is a todo
Got it! I've added this task:
  [T][X] this is a todo
Now, you have 1 task  in the list.
____________________________________________________________________________________________________
```

##6. Event
Adds an Event task to the list. 
* Contains the task name, the start datetime and the end datetime. 
* The start and end datetimes can be registered via a myriad of input formats.
>Syntax  

    event <string> <parameter type> <parameter 1> <parameter 2> to <parameter 3> <parameter 4>
        @ <string>:      Task name.
        @ <parameter type>:      /at
                # <parameter 1>:         Date in this format: YYMMDD or YYYY/M/D.
                # <parameter 2>:         Start time
                # <parameter 3>:         Date in this format: YYMMDD or YYYY/M/D. 
                                         (Feel free to omit this if the event starts and ends on the same day.)
                # <parameter 4>:         End time


>Example
```
>>> event this is an event /at 200919 1222 thisseperatorcanbeanythingsolongitdoesnotcontainnumbers 1300
Got it! I've added this task:
  [E][X] this is an event (at: SATURDAY SEPTEMBER 2020 to SATURDAY SEPTEMBER 2020)
Now, you have 2 tasks in the list.
____________________________________________________________________________________________________
```
   >Note that commands 6 and 7 accepts the following date and time formats:
        <br>@Date: YYYY*MM*DD or YYMMDD or YY/M/D or YY/MM/D
        <br>@Time: HH*MM*SS or HH*MM or HHMM or H
        <br>Note that * represents any non-numeric symbol.
>

##7. Deadline 
Adds an Event task to the list. 
* Contains the task name and a deadline datetime. 
* The deadline datetime can be registered via a myriad of input formats.
>Syntax

    deadline <string> <parameter type> <parameter 1> <parameter 2>
        < Add a task with a specific deadline>
        @ <string>:      Task name.
        @ <parameter type>:      /by
                # <parameter 1>:         Date in this format: YYMMDD or YYYY/M/D.
                # <parameter 2>:         Deadline time
>Example
```
>>> deadline this is a deadline /by 200919 1222
Got it! I've added this task:
  [D][X] this is a deadline (by: SATURDAY SEPTEMBER 2020)
Now, you have 3 tasks in the list.
____________________________________________________________________________________________________
```
   >Note that commands 6 and 7 accepts the following date and time formats:
        <br>@Date: YYYY\*MM\*DD or YYMMDD or YY/M/D or YY/MM/D
        <br>@Time: HH\*MM\*SS or HH*MM or HHMM or H
        <br>Note that * represents any non-numeric symbol.

##8. Remove task 
Removes a task with index number specified from the list. The index number starts from 1.

>Syntax

    remove <integer>
        @ <integer>:  Task number on the list. 
                      (Out-of-bounds and negative inputs are not allowed.)
>Example
```
>>> list
1. [T][X] this is a todo
2. [E][X] this is an event (at: SATURDAY 2020 to SATURDAY 2020)
3. [D][X] this is a deadline (by: SATURDAY 2020)
____________________________________________________________________________________________________
>>> remove 1
Process completed successfully!
        [NOTE]: You have 2 task/s left.
Noted! I've removed this task:
  [T][X] this is a todo
____________________________________________________________________________________________________
>>> list
1. [E][X] this is an event (at: SATURDAY 2020 to SATURDAY 2020)
2. [D][X] this is a deadline (by: SATURDAY 2020)
____________________________________________________________________________________________________
```
##9. Manual save 
Saves current list of tasks onto local disk as a save state. 
* Users can specify the save directory, and the name of the save state.
* Should a save state with the same input name already exists, the user will be prompted on whether they wish for the save file to be overwritten or not.

>Syntax

    save <parameter type 1> <parameter 1> <parameter type 2> <parameter 1>
        @ <parameter type 1>:    /name
                # <parameter 1>:         File name, with or without extension. Only .txt files accepted.
        @ <parameter type 2>:    /dir
                # <parameter 1>:         Specify a custom save folder path.

>Example
```
>>> saves
Save states in [~\savestates\]:
1.      lastSave.txt
2.      test.txt
____________________________________________________________________________________________________
>>> save /name test.txt
The file name supplied already exists in the directory. Are you sure you want to override it? [Y\N]
____________________________________________________________________________________________________
>>> y
Process completed successfully!
        [NOTE]: Alright, save state below will be overwritten:  [~\savestates\test.txt\]
Noted! I've saved the list to the following directory: [~\savestates\]

1. [E][X] this is an event (at: SATURDAY 2020 to SATURDAY 2020)
2. [D][X] this is a deadline (by: SATURDAY 2020)
____________________________________________________________________________________________________
```

## 10. Manual load
Loads an existing save state from local disk into the program as a list of tasks. 
* Users can specify the save directory, and the name of the save state.
* Should there be an open list in the program, the user will be prompted on whether they wish for the list to be overwritten or save onto local disk first.

>Syntax

    load <parameter type 1> <parameter 1> <parameter type 2> <parameter 1>
        @ <parameter type 1>:    /name
                # <parameter 1>:         File name, with or without extension. Only .txt files accepted.
        @ <parameter type 2>:    /dir
                # <parameter 1>:         Specify a custom save folder path.
                
>Example
```
>>> list
1. [T][X] run the dog
2. [D][O] walk the park (by: FRIDAY 2020)
3. [E][X] run the cat (at: FRIDAY 2020 to FRIDAY 2020)
4. [T][X] asad
____________________________________________________________________________________________________
>>> load /name test
There is a list currently being constructed. Would you like to save it first? [Y\N]
____________________________________________________________________________________________________
>>> y
Process completed successfully!
        [NOTE]: Alright, Enter the save command now:
>>> save /name anothersave
Noted! I've saved the list to the following directory: [~\savestates\]

1. [T][X] run the dog
2. [D][O] walk the park (by: FRIDAY 2020)
3. [E][X] run the cat (at: FRIDAY 2020 to FRIDAY 2020)
4. [T][X] asad
____________________________________________________________________________________________________
Noted! I've loaded the list from the following directory: [~\savestates\]

1. [E][X] this is an event (at: SATURDAY 2020 to SATURDAY 2020)
2. [D][X] this is a deadline (by: SATURDAY 2020)
____________________________________________________________________________________________________
```

## 11. Show save states
Show full list of save states in the default directory.
>Syntax

    saves

>Example
```
>>> saves
Save states in [C:\Users\Looi Kai Wen\Desktop\NUS\Year2Sem1\CS2113\testjar\savestates\]:
1.      anothersave.txt
2.      lastSave.txt
3.      test.txt
____________________________________________________________________________________________________
```

## 12. Find 
Conducts 1-to-1 word search over all tasks in their task name.
  >Syntax

    find <string>

  >Example
  ```
  >>> list
  1. [T][X] run the dog
  2. [D][O] walk the park (by: FRIDAY 2020)
  3. [E][X] run the cat (at: FRIDAY 2020 to FRIDAY 2020)
  4. [T][X] asad
  ____________________________________________________________________________________________________
  >>> find the
  0. [T][X] run the dog
  1. [D][O] walk the park (by: FRIDAY SEPTEMBER 2020)
  2. [E][X] run the cat (at: FRIDAY SEPTEMBER 2020 to FRIDAY SEPTEMBER 2020)
  Total number of results: 3!
  ____________________________________________________________________________________________________
  ```        

# Summary of Commands

No. | Command | Purpose | Syntax
----|---------|---------|-------
1|bye|Exit the program|_bye_ 
2|list | Lists all tasks|_list /format <format>_
3|command/s|Lists all commands|_command_, _commands_
4|done|Mark task as done|_done <index>_
5|todo|Add todo to list|_todo <task name>_
6|event|Add event to list|_event <task name> /at <start datetime> to <end datetime>_
7|deadline|Add deadline to list|_deadline <taskname> by <deadline datetime>_  
8|remove|Remove task from list|_remove <index>_
9|save|Manually saves list to save state|_save /name <name> /dir <path>_
10|load|Manually loads save state to list|_load /name <name> /dir <path>_
11|saves|Shows all save states|_saves_
12|find|Search task by string|_find <word>_
