# ItemLoan-system
The item loan system created using Java language.

System has multiple excpetion handling classes, interfaces, and redo, undo functionalities.

Also, the program has various commands for item loaning, such as:

startNewDay 'dd-mon-year'           - start a new day for system
register 'user-id' 'user-name'      - for user registration
arrive 'item-id' 'item-name'        - for item registration
checkout 'user-id' 'item-id'        - for user item checking out
request 'user-id' 'item-id'         - for user to get on a waiting queue for an item
cancelRequest 'user-id' 'item-id'   - for user cancel the item request
listMember                          - for listing all members
listItems                           - for listing all items
undo                                - undo previous action
redo                                - redo action


Example commands:

startNewDay 03-Jan-2021

register ID1 R1
register ID2 R2
register ID3 R3

listMembers

arrive LID1 phone
arrive LID2 printer
arrive LID3 stabilizer

listItems

checkout ID1 LID1
request ID2 LID1
listMembers

cancelRequest ID2 LID1
checkin ID1 LID1

listItems
listMembers

undo

listItems
listMembers

register ID1 R4
checkout ID8 LID2
checkout ID3 LID1
request ID2 LID3





