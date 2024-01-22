# To compile and run this program
- javac AllPrimeNumbers.java<br>
- java  AllPrimeNumbers

# Assignment Documentation
I used Java's executor library to handle the spawning and completion of threads.<br>
The main algorithm I used to calculate the primes was a modified version of the Sieve of Eratosthenes.<br>
To balance the work load across all eight threads, I used a synchronized counter class for selecting numbers to be processed.<br>
To mutually exclude other threads from potentially picking the same number, I utilize Java's Reentrant Lock library.<br>
Additionally, I utilize Java's synchronized lists to ensure mutual exclusion when reading and writing to the shared composite list.<br>
The work that is being done concurrently is marking off composite numbers in a shared list of all numbers from 2 to n.<br>
