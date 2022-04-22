
# SamS_Internship_Project

This is an educational project

The idea of this project is to create a web application to provide functionality to registrate organisations
such as barbershops, beauty salon, etc. that can provide their services to users.

Organisation has functionality to manage their workers, utilities, make schedule.

User has functionality to make a reservation.



## Deployment

To launch this project you have to download a dev branch:

```bash
  git clone --single-branch --branch Review_2_DB_Generation https://github.com/Alikhver/SamS_Internship_Project.git
```

To create database you need a mysql user with credentials:

```user=bestuser```\
```password=bestuser```

or

change properties of user, password(db.username, db.password) on path ```model/src/main/resources/db/db.properties```
if you want to use existing connection and user.


Once all listed above is completed, run database script on path

```model/src/main/resources/db/script/DB generation.sql```.

To create Database for tests run script on location

```model/src/test/resources/db/script/Test DB generation.sql```.

Now application is ready to start.

## API Reference

All server endpoints can be found on http://localhost:8080/swagger-ui/#/ once application
is launched


## Future Features

- Security
- Tests
- Web View


## Authors

- [@Alikhver](https://github.com/Alikhver)

