# Django Server [![CircleCI](https://circleci.com/gh/mohamedazab/cfc-smart-irrigation.svg?style=svg)](https://circleci.com/gh/mohamedazab/cfc-smart-irrigation)

Deployed on Kubernetes cluster on IBM cloud: http://159.122.174.163:31175

## How To Run
1. Create a `.env` file containing the following values and put it in the project's root folder.
    - `sk`: The django secret key
    - `db_user`: The username for the database admin/user
    - `db_password`: The password for the databse admin/user
2. Start a pipenv shell
    ```sh
    $ pipenv shell
    ```
3. Install all the python requirements with pip
    ```sh
    $ pip install -r requirements.txt
    ```
4. Make any necessary migrations
    ```sh
    $ python manage.py makemigrations
    $ python manage.py migrate
    ```
5. Start the server
    ```sh
    $ python manage.py runserver
    ```

## API Documentation
API used to interface between database, mobile app and hardware device for our smart irrigation system.

For the full documentation [go here](https://app.swaggerhub.com/apis-docs/AbdelrahmanKhaledAmer/CFC-Smart-Irrigation/1.0.0)
___
### /api/plant/:name

#### GET
##### Summary:

Retrieves plant from database

##### Description:

By passing the plant name in the  you can search for and retrieve the desired plant from the database.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| plant_name | path | the plant name being searched for | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | Search results matching criteria |
| 404 | No plant matching criteria |
| 405 | Method not allowed |
___
### /api/user

#### GET
##### Summary:

Get user

##### Description:

Get most up-to-date information of logged in user.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | user retrieved |
| 401 | Unauthorized |
| 405 | Method not allowed |
___
### /api/sign-up

#### POST
##### Summary:

Adds a user to the system

##### Description:

Creates a user and adds them to the system

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | new user created |
| 400 | Incomplete request |
| 405 | Method not allowed |
| 409 | User conflict |
___
### /api/login

#### POST
##### Summary:

creates a user and adds them to the system

##### Description:

creates a session for the user

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | Login successful |
| 400 | Incomplete request |
| 401 | Unauthorized |
| 403 | Already logged in |
| 405 | Method not allowed |
___
### /api/user/add

#### PUT
##### Summary:

Add a plant to user's grid

##### Description:

Add a plant that exists in the database to the logged-in user's grid.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | Login successful |
| 400 | Incomplete request |
| 401 | Unauthorized |
| 404 | No plant matching criteria |
| 405 | Method not allowed |
___
### /api/user/update-moisture

#### PUT
##### Summary:

Change certain plant's moisture.

##### Description:

Change the moisture of the specified plant for the logged-in user

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | moisture updated successfully |
| 400 | Incomplete request |
| 404 | No plant matching criteria |