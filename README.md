# bingo90Proto
Bingo 90 Stips creator

**Algorithm Logic explained:**

The algorithm is designed to work in three phases:
1. Create a strip with all empty values (represented by zeros)
2. Create a Hashmap which contains all 90 numbers which we need to insert. Key value pairs in the map will be:
     0 -> (1,...,9)
     1 -> (10,...19)
     2 -> (20,... 29)
     ...
     8 -> (80,...90)
 This is done in Strip.java method initialFillRemainingNumbersMap().
 
3. Iterate through all tickets on the strip, and all columns on each ticket. In the first iteration, we ensure that each ticket column will have at least one number. For each ticket column we take one random number from the Hashmap created in step 2. We take a number from Hashmap in a way that for column 0, we take value from MapEntry with key = 0, for column 1 value from MapEntry with key = 1, etc... While doing this we also take care that no ticket rows will have more then 5 numbers.
This is done in Strip.java method fillAllColumnsEachTicketWithOneNumber().

4. After we have ensured that we have at least one number in each ticket column, we have 36 numbers left to fill on the strip. Now we iterate row by row, placing the numbers left in the HashMap randomly into empty row fields. Each row is filled with numbers until we have 5 numbers in it. If row is already full we skip it. If none of the remaining empty row fields can be populated from the corresponding values from the HashMap (eg. meaning for row position 5, there are no more values in HashMap with key = 5, and for all other empty fields as well) that means that we messed up somewhere along the way and we need to backtrack our steps. 

5. Last part of the algorithm is going one row backwards, and changing one value which we placed in step 4 with one possible value from the HashMap. Then we try to finish the algorithm. If solution still does not work, we go one row back, replace another value with one from the HashMap, and so on... If we exhausted all options from that row, we go further back in line until we find a viable solution.     

6. Once we have all rules in check (at least one number per column, exactly 5 numbers per row, all 90 numbers used), we then sort the tickets in the strip. This is done in Strip.java method sortStripColumnsAndFillTicket().

More detailed comments can be found in Strip.java class within the methods


**Build project:** 

mvn clean install

or build and skip the tests:

mvn clean install -DskipTests

**Run the executable file:**

Open cmd on windows, or any other cli

Go to "bingo90/target" directory and run the following command (where 10,000 is arbitrary number you can use, and means number of strips you want to create):

java -cp bingo90-1.0-SNAPSHOT.jar BingoApp.Main 10000