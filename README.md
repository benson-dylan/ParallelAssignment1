<h1>To compile and run this program</h1>
- javac AllPrimeNumbers.java<br>
- java  AllPrimeNumbers

<h2>Assignment Documentation</h2>
The output file execution time is measured in milliseconds.<br>

I used Java's executor library to handle the spawning and completion of threads.<br>
The main algorithm I used to calculate the primes was a modified version of the Sieve of Eratosthenes.<br>
To balance the work load across all eight threads, I used a synchronized counter class for selecting numbers to be processed.<br>
To mutually exclude other threads from potentially picking the same number, I utilize Java's Reentrant Lock library.<br>
Additionally, I utilize Java's synchronized lists to ensure mutual exclusion when reading and writing to the shared composite list.<br>
The work that is being done concurrently is marking off composite numbers in a shared list of all numbers from 2 to n.<br>

<h2>Experimental Evaluation</h2>
I tested various numbers for n and outputs seem to be correct: <br>
For 10000 - 1229 primes, sum = 5736396 <br>
For 10^5 - 9592 primes, sum = 454396537 <br>
For 10^8 - 5761455 primes, sum = 279209790387276 <br>
For 10^9 - 50847534 primes, sum = 24739512092254535 <br>
