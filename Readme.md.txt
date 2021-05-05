Framework used- Spring Boot

Approaches Considered- 


- For each item, iterate over all its ingredients, and checking if desired quantity is available for all ingredients. If all ingredients have desired quantity available, then update the resources accordingly.

- To prevent race condition, used syncronization to serialize all operations. If ingredient is available, consume it. But before consuming, made sure that all the ingredients are avalable in terms of quantity,

- Pro: used syncronization to achieve some degree of concurrency.

- Approach used: We used a boolean flag to prevent a false state of the system. Whenever we recieve request to pour a drink, we try to check for the item quantities if possible and update the boolean flag.

- For example, consider "milk" ingredient has quantity = 10  and "water" ingredient has quantity = 20 currently Now, a request for an item came - which needs 5 units of "milk" but 30 units of "water"

- Since "milk" quantity is available as required but "water" quantity is not. So, did't reduce even the "milk" quantity.


To test the application-

-- Created random array of all the 4 beverages

Input-

String[] drinks = new String[] {"hot_tea","hot_coffee", "black_tea", "green_tea"};
			
String[] userInput = new String[100];
for(int i=0;i<100;i++) {
    int rand_int = rand.nextInt(100);
    userInput[i] = drinks[rand_int%4];
}

Output received-

01:25:52.323 [main] DEBUG org.apache.http.impl.conn.PoolingHttpClientConnectionManager - Connection [id: 0][route: {s}->https://api.npoint.io:443] can be kept alive indefinitely
01:25:52.323 [main] DEBUG org.apache.http.impl.conn.DefaultManagedHttpClientConnection - http-outgoing-0: set socket timeout to 0
01:25:52.323 [main] DEBUG org.apache.http.impl.conn.PoolingHttpClientConnectionManager - Connection released: [id: 0][route: {s}->https://api.npoint.io:443][total available: 1; route allocated: 1 of 2; total allocated: 1 of 20]
hot_tea is prepared
green_tea cannot be prepared because green_mixture is not available
black_tea is prepared
black_tea cannot be prepared because sugar_syrup is not sufficient
black_tea cannot be prepared because sugar_syrup is not sufficient
hot_tea cannot be prepared because hot_water is not sufficient
black_tea cannot be prepared because sugar_syrup is not sufficient
hot_coffee cannot be prepared because sugar_syrup is not sufficient
black_tea cannot be prepared because sugar_syrup is not sufficient
