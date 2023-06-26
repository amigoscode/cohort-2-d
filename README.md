# cohort-2-d

## Clone project from github repo

## Database setup
* ### Install Docker
* ### Run docker compose up
  In the root of the project run the following command:  using postgres image, this command will create our db container
    ```bash
      docker compose up -d
    ```
* ### Connect to the created container using shell
  #### 1- we'll display list of our containers to get database container name:
  ```bash
    docker ps
  ```
  #### 2- using db container name, we'll connect to it
  (in our case container name: 'postgres-bookstore-db') :
  ```bash
    docker exec -it postgres-bookstore-db bash
  ```
* ### Create bookstore database
    #### 1- Connect to database: Using postgres cli
    Now that we are connected to the container, we can connect to DB running inside 
    ```bash
      psql -U bookstore-db-user
    ```
    #### 2- Create ou bookstore database: Using SQL
    Then we can create ou Database
    ```bash
      CREATE DATABASE bookstore;
    ```