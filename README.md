# rekreativ-startup

##About project

Project is created for keeping stats of football matches being played between teams. User is not part of team, teammates or matches entities, but is required for creating each of them. User has been mapped to UserDTO, so when getting all users, you won't be able to see passwords. User can get list of all users that are not admins (ROLE_ADMIN).

##UML Diagram

![alt text](https://github.com/salexdxd/rekreativ-startup/blob/main/src/main/resources/static/UML/RekreativUML.drawio.png?raw=true)

##Description

User with admin role (ROLE_ADMIN) is initialized when project is ran. When registering, user gets a default user role (ROLE_USER). Every user can create a team. Each team can have teammates that can be created by user. Teams can play each other, which will be saved in table Matches. For every match between teams, scores and total games played will be kept for TeamA and TeamB. If team is deleted, scores and total games played will be decreased accordingly.

Project has basic CRUD API for each of entities.

##Authorization

Using JWT (JSON Web Token) for authorizing users. Dependency used for generating token is auth0. Roles (ROLE_USER, ROLE_ADMIN) are used for permitting users to access each endpoint. ROLE_USER can access any endpoint that has anything to do with Teammate, Team and Matches.



