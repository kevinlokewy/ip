# Augustine User Guide

Augustine is a CLI based chatbot that helps
users keep track of tasks. 

Augustine uses several commands to enable users to distinguish between different tasks. For each task, the output in the terminal is as such: [%][ ] Description. Where % is either D,E or T representing deadline, event or todo respectively. The second box indicates whether the task is done or not done. This is represented by the presence of [X] in the case of done, and [ ] in the case of not done.

Specifically for the command event, dates and times in prose or in dd/mm/yyyy format are accepted by the programme. Specific outputs are given as examples in the feature section.

## Core Features of Augustine

### deadline
This command allows users to input a task that has a deadline or due date.

example input: 'deadline CS2113 ip /by 03 Oct 2025' 

expected output: 
____________________________________________________________
Okay! I've added the following task:
[D][ ] CS2113 ip (by: 03 Oct 2025)
You now have 1 task in the list.
____________________________________________________________

### event
This command allows users to input a task that has both a duration (i.e, a start and end time/date)

example input: 'event CS2113 Lecture /from Friday 1600 /to 1800'

expected output:
____________________________________________________________
Okay! I've added the following task:

[E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
You now have 2 tasks in the list.
____________________________________________________________

example input: 'event CS2113 lecture /from 03/10/2025 1615 /to 03/10/2025 1715'

expected output: 
____________________________________________________________
Okay! I've added the following task:

[E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
You now have 5 tasks in the list.
____________________________________________________________
### todo
The todo command enables users to input tasks that have no due date or start time attatched to it. 

example input: 'todo clean toilet'

expected output: 
____________________________________________________________
Okay! I've added the following task:

[T][ ] clean toilet
You now have 6 tasks in the list.
____________________________________________________________
### list

The list command allows users to see the current list of tasks that the user had already input. Each task has a corresponding index which is based on the order of input (earliest to latest).

example input: 'list'

expected output: 
____________________________________________________________
Here are the tasks in your list:
1. [D][X] CS2113 ip (by: 03 Oct 2025)
2. [E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
3. [E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
4. [T][ ] clean toilet
____________________________________________________________

### mark
The mark command allows users to keep track of which tasks have been completed. This is indicated by the [X] in the second box. The mark command marks based on the index of the task on the list. 

For example, if this is the list prior to marking:
____________________________________________________________
Here are the tasks in your list:
1. [D][ ] CS2113 ip (by: 03 Oct 2025)
2. [E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
3. [E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
4. [T][ ] clean toilet
____________________________________________________________
example input: mark 1

example input: 
____________________________________________________________
Okay, I've marked this task as done!

[D][X] CS2113 ip (by: 03 Oct 2025)
____________________________________________________________

Calling 'list' again will return the list with the marked tasks indicated:
____________________________________________________________
Here are the tasks in your list:
1. [D][X] CS2113 ip (by: 03 Oct 2025)
2. [E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
3. [E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
4. [T][ ] clean toilet
____________________________________________________________

### unmark
The unmark command is exactly the same as mark, except that it unmarks the task instead of marking it as done. 

### find

The find function allows users to search for a specific keyword in their list. The output will show all the tasks, with their corresponding indexes, to the user. 

example input: 'find CS2113'

expected output:
____________________________________________________________
Here are the matching tasks in your list:
1. [D][X] CS2113 ip (by: 03 Oct 2025)
2. [E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
3. [E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
____________________________________________________________

### delete

delete allows users to remove tasks from the list. Similar to mark and unmark, delete requires the corresponding index of the task that the user intends to remove. If this is the list prior to deleting: 

____________________________________________________________
Here are the tasks in your list:
1. [D][X] CS2113 ip (by: 03 Oct 2025)
2. [E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
3. [E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
4. [T][ ] clean toilet
____________________________________________________________


example input: 'delete 1'

expected output: 
____________________________________________________________
Noted. I've removed this task:
[D][X] CS2113 ip (by: 03 Oct 2025)
Now you have 3 tasks in the list.
____________________________________________________________

Calling 'list' again will show the reflected changes:

____________________________________________________________
Here are the tasks in your list:
1. [E][ ] CS2113 Lecture (from: Friday 1600 to: 1800)
2. [E][ ] CS2113 lecture (from: Oct 03 2025 16:15 to: Oct 03 2025 17:15)
3. [T][ ] clean toilet
____________________________________________________________


### bye
This command is used when the user wants to exit the programme. 

example input: 'bye'

expected output:
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________

