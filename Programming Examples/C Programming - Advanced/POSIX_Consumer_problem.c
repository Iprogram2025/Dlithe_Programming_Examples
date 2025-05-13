// Implement a thread-safe producer-consumer problem using POSIX threads and semaphores in C.
/*
Pseudocode for Producer-Consumer Problem
BEGIN
    Initialize BUFFER_SIZE, NUM_ITEMS
    Initialize semaphores: EMPTY, FULL
    Initialize mutex LOCK

    FUNCTION Producer()
        FOR i = 0 TO NUM_ITEMS - 1 DO
            WAIT(EMPTY)  // Check if buffer has space
            LOCK(MUTEX)  // Enter critical section

            Produce Item i and store in BUFFER
            Print "Produced: i"

            UNLOCK(MUTEX)  // Exit critical section
            SIGNAL(FULL)  // Notify Consumer that data is available
        END FOR
    END FUNCTION

    FUNCTION Consumer()
        FOR i = 0 TO NUM_ITEMS - 1 DO
            WAIT(FULL)  // Check if buffer is not empty
            LOCK(MUTEX)  // Enter critical section

            Retrieve last item from BUFFER
            Print "Consumed: item"

            UNLOCK(MUTEX)  // Exit critical section
            SIGNAL(EMPTY)  // Notify Producer that space is available
        END FOR
    END FUNCTION

    MAIN()
        Create Producer and Consumer Threads
        Start Producer Thread
        Start Consumer Thread
        Wait for threads to finish

        Destroy semaphores and mutex

    END MAIN
END
*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

#define BUFFER_SIZE 5
#define NUM_ITEMS 10

int buffer[BUFFER_SIZE];
int count = 0;

sem_t empty, full;
pthread_mutex_t mutex;

// Producer function
void *producer(void *arg)
{
    for (int i = 0; i < NUM_ITEMS; i++)
    {
        sem_wait(&empty);
        pthread_mutex_lock(&mutex);

        buffer[count] = i;
        printf("Produced: %d\n", i);
        count++;

        pthread_mutex_unlock(&mutex);
        sem_post(&full);
    }
    return NULL;
}

// Consumer function
void *consumer(void *arg)
{
    for (int i = 0; i < NUM_ITEMS; i++)
    {
        sem_wait(&full);
        pthread_mutex_lock(&mutex);

        int item = buffer[count - 1];
        printf("Consumed: %d\n", item);
        count--;

        pthread_mutex_unlock(&mutex);
        sem_post(&empty);
    }
    return NULL;
}

int main()
{
    pthread_t prodThread, consThread;

    sem_init(&empty, 0, BUFFER_SIZE);
    sem_init(&full, 0, 0);
    pthread_mutex_init(&mutex, NULL);

    pthread_create(&prodThread, NULL, producer, NULL);
    pthread_create(&consThread, NULL, consumer, NULL);

    pthread_join(prodThread, NULL);
    pthread_join(consThread, NULL);

    sem_destroy(&empty);
    sem_destroy(&full);
    pthread_mutex_destroy(&mutex);

    return 0;
}

/*
Expected OutPut

Produced: 0
Produced: 1
Consumed: 1
Produced: 2
Consumed: 2
...
Produced: 9
Consumed: 9
*/